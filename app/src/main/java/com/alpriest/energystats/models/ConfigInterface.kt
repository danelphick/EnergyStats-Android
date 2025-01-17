package com.alpriest.energystats.models

interface ConfigInterface {
    var showUsableBatteryOnly: Boolean
    var showBatteryEstimate: Boolean
    var showSunnyBackground: Boolean
    var selectedDeviceID: String?
    var devices: String?
    var refreshFrequency: Int
    var showBatteryTemperature: Boolean
    var useColouredFlowLines: Boolean
    var useLargeDisplay: Boolean
    var isDemoUser: Boolean
    var decimalPlaces: Int
}