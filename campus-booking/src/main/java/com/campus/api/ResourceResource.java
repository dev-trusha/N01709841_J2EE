package com.campus.api;

import com.campus.model.Resource;
import com.campus.service.BookingStore;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/resources")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResourceResource {

    @Inject
    private BookingStore store;

    @GET
    public List<Resource> all() {
        return store.allResources();
    }

    @POST
    public Response create(@Context SecurityContext sc, Resource r) {
        if (!sc.isUserInRole("admin"))
            return Response.status(403).entity("Admin only").build();

        if (r == null || r.name == null || r.type == null)
            return Response.status(400).entity("name & type required").build();

        return Response.status(201).entity(store.createResource(r)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@Context SecurityContext sc, @PathParam("id") Long id, Resource r) {
        if (!sc.isUserInRole("admin")) return Response.status(403).entity("Admin only").build();
        Resource updated = store.updateResource(id, r);
        return updated == null ? Response.status(404).build() : Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@Context SecurityContext sc, @PathParam("id") Long id) {
        if (!sc.isUserInRole("admin")) return Response.status(403).entity("Admin only").build();
        return store.deleteResource(id) ? Response.noContent().build() : Response.status(404).build();
    }
}