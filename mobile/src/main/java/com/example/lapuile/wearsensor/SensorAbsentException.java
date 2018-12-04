package com.example.lapuile.wearsensor;

import android.hardware.Sensor;

public class SensorAbsentException extends NullPointerException {

    String msg;
    public SensorAbsentException(String message){
        this.msg= message;

    }


    public String getError() {
        return "The sensor that you request may be broken";
    }
}
