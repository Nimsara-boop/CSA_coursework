package com.mycompany.newcoursework;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nimsara
 */
@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                409, 
                "http://localhost:8080/NewCoursework/api/v1/"
                );
        
        return Response.status(Response.Status.CONFLICT)
                       .entity(errorMessage)
                       .type(MediaType.APPLICATION_JSON)
                       .build();
    }
}
