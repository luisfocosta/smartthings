metadata {
    definition (name:"Virtual humidity sensor", namespace:"luisfcosta", author:"luisfcosta@live.com") {
        capability "Relative Humidity Measurement"
        capability "Sensor"
        capability "Refresh"
        attribute "LastReport", "String"

        // custom commands
        command "parse"     // (String "temperature:<value>")
        command "set"
    }

    tiles {

        valueTile("humidity", "device.humidity", width: 2, height: 2) {
			state "humidity", label:'${currentValue}% humidity', unit:"%"
		}
        
        valueTile("lastreport", "device.LastReport", width: 2, height: 1) {
			state "", label:'Reported on ${currentValue}', unit:""
		}

        main(["humidity"])
        details(["humidity","lastreport"])
    }

    simulator {
    }
}


def set(attribute, value) {
    //TRACE("vDTH sensor: Setting the ${attribute} to ${value}")
    sendEvent(name: "${attribute}", value: "${value}")
    UpdateLastReport()
}

private def TRACE(message) {
    log.debug message
}

def refresh() {
    log.info "refresh"
    UpdateLastReport()
}

def UpdateLastReport () {
	def nowTime = new Date(now()).format("yyyy-MM-dd HH:mm", location.timeZone)
    log.debug "$nowTime updated"
    sendEvent(name: "LastReport", value: "${nowTime}")
}