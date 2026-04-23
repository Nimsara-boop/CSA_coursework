/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.newcoursework;

import static com.mycompany.newcoursework.Status.UNRECORDED;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;

/**
 *
 * @author Nimsara
 */
public class SensorReadingResource {

    private String sensorId;
    private ArrayList<Sensor> sensors = SensorResource.sensorList;
    public static Map<String, List<SensorReading>> sensorReadings = SensorResource.readings;

    public SensorReadingResource() {
    }

    public SensorReadingResource(String Id) {
        sensorId = Id;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchHistory() {
        for (Map.Entry<String, List<SensorReading>> entry : sensorReadings.entrySet()) {
            if (entry.getKey().equals(sensorId)) {
                return Response.ok(entry.getValue()).build();
            }
        }
        return Response.ok("No such sensor found").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewReading(SensorReading st) {
        for (Map.Entry<String, List<SensorReading>> entry : sensorReadings.entrySet()) {
            if (entry.getKey().equals(sensorId)) {
                entry.getValue().add(st);
                for (Sensor s : sensors) {
                    if (s.getId().equals(sensorId)) {
                        if (!(s.getStatus()==Status.MAINTENANCE)){
                        s.setCurrentValue(st.getValue());
                        break;}
                        else{
                        throw new SensorUnavailableException(
            "Sensor is under Maintenance. Cannot update value."
        );
                        }
                    }
                }
                    return Response.ok("status added").build();
            }
        }
        return Response.noContent().build();
    }

}
