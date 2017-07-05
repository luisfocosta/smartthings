
metadata {
	definition (name: "Computer controller", namespace: "luisfcosta", author: "luisfocosta@live.com") {
		capability "Switch"
		capability "Polling"
		capability "Refresh"
        
        command "turnon"
	}

	// UI tile definitions
	tiles {
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			//state "off", label: 'off', action: "switch.on", icon: "st.switches.light.off", backgroundColor: "#DDDDff", nextState: "turningOn"
			//state "on", label: 'on', action: "switch.off", icon: "st.switches.light.on", backgroundColor: "#0088ff", nextState: "turningOff"
			state "off", label: 'off', icon: "st.switches.light.off", backgroundColor: "#DDDDff", nextState: "turningOn"
			state "on", label: 'on', action: "turnon", icon: "st.switches.light.on", backgroundColor: "#0088ff", nextState: "turningOff"
			state "turningOff", label: 'Turning Off', action: "switch.on", icon: "st.switches.light.off", backgroundColor: "#0088ff"
			state "turningOn", label: 'Turning On', action: "switch.off", icon: "st.switches.light.on", backgroundColor: "#444488"
		}
		main "switch"
		details("switch")
	}
    preferences {
    input name: "Computer", type: "text", title: "Computer/device name", description: "Enter full command", required: true
    //input name: "textOFF", type: "text", title: "Command to turn device OFF", description: "Enter full command", required: true    
    }
}

def parse(String description) {
}

def on() {
	logger("Start On")
    sendEvent (name: "switch", "on")
}

def off() {
	logger("Start Off")
    sendEvent (name: "switch", "off")
}

def poll() {
		log.debug "Polling"
		refresh.refresh
}

//def private stCh(nwst) {
//	sendEvent(nwst)
//}

//def computeron () {
//	logger("Starting computer with command" + textOn)
//   sendEvent (name: "computer", textOn)
//}

//def computeroff () {
//    sendEvent (name: "computer", textOff)
//}

def turnon(){
	sendevent (name: "switch", "turnon")
}

def logger(txt) {
	log.debug "$txt"
}