metadata {
    definition (name:"vHotTubTempsensor", namespace:"luisfcosta", author:"luisfocosta@live.com") {
        capability "Temperature Measurement"
        //capability "Battery"
        capability "Relative Humidity Measurement"
        capability "Sensor"
        capability "Refresh"
        attribute "LastReport", "String"

        // custom commands
        command "parse"     // (String "temperature:<value>")
        command "set"
    }

    tiles {
        valueTile("temperature", "device.temperature", width: 2, height: 2) {
            state("temperature", label:'${currentValue}°', unit:"F",
                backgroundColors:[
                    [value: 30, color: "#153591"],
                    [value: 68, color: "#1e9cbb"],
                    [value: 86, color: "#90d2a7"],
                    [value: 93, color: "#44b621"],
                    [value: 97, color: "#f1d801"],
                    [value: 100, color: "#d04e00"],
                    [value: 104, color: "#bc2323"]
                ]
            )
       }
        valueTile("lastreport", "device.LastReport", width: 2, height: 1) {
			state "", label:'Reported on ${currentValue}', unit:""
		}
        valueTile("battery", "device.battery", decoration: "flat", width: 1, height: 1) {
			state "battery", label:'${currentValue} battery', unit:""
		}

        main(["temperature"])
        details(["temperature", "lastreport", "battery"])
    }

    simulator {
        for (int i = 20; i <= 110; i += 10) {
            status "Temperature ${i}°": "temperature:${i}"
        }
        status "Invalid message" : "foobar:100.0"
    }
}

def set(attribute, value) {
    TRACE("vPoolsensor: setting ${attribute} to ${value}")
    sendEvent(name: "${attribute}", value: "${value}")
    UpdateLastReport()
}

private def TRACE(message) {
    log.debug message
}

def refresh() {
    log.debug "refreshed"
    UpdateLastReport()
}

def UpdateLastReport () {
	def nowTime = new Date(now()).format("yyyy-MM-dd HH:mm", location.timeZone)
    log.debug "$nowTime updated"
    sendEvent(name: "LastReport", value: "${nowTime}")
}