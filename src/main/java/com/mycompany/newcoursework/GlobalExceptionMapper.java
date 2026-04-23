/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.newcoursework;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Nimsara
 */
    @Provider
    public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

        @Override
        public Response toResponse(Throwable exception) {
            ErrorMessage errorMessage = new ErrorMessage( "Unexpected Error Occurred",                        // errorMessage
            500,                                                // errorCode
            "http://localhost:8080/NewCoursework/api/v1"       );

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(errorMessage)
                           .type(MediaType.APPLICATION_JSON)
                           .build();
        }
    }
