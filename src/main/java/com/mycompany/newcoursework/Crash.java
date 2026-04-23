/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.newcoursework;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Nimsara
 */

//----------For the Global Exception Testing    

public class Crash{
@GET
@Path("/crash")
public Response crash() {
    String s = null;
    return Response.ok(s.length()).build(); // NullPointerException
}
}