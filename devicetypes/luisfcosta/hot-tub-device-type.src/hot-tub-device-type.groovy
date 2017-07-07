/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *  Based on smartthings simulated thermostat device type
 *
 * Will be used to control a Spa/Pool with: individual control switches for heater and jets and a temperature sensor.
 * Actions: turn on - heater will heat the water till it reaches the set temperature, turn off - turn heater and jets off, 
 * jets on/off - turn on/off jets switch.
 * Needs a companion smartapp to subscribe events to/from the switches and temperature sensor.
 *
 */
metadata {
	definition (name: "Hot Tub device type", namespace: "luisfcosta", author: "luisfcosta@live.com") {
		capability "Thermostat"
		capability "Sensor"
		capability "Actuator"
        capability "Switch"

		command "tempUp"
		command "tempDown"
		command "setTemperature", ["number"]
        command "JetsOn"
        command "JetsOff"
        command "heaterOn"
        command "heaterOff"
        
        attribute "heater", "String"
        attribute "jets", "String"
        //attribute "temperature", "Number"
        attribute "heatingSetpoint", "Number"
        attribute "mode", "String"
	}

	tiles(scale: 2) {
        
		valueTile("heatingSetpoint", "device.heatingSetpoint", width: 6, height: 4) {
			state("heatingSetpoint", label:'${currentValue} F', unit:"dF",
				backgroundColors:[
					[value: 80, color: "#0033cc"],
					[value: 95, color: "#66ccff"],
					[value: 100, color: "#33cc33"],
					[value: 105, color: "#ff9900"],
					[value: 110, color: "#cc0033"]
				]
			)
		}
        
		controlTile("heatSliderControl", "device.heatingSetpoint", "slider", height: 1, width: 6, inactiveLabel: false, range:"(90..105)") {
			state "setHeatingSetpoint", action:"thermostat.setHeatingSetpoint", backgroundColor:"#d04e00"
		}

		standardTile("tempDown", "device.heatingSetpoint", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
            state "default", action:"tempDown", icon:"st.thermostat.thermostat-down"
		}
        
		standardTile("tempUp", "device.heatingSetpoint", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
            state "default", action:"tempUp", icon:"st.thermostat.thermostat-up"
		}
        
        standardTile("jets", "device.jets", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "off", action:"JetsOn", nextState: "on", icon: "https://raw.githubusercontent.com/luisfocosta/smartthings/master/Hot%20tub/icons/Hot-tub-jets-off.png"
			state "on", action:"JetsOff", nextState: "off", icon: "https://raw.githubusercontent.com/luisfocosta/smartthings/master/Hot%20tub/icons/Hot-tub-Jets-On.png"
		}

		valueTile("temperature", "device.temperature", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "heat", label:'Now ${currentValue} F', unit: "F", backgroundColor:"#ffffff"
		}

		standardTile("mode", "device.mode", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "off", action:"on", nextState: "on", icon: "https://raw.githubusercontent.com/luisfocosta/smartthings/master/Hot%20tub/icons/Hot-tub-heating-off.png"
			state "on", action:"off", nextState: "off",  icon: "https://raw.githubusercontent.com/luisfocosta/smartthings/master/Hot%20tub/icons/Hot-Tub-On.png"
            state "heating", action: "off", nextState: "off", icon: "https://raw.githubusercontent.com/luisfocosta/smartthings/master/Hot%20tub/icons/Hot-Tub-Heating-On.png"
		}
        
        standardTile("switch", "device.switch", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
            state "off", action:"on", icon: "https://raw.githubusercontent.com/luisfocosta/smartthings/master/Hot%20tub/icons/off-icon.png", backgroundColor: "#ffffff"
            state "on", action:"off", icon: "https://raw.githubusercontent.com/luisfocosta/smartthings/master/Hot%20tub/icons/on-icon.png", backgroundColor: "#79b821"
		}
        
		main("switch")
		details([
			"temperature","mode", "jets",
            "heatSliderControl", "heatingSetpoint",
			"tempUp","tempDown", "switch"
		])
	}
}

def installed() {
	//set some defaults
    //sendEvent(name: "switch", value: "off")
    //sendEvent(name: "mode", value: "off")
	//sendEvent(name: "temperature", value: 72, unit: "F")
	//sendEvent(name: "heatingSetpoint", value: 100, unit: "F")
	//sendEvent(name: "jets", value: "off")
    //sendEvent(name: "heater", value: "off")
}

def parse(String description) {
}

def evaluate(temp, heatingSetpoint) {
	def threshold = 1.0
    def mode = device.currentState("mode")?.value
    log.debug "evaluate: current temp:$temp, heating point:$heatingSetpoint, switch=$mode"
    def idle = false
    def heating = false

	if (mode == "on") {
		if (heatingSetpoint - temp >= threshold) {
        	log.debug "Device: set heater on"
        	heaterOn()
			heating = true
            sendEvent(name: "mode", value: "heating")
		}
		else if (temp - heatingSetpoint >= threshold) {
	        log.debug "Device: set operating state idle (on)"
			idle = true
            heaterOff()
            sendEvent(name: "mode", value: "on")
		}
	}
	else {
		sendEvent(name: "thermostatSetpoint", value: heatingSetpoint)
	}
	if (idle && !heating) {
		sendEvent(name: "thermostatOperatingState", value: "on")
	}
}

def JetsOn () {
    sendEvent(name: "jets", value: "on")
}

def JetsOff () {
	log.debug "Device: set jets off"
    sendEvent(name: "jets", value: "off")
}

def off() {
    heaterOff()
    JetsOff()
	sendEvent(name: "switch", value: "off")
    sendEvent(name: "mode", value: "off")
}

def on() {
	sendEvent(name: "switch", value: "on")
    sendEvent(name: "mode", value: "on")
    def ts = device.currentState("heatingSetpoint")?.integerValue
    def temp = device.currentState("temperature")?.integerValue
    log.debug "on() - heatingSetpoint=$ts, temp=$temp"
    evaluate(temp, ts)

}

def poll() {
	null
}

def heaterOn() {
	//log.debug "Device: set heater on"
	sendEvent(name: "heater", value: "on")
    sendEvent(name: "mode", value: "heating")
    def temp = device.currentState("temperature")?.integerValue
    def ts = device.currentState("heatingSetpoint")?.integerValue
    log.debug "temp: $temp, heating set point: $ts"
    evaluate(temp, ts)
}

def heaterOff() {
	//log.debug "Device: set heater off"
	sendEvent(name: "heater", value: "off")
    sendEvent(name: "switch", value: "on")
}

def setHeatingSetpoint(Integer value) {
	//log.debug "setHeatingSetpoint($value)"
    value = normalizeHeatingSetPoint (value)
	sendEvent(name: "heatingSetpoint", value: value)
    evaluate(device.currentState("temperature")?.integerValue, value)
}

def tempUp() {
	def ts = device.currentState("heatingSetpoint")?.integerValue
    def value = ts + 1
    def temp = device.currentState("temperature")?.integerValue
    //log.debug "tempUp from $ts to $value. Current temp is $temp"
    value = normalizeHeatingSetPoint (value)
    sendEvent(name:"heatingSetpoint", value: value)
    evaluate(device.currentState("temperature")?.integerValue, value)
}

def tempDown() {
    def ts = device.currentState("heatingSetpoint")?.integerValue
	def value = ts - 1
    def temp = device.currentState("temperature")?.integerValue
    //log.debug "tempDown from $ts to $value. Current temp is $temp"
    value = normalizeHeatingSetPoint (value)
	sendEvent(name:"heatingSetpoint", value: value)
    evaluate(device.currentState("temperature")?.integerValue, value)
}

def setTemperature(number) {
	log.debug "setTemperature - $number"
    def ts = device.currentState("heatingSetpoint")?.integerValue
	//set h = device.currentValue("heatingSetpoint")
    log.debug "setTemperature($number)"
    log.debug "heatingSetpoint is $ts"
	sendEvent(name:"temperature", value: number)
    evaluate(number, ts)
}

def normalizeHeatingSetPoint (temp) {
	if (temp > 105) {
		temp = 105
	} else if (temp < 90) {
		temp = 90
	}
    return (temp)
}