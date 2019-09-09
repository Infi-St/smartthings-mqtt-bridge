/**
 *  MQTT Bridge
 *
 *  Authors
 *   - st.john.johnson@gmail.com
 *   - jeremiah.wuenschel@gmail.com
 *
 *  Copyright 2016
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
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import groovy.transform.Field

// Massive lookup tree
@Field CAPABILITY_MAP = [
    "accelerationSensors": [
        name: "Acceleration Sensor",
        capability: "capability.accelerationSensor",
        attributes: [
            "acceleration"
        ]
    ],
	"airQualitySensors": [
		name: "AirQuality Sensor",
		capability: "capability.airQualitySensor",
		attributes: [
			"airQuality"
		]
	],
    "alarm": [
        name: "Alarm",
        capability: "capability.alarm",
        attributes: [
            "alarm"
        ],
        action: "actionAlarm"
    ],
    "battery": [
        name: "Battery",
        capability: "capability.battery",
        attributes: [
            "battery"
        ]
    ],
    "beacon": [
        name: "Beacon",
        capability: "capability.beacon",
        attributes: [
            "presence"
        ]
    ],
    "button": [
        name: "Button",
        capability: "capability.button",
        attributes: [
            "button"
        ]
    ],
    "carbonDioxideMeasurement": [
        name: "Carbon Dioxide Measurement",
        capability: "capability.carbonDioxideMeasurement",
        attributes: [
            "carbonDioxide"
        ]
    ],
    "carbonMonoxideDetector": [
        name: "Carbon Monoxide Detector",
        capability: "capability.carbonMonoxideDetector",
        attributes: [
            "carbonMonoxide"
        ]
    ],
    "colorControl": [
        name: "Color Control",
        capability: "capability.colorControl",
        attributes: [
            "hue",
            "saturation",
            "color"
        ],
        action: "actionColor"
    ],
    "colorTemperature": [
        name: "Color Temperature",
        capability: "capability.colorTemperature",
        attributes: [
            "colorTemperature"
        ],
        action: "actionColorTemperature"
    ],
    "consumable": [
        name: "Consumable",
        capability: "capability.consumable",
        attributes: [
            "consumable"
        ],
        action: "actionConsumable"
    ],
    "contactSensors": [
        name: "Contact Sensor",
        capability: "capability.contactSensor",
        attributes: [
            "contact"
        ]
    ],
    "doorControl": [
        name: "Door Control",
        capability: "capability.doorControl",
        attributes: [
            "door"
        ],
        action: "actionOpenClosed"
    ],
	"dustSensors": [
		name: "Dust Sensor",
		capability: "capability.dustSensor",
		attributes: [
			"fineDustLevel",
			"dustLevel"
		]
	],
    "energyMeter": [
        name: "Energy Meter",
        capability: "capability.energyMeter",
        attributes: [
            "energy"
        ]
    ],
    "garageDoors": [
        name: "Garage Door Control",
        capability: "capability.garageDoorControl",
        attributes: [
            "door"
        ],
        action: "actionOpenClosed"
    ],
    "illuminanceMeasurement": [
        name: "Illuminance Measurement",
        capability: "capability.illuminanceMeasurement",
        attributes: [
            "illuminance"
        ]
    ],
    "imageCapture": [
        name: "Image Capture",
        capability: "capability.imageCapture",
        attributes: [
            "image"
        ]
    ],
    "levels": [
        name: "Switch Level",
        capability: "capability.switchLevel",
        attributes: [
            "level"
        ],
        action: "actionLevel"
    ],
    "lock": [
        name: "Lock",
        capability: "capability.lock",
        attributes: [
            "lock"
        ],
        action: "actionLock"
    ],
    "mediaController": [
        name: "Media Controller",
        capability: "capability.mediaController",
        attributes: [
            "activities",
            "currentActivity"
        ]
    ],
    "motionSensors": [
        name: "Motion Sensor",
        capability: "capability.motionSensor",
        attributes: [
            "motion"
        ],
        action: "actionActiveInactive"
    ],
    "musicPlayer": [
        name: "Music Player",
        capability: "capability.musicPlayer",
        attributes: [
            "status",
            "level",
            "trackDescription",
            "trackData",
            "mute"
        ],
        action: "actionMusicPlayer"
    ],
    "pHMeasurement": [
        name: "pH Measurement",
        capability: "capability.pHMeasurement",
        attributes: [
            "pH"
        ]
    ],
    "powerMeters": [
        name: "Power Meter",
        capability: "capability.powerMeter",
        attributes: [
            "power"
        ]
    ],
    "presenceSensors": [
        name: "Presence Sensor",
        capability: "capability.presenceSensor",
        attributes: [
            "presence"
        ],
        action: "actionPresence"
    ],
    "humiditySensors": [
        name: "Relative Humidity Measurement",
        capability: "capability.relativeHumidityMeasurement",
        attributes: [
            "humidity"
        ]
    ],
    "relaySwitch": [
        name: "Relay Switch",
        capability: "capability.relaySwitch",
        attributes: [
            "switch"
        ],
        action: "actionOnOff"
    ],
    "sceneSwitch": [
        name: "Scene Switch",
        capability: "capability.switch",
        attributes: [
            "scene"
        ]
    ],
    "shockSensor": [
        name: "Shock Sensor",
        capability: "capability.shockSensor",
        attributes: [
            "shock"
        ]
    ],
    "signalStrength": [
        name: "Signal Strength",
        capability: "capability.signalStrength",
        attributes: [
            "lqi",
            "rssi"
        ]
    ],
    "sleepSensor": [
        name: "Sleep Sensor",
        capability: "capability.sleepSensor",
        attributes: [
            "sleeping"
        ]
    ],
    "smokeDetector": [
        name: "Smoke Detector",
        capability: "capability.smokeDetector",
        attributes: [
            "smoke"
        ]
    ],
    "soundSensor": [
        name: "Sound Sensor",
        capability: "capability.soundSensor",
        attributes: [
            "sound"
        ]
    ],
    "stepSensor": [
        name: "Step Sensor",
        capability: "capability.stepSensor",
        attributes: [
            "steps",
            "goal"
        ]
    ],
    "switches": [
        name: "Switch",
        capability: "capability.switch",
        attributes: [
            "switch"
        ],
        action: "actionOnOff"
    ],
    "soundPressureLevel": [
        name: "Sound Pressure Level",
        capability: "capability.soundPressureLevel",
        attributes: [
            "soundPressureLevel"
        ]
    ],
    "tamperAlert": [
        name: "Tamper Alert",
        capability: "capability.tamperAlert",
        attributes: [
            "tamper"
        ]
    ],
    "temperatureSensors": [
        name: "Temperature Measurement",
        capability: "capability.temperatureMeasurement",
        attributes: [
            "temperature"
        ]
    ],
    "thermostat": [
        name: "Thermostat",
        capability: "capability.thermostat",
        attributes: [
            "temperature",
            "heatingSetpoint",
            "coolingSetpoint",
            "thermostatSetpoint",
            "thermostatMode",
            "thermostatFanMode",
            "thermostatOperatingState"
        ],
        action: "actionThermostat"
    ],
    "thermostatCoolingSetpoint": [
        name: "Thermostat Cooling Setpoint",
        capability: "capability.thermostatCoolingSetpoint",
        attributes: [
            "coolingSetpoint"
        ],
        action: "actionCoolingThermostat"
    ],
    "thermostatFanMode": [
        name: "Thermostat Fan Mode",
        capability: "capability.thermostatFanMode",
        attributes: [
            "thermostatFanMode"
        ],
        action: "actionThermostatFan"
    ],
    "thermostatHeatingSetpoint": [
        name: "Thermostat Heating Setpoint",
        capability: "capability.thermostatHeatingSetpoint",
        attributes: [
            "heatingSetpoint"
        ],
        action: "actionHeatingThermostat"
    ],
    "thermostatMode": [
        name: "Thermostat Mode",
        capability: "capability.thermostatMode",
        attributes: [
            "thermostatMode"
        ],
        action: "actionThermostatMode"
    ],
    "thermostatOperatingState": [
        name: "Thermostat Operating State",
        capability: "capability.thermostatOperatingState",
        attributes: [
            "thermostatOperatingState"
        ]
    ],
    "thermostatSetpoint": [
        name: "Thermostat Setpoint",
        capability: "capability.thermostatSetpoint",
        attributes: [
            "thermostatSetpoint"
        ]
    ],
    "threeAxis": [
        name: "Three Axis",
        capability: "capability.threeAxis",
        attributes: [
            "threeAxis"
        ]
    ],
    "timedSession": [
        name: "Timed Session",
        capability: "capability.timedSession",
        attributes: [
            "timeRemaining",
            "sessionStatus"
        ],
        action: "actionTimedSession"
    ],
    "touchSensor": [
        name: "Touch Sensor",
        capability: "capability.touchSensor",
        attributes: [
            "touch"
        ]
    ],
    "ultravioletIndex": [
        name: "UV Index",
        capability: "capability.ultravioletIndex",
        attributes: [
            "ultravioletIndex"
        ]
    ],
    "valve": [
        name: "Valve",
        capability: "capability.valve",
        attributes: [
            "contact"
        ],
        action: "actionOpenClosed"
    ],
    "voltageMeasurement": [
        name: "Voltage Measurement",
        capability: "capability.voltageMeasurement",
        attributes: [
            "voltage"
        ]
    ],
    "waterSensors": [
        name: "Water Sensor",
        capability: "capability.waterSensor",
        attributes: [
            "water"
        ]
    ],
    "windowShades": [
        name: "Window Shade",
        capability: "capability.windowShade",
        attributes: [
            "windowShade"
        ],
        action: "actionOpenClosed"
    ]
]

definition(
    name: "MQTT Bridge",
    namespace: "stj",
    author: "St. John Johnson and Jeremiah Wuenschel",
    description: "A bridge between SmartThings and MQTT",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Connections/Cat-Connections.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Connections/Cat-Connections@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Connections/Cat-Connections@3x.png"
)

preferences {
    section("Send Notifications?") {
        input("recipients", "contact", title: "Send notifications to", multiple: true, required: false)
    }

    section ("Input") {
        CAPABILITY_MAP.each { key, capability ->
            input key, capability["capability"], title: capability["name"], multiple: true, required: false
        }
    }

    section ("Bridge") {
        input "bridge", "capability.notification", title: "Notify this Bridge", required: true, multiple: false
    }
}

def installed() {
    log.debug "Installed with settings: ${settings}"

    runEvery3Hours(initialize)
    initialize()
}

def updated() {

    log.debug "Updated with settings: ${settings}"

    // Unsubscribe from all events
    unsubscribe()
    // Subscribe to stuff
    initialize()
}

// Return list of displayNames
def getDeviceNames(devices) {
    def list = []
    devices.each{device->
        list.push(device.displayName)
    }
    list
}

def initialize() {
    // Subscribe to new events from devices
    unsubscribe()
    CAPABILITY_MAP.each { key, capability ->
    //log.debug "map to: ${key} ${capability}"
        capability["attributes"].each { attribute -> 

			if (settings[key] != null) {            	
            	subscribe(settings[key], attribute, inputHandler)
                settings[key].each { device ->   
                	//log.debug "Initialize: ${device.displayName}.${attribute} to ${device.currentValue(attribute).toString()}"
                	forwardEvent(device.displayName, device.currentValue(attribute).toString(), attribute)        
                }
            	//log.debug "Subscribing to: ${settings[key]} ${attribute}"
             }
             else {
             //log.debug "Not Subscribing to: ${settings[key]} ${attribute}"
             }
 
        }
    }
    subscribe(location, "routineExecuted", inputHandler)
    // Subscribe to events from the bridge
    subscribe(bridge, "message", bridgeHandler)

    // Update the bridge
    updateSubscription()
}

// Update the bridge"s subscription
def updateSubscription() {
    def attributes = [
        notify: ["Contacts", "System"],
        ExecuteRoutine: ["routine"]
    ]
    CAPABILITY_MAP.each { key, capability ->
        capability["attributes"].each { attribute ->
            if (!attributes.containsKey(attribute)) {
                attributes[attribute] = []
            }
            settings[key].each {device ->
                attributes[attribute].push(device.displayName)
            }
        }
    }
    
    def json = new groovy.json.JsonOutput().toJson([
        path: "/subscribe",
        body: [
            devices: attributes
        ]
    ])

    log.debug "Updating subscription: ${json}"

    bridge.deviceNotification(json)
}

// Receive an event from the bridge
def bridgeHandler(evt) {
//state.ignoreEvent = json

    def json = new JsonSlurper().parseText(evt.value)
    log.debug "Received device event from bridge: ${json}"
    log.debug "evt.name = ${evt.name} ${evt.description} ${evt.descriptionText}"
    log.debug "(JSON) = ${json.type} ${json.name} ${json.command} ${json.value}"
    if (json.type == "notify") {
        if (json.name == "Contacts") {
            sendNotificationToContacts("${json.value}", recipients)
        } else {
            sendNotificationEvent("${json.value}")
        }
        return
    }
//Received device event from bridge: [command:true, name:routine, type:ExecuteRoutine, value:Relax Alexa Skill]
//(JSON) = ExecuteRoutine routine true

	if (json.type == "ExecuteRoutine")
	{
    	def actions = location.helloHome?.getPhrases()*.label
        	actions.each { action ->
            log.debug "${action}"
            }
		log.debug "Calling location.helloHome?.execute(${json.value})"
        forwardEvent("routine", "executed", json.type)
		location.helloHome?.execute(json.value)
        forwardEvent("routine", "executed", json.type)
		return
	}
    
    // @NOTE this is stored AWFUL, we need a faster lookup table
    // @NOTE this also has no fast fail, I need to look into how to do that
    CAPABILITY_MAP.each { key, capability ->
        if (capability["attributes"].contains(json.type)) {
            settings[key].each {device ->
                if (device.displayName == json.name) {
                    if (json.command == false) {
                        if (device.getSupportedCommands().any {it.name == "setStatus"}) {
                            log.debug "Setting state ${json.type} = ${json.value}"
                            device.setStatus(json.type, json.value)
                            state.ignoreEvent = json
                        }
                    }
                    else {
                        if (capability.containsKey("action")) {
                            def action = capability["action"]
                            log.debug "Calling ${action} = {$json.value} on ${device}"
                            // Yes, this is calling the method dynamically
                            "$action"(device, json.type, json.value)
                            
                           //if (device.currentValue(json.type) != json.value) {
  							//	 log.debug "Retry1 Forward2 device = ${device.currentValue(json.type)}"
  							//}
  							//if (device.currentValue(json.type) != json.value) {
  							//	log.debug "Retry2 Forward2 device = ${device.currentValue(json.type)}"
                            //}
  							//forwardEvent(device.displayName, device.currentValue(json.type).toString(), json.type)
  							//log.debug "Forward2 device = ${device.currentValue(json.type)}"
                                                        
                        }
                        else{
                        log.debug "No Match found ${action} = {$json.value} on ${device}"
                        }
                    }
                }
            }
        }
    }
}

// Receive an event from a device
def inputHandler(evt) {

log.debug "evt = ${evt.displayName} ${evt.value} ${evt.name}"

    if (
        state.ignoreEvent
        && state.ignoreEvent.name == evt.displayName
        && state.ignoreEvent.type == evt.name
        && state.ignoreEvent.value == evt.value
    ) {
        log.debug "Ignoring event ${state.ignoreEvent}"
        state.ignoreEvent = false;
    }
    else {
        def json = new JsonOutput().toJson([
            path: "/push",
            body: [
                name: evt.displayName,
                value: evt.value,
                type: evt.name
            ]
        ])

        log.debug "Forwarding device event to bridge: ${json}"
        bridge.deviceNotification(json)
    }
}

def forwardEvent(displayName, value, name) {
def json = new JsonOutput().toJson([
            path: "/push",
            body: [
                name: displayName,
                value: value,
                type: name
            ]
        ])
        log.debug "Forwarding2 device event to bridge: ${json}"
        bridge.deviceNotification(json)
}

// +---------------------------------+
// | WARNING, BEYOND HERE BE DRAGONS |
// +---------------------------------+
// These are the functions that handle incoming messages from MQTT.
// I tried to put them in closures but apparently SmartThings Groovy sandbox
// restricts you from running clsures from an object (it's not safe).

def actionAlarm(device, attribute, value) {
    switch (value) {
        case "strobe":
            device.strobe()
        break
        case "siren":
            device.siren()
        break
        case "off":
            device.off()
        break
        case "both":
            device.both()
        break
    }
    forwardEvent(device.displayName, device.currentValue(attribute).toString(), attribute)
}

def actionColor(device, attribute, value) {
    switch (attribute) {
        case "hue":
            device.setHue(value as float)
        break
        case "saturation":
            device.setSaturation(value as float)
        break
        case "color":
            def values = value.split(',')
            def colormap = ["hue": values[0] as float, "saturation": values[1] as float]
            device.setColor(colormap)
        break
    }
}

def actionOpenClosed(device, attribute, value) {

	if (device.currentValue(attribute) == value) 
    	return   
    if (value == "open") {
       	device.open()
    } else if (value == "closed") {
       	device.close()
    }
	forwardEvent(device.displayName, value, attribute)
}

def actionOnOff(device, attribute, value) {
	if (device.currentValue(attribute) == value) 
    	return   
    if (value == "off") {
        device.off()
    } else if (value == "on") {
        device.on()
    }    
    forwardEvent(device.displayName, value, attribute)
}

def actionActiveInactive(device, attribute, value) {
	if (device.currentValue(attribute) == value) 
    	return   
    if (value == "active") {
        device.active()
    } else if (value == "inactive") {
        device.inactive()
    }
    forwardEvent(device.displayName, value, attribute)
}

def actionThermostat(device, attribute, value) {
    switch(attribute) {
        case "heatingSetpoint":
            device.setHeatingSetpoint(value)
        break
        case "coolingSetpoint":
            device.setCoolingSetpoint(value)
        break
        case "thermostatMode":
            device.setThermostatMode(value)
        break
        case "thermostatFanMode":
            device.setThermostatFanMode(value)
        break
    }
    forwardEvent(device.displayName, device.currentValue(attribute).toString(), attribute)
}

def actionMusicPlayer(device, attribute, value) {
    log.debug "actionMusicPlayer: ${attribute} ${value}"
    switch(attribute) {
        case "level":
            device.setLevel(value)
        break
        case "mute":
            if (value == "muted") {
                device.mute()
            } else if (value == "unmuted") {
                device.unmute()
            }
        break
        case "status":
            if (device.getSupportedCommands().any {it.name == "setStatus"}) {
                device.setStatus(value)
            }
        break
    }
    forwardEvent(device.displayName, device.currentValue(attribute).toString(), attribute)
}

def actionColorTemperature(device, attribute, value) {
    device.setColorTemperature(value as int)
}

def actionLevel(device, attribute, value) {
    device.setLevel(value as int)
}

def actionPresence(device, attribute, value) {
	if (device.currentValue(attribute) == value) 
    	return   
    if (value == "present") {
    	device.arrived();
    }
    else if (value == "not present") {
    	device.departed();
    }
       forwardEvent(device.displayName, value, attribute)
}

def actionConsumable(device, attribute, value) {
    device.setConsumableStatus(value)
    forwardEvent(device.displayName, device.currentValue(attribute).toString(), attribute)
}

def actionLock(device, attribute, value) {
	if (device.currentValue(attribute) == value) 
    	return   
    if (value == "locked") {
        device.lock()
    } else if (value == "unlocked") {
        device.unlock()
    }
    forwardEvent(device.displayName, value, attribute)
}

def actionCoolingThermostat(device, attribute, value) {
	if (device.currentValue(attribute) == value) 
    	return   
    device.setCoolingSetpoint(value)
    forwardEvent(device.displayName, device.currentValue(attribute).toString(), attribute)
}

def actionThermostatFan(device, attribute, value) {
	if (device.currentValue(attribute) == value) 
    	return   
    device.setThermostatFanMode(value)
    forwardEvent(device.displayName, device.currentValue(attribute).toString(), attribute)
}

def actionHeatingThermostat(device, attribute, value) {
	if (device.currentValue(attribute) == value) 
    	return   
    device.setHeatingSetpoint(value)
    forwardEvent(device.displayName, device.currentValue(attribute).toString(), attribute)
}

def actionThermostatMode(device, attribute, value) {
	if (device.currentValue(attribute) == value) 
    	return   
    device.setThermostatMode(value)
    forwardEvent(device.displayName, device.currentValue(attribute).toString(), attribute)
}

def actionTimedSession(device, attribute, value) {
    if (attribute == "timeRemaining") {
        device.setTimeRemaining(value)
        forwardEvent(device.displayName, device.currentValue(attribute).toString(), attribute)
    }
}
