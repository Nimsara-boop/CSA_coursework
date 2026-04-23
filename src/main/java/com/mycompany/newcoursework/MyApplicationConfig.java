/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.newcoursework;

/**
 *
 * @author Nimsara
*/
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

//@ApplicationPath("/api/v1")
public class MyApplicationConfig extends ResourceConfig {

    public MyApplicationConfig() {
        packages("com.mycompany.newcoursework");
    }
}

