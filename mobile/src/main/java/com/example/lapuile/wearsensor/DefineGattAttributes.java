package com.example.lapuile.wearsensor;

import java.util.HashMap;

public class DefineGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String BODY_SENSOR_LOCATION = "00002a38-0000-1000-8000-00805f9b34fb";
    public static String BATTERY_LEVEL = "00002a19-0000-1000-8000-00805f9b34fb";
    public static String BLOODY_PRESSURE = "00002a35-0000-1000-8000-00805f9b34fb";

    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    public static String heartRate = "Heart Rate Service";

    static {
        // Sample Services.

        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        attributes.put("0000180f-0000-1000-8000-00805f9b34fb", "Battery Service");
        attributes.put("00001810-0000-1000-8000-00805f9b34fb", "Bloody Pressure Service");
        //NOT IMPLEMENTED
        attributes.put("00001800-0000-1000-8000-00805f9b34fb", "Generic Access Service");
        attributes.put("0000181a-0000-1000-8000-00805f9b34fb", "Environmental sensing Service");
        attributes.put("00001808-0000-1000-8000-00805f9b34fb", "Glucose Service");
        attributes.put("00001809-0000-1000-8000-00805f9b34fb", "Health Thermometer Service");
        attributes.put("00001822-0000-1000-8000-00805f9b34fb", "Pulse Oximeter Service");
        attributes.put("E95D0753251D470AA062FA1922DFA9A8", "Accelerometer Service");
        attributes.put("00002800-0000-1000-8000-00805f9b34fb", "ACCELEROMETER");





        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a39-0000-1000-8000-00805f9b34fb", "Heart Rate Control Point");
        attributes.put(BODY_SENSOR_LOCATION, "Body Sensor Location");
        attributes.put(BATTERY_LEVEL, "Battery Level");
        attributes.put(BLOODY_PRESSURE, "Bloody Pressure");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
        attributes.put("00002a24-0000-1000-8000-00805f9b34fb", "Model Number String");
        attributes.put("00002a25-0000-1000-8000-00805f9b34fb", "Serial Number String");
        attributes.put("00002a28-0000-1000-8000-00805f9b34fb", "Software Revision String");
        attributes.put("00002a26-0000-1000-8000-00805f9b34fb", "Firmware Revision  String");
        attributes.put("00002a00-0000-1000-8000-00805f9b34fb", "Device name");
        attributes.put("00002a01-0000-1000-8000-00805f9b34fb", "Appearance");
        attributes.put("E95DCA4B251D470AA062FA1922DFA9A8", "Accelerometer Data");
        //NOT IMPLEMENTED

    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}

