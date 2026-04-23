/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.newcoursework;

/**
 *
 * @author Nimsara
 */

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/") 
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscovery() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("version", "1.0");
        metadata.put("contact", "admin@example.com");
        
        Map<String, String> links = new HashMap<>();
        links.put("rooms", "/NewCoursework/api/v1/rooms");
        links.put("sensors", "/NewCoursework/api/v1/sensors");
        metadata.put("collections", links);

        return Response.ok(metadata).build();
    }
}
