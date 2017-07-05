/**
 *  Originally developed by Smartthings
 *  Adapted by luisfcosta@live.com to work with MQTT bridge and allow setting the device data from the MQTT bridge
 *  > New command set (device, attribute, value)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	// Automatically generated. Make future change here.
	definition (name: "vContact Sensor", namespace: "luisfcosta", author: "luisfocosta@live.com") {
		capability "Contact Sensor"
        capability "Refresh"
        capability "Sensor"

		command "open"
		command "close"
	}

	simulator {
		status "open": "contact:open"
		status "closed": "contact:closed"
	}

	tiles {
		standardTile("contact", "device.contact", width: 2, height: 2) {
			state("closed", label:'${name}', icon:"st.contact.contact.closed", backgroundColor:"#79b821")
			state("open", label:'${name}', icon:"st.contact.contact.open", backgroundColor:"#ffa81e")
		}
		main "contact"
		details "contact"
	}
}

def parse(String description) {
	def pair = description.split(":")
	createEvent(name: pair[0].trim(), value: pair[1].trim())
}

def open() {
	log.trace "vcontactsensor: open()"
	sendEvent(name: "contact", value: "open")
}

def close() {
	log.trace "vcontactsensor: close()"
    sendEvent(name: "contact", value: "closed")
}

def refresh() {
    log.info "refresh"
}