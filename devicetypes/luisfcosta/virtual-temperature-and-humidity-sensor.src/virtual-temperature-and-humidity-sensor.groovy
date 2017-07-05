metadata {
    definition (name:"Virtual temperature and humidity sensor", namespace:"luisfcosta", author:"luisfcosta@live.com") {
        capability "Temperature Measurement"
        capability "Relative Humidity Measurement"
        capability "Sensor"
        capability "Refresh"

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
        valueTile("humidity", "device.humidity", width: 2, height: 2) {
			state "humidity", label:'${currentValue}% humidity', unit:"%"
		}

        main(["temperature", "humidity"])
        details(["temperature", "humidity"])
    }

    simulator {
        for (int i = 20; i <= 110; i += 10) {
            status "Temperature ${i}°": "temperature:${i}"
        }
        status "Invalid message" : "foobar:100.0"
    }
}

//def parse(String message) {
//    TRACE("parse(${message})")

//    Map msg = stringToMap(message)
//    if (!msg.containsKey("temperature")) {
//        log.error "Invalid message: ${message}"
//        return null
//    }

//    Float temp = msg.temperature.toFloat()
//    def event = [
//        name  : "temperature",
//        value : temp.round(1),
//        unit  : tempScale,
//    ]

//    TRACE("event: (${event})")
//    sendEvent(event)
//}

def set(attribute, value) {
    TRACE("vDTH sensor: Setting the ${attribute} to ${value}")
    sendEvent(name: "${attribute}", value: "${value}")
}

private def TRACE(message) {
    log.debug message
}

def refresh() {
    log.info "refresh"
}