    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package com.mycompany.newcoursework;

    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.stream.Collectors; 
    import javax.ws.rs.Consumes;
    import javax.ws.rs.DELETE;
    import javax.ws.rs.GET;
    import javax.ws.rs.POST;
    import javax.ws.rs.Path;
    import javax.ws.rs.PathParam;
    import javax.ws.rs.Produces;
    import javax.ws.rs.QueryParam;
    import javax.ws.rs.core.MediaType;
    import javax.ws.rs.core.Response;

    /**
     *
     * @author Nimsara
     */
    @Path("/sensors/")
    public class SensorResource {
        public static ArrayList<Sensor> sensorList= new ArrayList<>();
        private static Map<String, Room> roomList = SensorRoomResource.roomlinks;
        protected static Map<String, List<SensorReading>> readings = new HashMap<>();

        public SensorResource() {}

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response addSensor(Sensor sensor) {
            if (roomList.containsKey(sensor.getRoomId())) {
                sensorList.add(sensor);
                SensorReading sreading = new SensorReading(sensor.getId(), LocalDateTime.now(), sensor.getCurrentValue());
                List<SensorReading> r = new ArrayList<>();
                r.add(sreading);
                readings.put(sensor.getId(), r);
                return Response.ok("added the sensor successfully").build();
            } else {
                throw new LinkedResourceNotFoundException(
            "Room '" + sensor.getRoomId() + "' does not exist. Register the room first."
        );
            }

        }
        /*
        @DELETE
        @Path("/{sensorID}")
            public Response deleteSensor(@PathParam("sensorId") String sensorId) {
            if (!roomList.containsKey(sensorId)) {
                return Response.ok("No such sensor ID or sensor has already been removed. ").build();
            } else {
                Room room = new Room();
                room.setSensorIds(sensorsIds);
                roomList.remove(sensorId);
                return Response.ok("No such room available to assign the sensor").build();
            }

        }
        */
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getSensors(
            @QueryParam("type")
            SensorType type){
            if (type==null){return Response.ok(sensorList).build();}
            ArrayList<Sensor> filtered = new ArrayList<>();
            for (Sensor s : sensorList){
                if (s.getType().equals(type)){
                    filtered.add(s);
                }
            }

            return Response.ok(filtered).build();
        }

        @Path("/{sensorId}/reading")
        public SensorReadingResource getSensorReading(@PathParam("sensorId") String sensorId){
            return new SensorReadingResource(sensorId);
        }

    }
