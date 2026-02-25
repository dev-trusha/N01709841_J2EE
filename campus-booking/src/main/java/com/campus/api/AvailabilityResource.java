package com.campus.api;

import com.campus.service.BookingStore;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;

@Path("/availability")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AvailabilityResource {

    @Inject
    private BookingStore store;

    @GET
    public Map<String, Object> check(@QueryParam("resourceId") Long resourceId,
                                     @QueryParam("start") String start,
                                     @QueryParam("end") String end) {
        boolean ok = store.isAvailable(resourceId, start, end);
        return Map.of("resourceId", resourceId, "start", start, "end", end, "available", ok);
    }
}