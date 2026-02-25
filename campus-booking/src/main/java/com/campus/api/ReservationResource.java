package com.campus.api;

import com.campus.model.Reservation;
import com.campus.service.BookingStore;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationResource {

    @Inject
    private BookingStore store;

    @GET
    public List<Reservation> list(@Context SecurityContext sc) {
        String username = sc.getUserPrincipal().getName();
        boolean isAdmin = sc.isUserInRole("admin");
        return isAdmin ? store.allReservations() : store.reservationsForUser(username);
    }

    @POST
    public Response create(@Context SecurityContext sc,
                           @QueryParam("resourceId") Long resourceId,
                           Reservation req) {
        if (resourceId == null) return Response.status(400).entity("resourceId required").build();
        String username = sc.getUserPrincipal().getName();

        try {
            Reservation created = store.createReservation(resourceId, username, req);
            if (created == null) return Response.status(404).entity("Resource not found").build();
            return Response.status(201).entity(created).build();
        } catch (IllegalArgumentException ex) {
            return Response.status(409).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Path("/{id}/cancel")
    public Response cancel(@Context SecurityContext sc, @PathParam("id") Long id) {
        String username = sc.getUserPrincipal().getName();
        boolean isAdmin = sc.isUserInRole("admin");

        try {
            return store.cancelReservation(id, username, isAdmin)
                    ? Response.ok().build()
                    : Response.status(404).build();
        } catch (SecurityException ex) {
            return Response.status(403).entity(ex.getMessage()).build();
        }
    }
}