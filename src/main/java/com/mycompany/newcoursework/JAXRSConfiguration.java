package com.mycompany.newcoursework;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 *
 * @author Juneau
 */
//@ApplicationPath("/api/v1")
public class JAXRSConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(DiscoveryResource.class);
        classes.add(SensorReadingResource.class);
        classes.add(SensorResource.class);
        classes.add(SensorRoomResource.class);
        classes.add(LinkedResourceNotFoundExceptionMapper.class); 
        classes.add(SensorUnavailableExceptionMapper.class); 
        classes.add(RoomNotEmptyExceptionMapper.class); 
        classes.add(GlobalExceptionMapper.class);
        return classes;
    }
}
