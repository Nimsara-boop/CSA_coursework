/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.newcoursework;

/**
 *
 * @author Nimsara
 */
public class Sensor {
    private String id;
    private SensorType type;
    private Status status;
    private double currentValue;
    private String roomId;
    
    public Sensor(){}
    
    public Sensor(String i, SensorType t, Status s, double v, String rID ){
        id=i;
        type=t;
        status=s;
        currentValue=v;
        roomId=rID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
            
    
    
}
