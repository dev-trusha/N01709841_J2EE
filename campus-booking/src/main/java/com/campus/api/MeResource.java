package com.campus.api;

import com.campus.api.dto.MeDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/me")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MeResource {

    @GET
    public MeDTO me(@Context SecurityContext sc) {
        String user = (sc.getUserPrincipal() == null) ? null : sc.getUserPrincipal().getName();
        return new MeDTO(user, sc.isUserInRole("admin"), sc.getAuthenticationScheme());
    }
}