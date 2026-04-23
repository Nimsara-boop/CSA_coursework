    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
package com.mycompany.newcoursework;

    /**
     *
     * @author Nimsara
     */
    import java.util.List;
    import java.util.ArrayList;

    public class Room {

        private String Id;
        private String name;
        private int capacity;
        private List<String> sensorIds = new ArrayList<>();


        public Room() {
        }

        public Room(String id, String n, int c, List<String> sensorsIds) {
            this.Id = id;
            name=n;
            capacity=c;
            this.sensorIds = sensorsIds;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public List<String> getSensorIds() {
            return sensorIds;
        }

        public void setSensorIds(List<String> sensorsIds) {
            this.sensorIds = sensorsIds;
        }

        public void addSensorId(Sensor sensor){
            this.sensorIds.add(sensor.getId());
        }

        public void setID(String id) {
            this.Id = id;
        }

        public String getID() {
            return this.Id;
        }
    }
