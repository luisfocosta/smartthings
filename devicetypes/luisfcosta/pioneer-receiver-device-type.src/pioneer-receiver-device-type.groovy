/**
 *  SmartThings Device Handler: Pioneer Receiver VSX 822K
 *
 *  Author: redloro@gmail.com
 *  Adapted by Luis Costa, luisfcosta@live.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 */
metadata {
  definition (name: "Pioneer receiver device type", namespace: "luisfcosta", author: "luisfcosta@live.com") {

    /**
     * List our capabilties. Doing so adds predefined command(s) which
     * belong to the capability.
     */
    capability "Music Player"
    capability "Switch"
    capability "Switch Level"
    capability "Refresh"
    capability "Polling"
    //capability "Sensor"
    capability "Actuator"

    /**
     * Define all commands, ie, if you have a custom action not
     * covered by a capability, you NEED to define it here or
     * the call will not be made.
     *
     * To call a capability function, just prefix it with the name
     * of the capability, for example, refresh would be "refresh.refresh"
     */
    command "function0"
    command "function1"
    command "function2"
    command "function4"
    command "function5"
    //command "mutedOn"
    command "mute"
    //command "mutedOff"
    command "partyModeOn"
    command "partyModeOff"
    command "zone"
    command "commandint"
    //command "mute" // as per MQTT bridge
    command "function" // new MQTT bridge
    //command "switch" // MQTT bridge
  }

  /**
   * Define the various tiles and the states that they can be in.
   * The 2nd parameter defines an event which the tile listens to,
   * if received, it tries to map it to a state.
   *
   * You can also use ${currentValue} for the value of the event
   * or ${name} for the name of the event. Just make SURE to use
   * single quotes, otherwise it will only be interpreted at time of
   * launch, instead of every time the event triggers.
   */
  tiles(scale: 2) {
    //multiAttributeTile(name:"state", type:"lighting", width:6, height:4) {
      //tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
        //attributeState "on", label:'On', action:"switch.off", icon:"st.Electronics.electronics16", backgroundColor:"#79b821", nextState:"off"
        //attributeState "off", label:'Off', action:"switch.on", icon:"st.Electronics.electronics16", backgroundColor:"#ffffff", nextState:"on"
      //}
      //tileAttribute ("source", key: "SECONDARY_CONTROL") {
        //attributeState "source", label:'${currentValue}'
      //}
    //}

    // row
    //controlTile("volume", "device.volume", "slider", height: 1, width: 6, range:"(0..100)") {
    //controlTile("level", "device.level", "slider", height: 2, width: 6, range:"(0..100)") {
      //state "levelvalue", label: "Volume", action:"switch level.setLevel", backgroundColor:"#00a0dc"
    //}
    
    multiAttributeTile(name: "switch", type: "lighting", width: 6, height: 4, canChangeIcon: true, canChangeBackground: true) {
			tileAttribute("device.switch", key: "PRIMARY_CONTROL") {
    			attributeState "off", label: '${name}', action: "switch.on", icon: "st.Electronics.electronics16", backgroundColor: "#ffffff", nextState: "turningOn"
		      	attributeState "on", label: '${name}', action: "switch.off", icon: "st.Electronics.electronics16", backgroundColor: "#79b821", nextState: "turningOff"
				attributeState "turningOff", label: '${name}', action: "switch.on", icon: "st.Electronics.electronics16", backgroundColor: "#ffffff", nextState: "turningOn"
		      	attributeState "turningOn", label: '${name}', action: "switch.off", icon: "st.Electronics.electronics16", backgroundColor: "#79b821", nextState: "turningOff"
        	}
        		tileAttribute("device.level", key: "SLIDER_CONTROL") {
            		attributeState "level", action:"switch level.setLevel"
        		}
        		//tileAttribute("level", key: "SECONDARY_CONTROL") {
              		//attributeState "level", label: 'Volume:${currentValue}%'
        		//}    
		}

    // row
    standardTile("0", "device.function0", decoration: "flat", width: 2, height: 2) {
      state("off", label:"Amazon Echo", action:"function0", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-gray.png", backgroundColor:"#ffffff")
      state("on", label:"Amazon Echo", action:"function0", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-green.png", backgroundColor:"#ffffff")
    }
    standardTile("1", "device.function1", decoration: "flat", width: 2, height: 2) {
      state("off", label:"Apple TV", action:"function1", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-gray.png", backgroundColor:"#ffffff")
      state("on", label:"Apple TV", action:"function1", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-green.png", backgroundColor:"#ffffff")
    }
    standardTile("2", "device.function2", decoration: "flat", width: 2, height: 2) {
      state("off", label:"Cable TV", action:"function2", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-gray.png", backgroundColor:"#ffffff")
      state("on", label:"Cable TV", action:"function2", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-green.png", backgroundColor:"#ffffff")
    }
    standardTile("3", "device.function3", decoration: "flat", width: 2, height: 2) {
      state("off", label:"Amazon TV", action:"function3", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-gray.png", backgroundColor:"#ffffff")
      state("on", label:"Amazon TV", action:"function3", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-green.png", backgroundColor:"#ffffff")
    }
    standardTile("4", "device.function4", decoration: "flat", width: 2, height: 2) {
      state("off", label:"Chromecast TV", action:"function4", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-gray.png", backgroundColor:"#ffffff")
      state("on", label:"Chromecast TV", action:"function4", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-green.png", backgroundColor:"#ffffff")
    }
    standardTile("5", "device.function5", decoration: "flat", width: 2, height: 2) {
      state("off", label:"Airplay", action:"function5", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-gray.png", backgroundColor:"#ffffff")
      state("on", label:"Airplay", action:"function5", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-green.png", backgroundColor:"#ffffff")
    }

    // row
    standardTile("mute", "device.mute", decoration: "flat", width: 2, height: 2) {
      state("off", label:'Muted', action:"muteOn", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-gray.png", backgroundColor:"#ffffff", nextState:"on")
      state("on", label:'Muted', action:"muteOff", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-mute.png", backgroundColor:"#ffffff", nextState:"off")
    }
    //standardTile("partyMode", "device.partyMode", decoration: "flat", width: 2, height: 2, inactiveLabel: false) {
    //  state("off", label:'Party Mode', action:"partyModeOn", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-gray.png", backgroundColor:"#ffffff", nextState:"on")
    //  state("on", label:'Party Mode', action:"partyModeOff", icon:"https://raw.githubusercontent.com/redloro/smartthings/master/images/indicator-dot-party.png", backgroundColor:"#ffffff", nextState:"off")
    //}
    standardTile("refresh", "device.status", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
      state "default", label:"Refresh", action:"refresh.refresh", icon:"st.secondary.refresh-icon", backgroundColor:"#ffffff"
    }

    // Defines which tile to show in the overview
    main "switch"

    // Defines which tile(s) to show when user opens the detailed view
    details([
      "switch",
      //"level",
      "0","1","2","3","4","5",
      "mute", //"partyMode",
      "refresh"
    ])
  }

  preferences {
    input name: "functionname0", type: "text", title: "Function 0 name", defaultValue: "Amazon Echo/CD"
    input name: "function0", type: "text", title: "Function 0 command", defaultValue: "01FN"
    input name: "functionname1", type: "text", title: "Function 1 name", defaultValue: "Apple TV/BD"
    input name: "function1", type: "text", title: "Function 1 command", defaultValue: "25FN"
    input name: "functionname2", type: "text", title: "Function 2 name", defaultValue: "Cable TV"
    input name: "function2", type: "text", title: "Function 2 command", defaultValue: "06FN"
    input name: "functionname3", type: "text", title: "Function 3 name", defaultValue: "Amazon TV/DVR"
    input name: "function3", type: "text", title: "Function 3 command", defaultValue: "15FN"
    input name: "functionname4", type: "text", title: "Function 4 name", defaultValue: "Chromecast/DVD"
    input name: "function4", type: "text", title: "Function 4 command", defaultValue: "04FN"
    input name: "functionname5", type: "text", title: "Function 5 name", defaultValue: "Airplay"
    input name: "function5", type: "text", title: "Function 5 command", defaultValue: "46FN"
  }
}

/**************************************************************************
 * The following section simply maps the actions as defined in
 * the metadata into onAction() calls.
 *
 * This is preferred since some actions can be dealt with more
 * efficiently this way. Also keeps all user interaction code in
 * one place.
 *
 */
def on() {
  sendEvent(name: "switch", value: "on")
  //sendEvent(name: "power", value: "on")
  //sendEvent(name: "command", value: 'PO')
}
def off() {
  sendEvent(name: "switch", value: "off")
  //sendEvent(name: "power", value: "off")
  //sendEvent(name: "command", value: 'PF')
}

def function0() {
  setFunction(0)
}
def function1() {
  setFunction(1)
}
def function2() {
  setFunction(2)
}
def function3() {
  setFunction(3)
}
def function4() {
  setFunction(4)
}
def function5() {
  setFunction(5)
}

def mute(val) {
  debug.log "Audio receiver: Received MQTT mute command:" + val
  if (val == 'on') {
  	sendEvent(name: "command", value: 'MO')
    sendEvent(name: "muteOn", value: val)
  } else if (val == 'off') {
  	sendEvent(name: "command", value: 'MF')
    sendEvent(name: "muteOff", value: val)
  }
}

def muteOn() {
  debug.log "Audio receiver: Received device mute ON"
  sendEvent(name: "mute", value: "on")

}
def muteOff() {
  debug.log "Audio receiver: Received device mute OFF"
  sendEvent(name: "mute", value: "off")
}

def refresh() {
  //sendCommand("<YAMAHA_AV cmd=\"GET\"><${getZone()}><Basic_Status>GetParam</Basic_Status></${getZone()}></YAMAHA_AV>")
  //sendCommand("<YAMAHA_AV cmd=\"GET\"><System><Party_Mode><Mode>GetParam</Mode></Party_Mode></System></YAMAHA_AV>")
}
/**************************************************************************/

/**
 * Called every so often (every 5 minutes actually) to refresh the
 * tiles so the user gets the correct information.
 */
def poll() {
  refresh()
}

def parse(String description) {
  return
}

def function (val) {
//This will come from the MQTT bridge to adjust the device accordingly
  log.debug "Audio receiver: MQTT bridge set changing function to: " + val
  sendEvent (name: "command", value: val)
  setSourceTile(val)
}

def setLevel (val) {
   if (val < 0){
    	val = 0
    }
    
    if( val > 100){
    	val = 100
    }
    
    log.debug "Audio receiver: set volume to: " + val

    if (val == 0){ 
    	sendEvent(name:"level",value:val)
    }
    else
    {
    	//sendEvent(name:"setLevel",value:val)
        sendEvent(name:"level",value:val)
    	sendEvent(name:"switch.setLevel",value:val)
    }
  //sendEvent (name: "volume", value: val)
}

def setFunction(id) {
  log.debug "Audio receiver: Changing function to: "+ getFunctionName(id)
  sendEvent (name: "function", value: getFunctionName(id))
  setSourceTile(getFunctionName(id))
}

def getFunctionName(id) {
  if (settings) {
  switch (id) {
  	case "0": 
    	return settings.functionname0
        break
  	case "1": 
    	return settings.functionname1
        break
  	case "2": 
    	return settings.functionname2
        break
  	case "3": 
    	return settings.functionname3
        break
   	case "4": 
    	return settings.functionname4
        break
  	case "5": 
    	return settings.functionname5
        break
  }
  } else {
    return ['AV1', 'AV2', 'AV3', 'AV4', 'AV5', 'AV6'].get(id)
  }
}

def getFunctionCommand(id) {
  if (settings) {
    switch (id) {
  	case "0": 
    	return settings.function0
        break
  	case "1": 
    	return settings.function1
        break
  	case "2": 
    	return settings.function2
        break
  	case "3": 
    	return settings.function3
        break
   	case "4": 
    	return settings.function4
        break
  	case "5": 
    	return settings.function5
        break
    }
  } else {
    return ['AV1', 'AV2', 'AV3', 'AV4', 'AV5', 'AV6'].get(id)
  }
}

def setSourceTile(name) {
  sendEvent(name: "source", value: 'Source: ${name}')
  for (def i = 0; i < 6; i++) {
    if (name == getFunctionName(i)) {
      sendEvent(name: 'function${i}', value: "on")
    }
    else {
      sendEvent(name: 'function${i}', value: "off")
    }
  }
}

def zone(evt) {
  /*
  * Zone On/Off
  */
  if (evt.Basic_Status.Power_Control.Power.text()) {
    sendEvent(name: "switch", value: (evt.Basic_Status.Power_Control.Power.text() == "On") ? "on" : "off")
  }

  /*
  * Zone Volume
  */
  if (evt.Basic_Status.Volume.Lvl.Val.text()) {
    def int volLevel = evt.Basic_Status.Volume.Lvl.Val.toInteger() ?: -250
    //sendEvent(name: "volume", value: ((volLevel + 800) / 9).intValue())
    sendEvent(name: "volume", value: (volLevel / 10).intValue())
  }

  /*
  * Zone Muted
  */
  if (evt.Basic_Status.Volume.Mute.text()) {
    sendEvent(name: "muted", value: (evt.Basic_Status.Volume.Mute.text() == "On") ? "on" : "off")
  }

  /*
  * Zone Source
  */
  if (evt.Basic_Status.Input.Input_Sel.text()) {
    setSourceTile(evt.Basic_Status.Input.Input_Sel.text())
  }

  /*
  * Party Mode
  */
  if (evt.Party_Mode.Mode.text()) {
    sendEvent(name: "partyMode", value: (evt.Party_Mode.Mode.text() == "On") ? "on" : "off")
  }
}

private sendCommand(body) {
  parent.sendCommand(body)
}

private getZone() {
  return new String(device.deviceNetworkId).tokenize('|')[2]
}