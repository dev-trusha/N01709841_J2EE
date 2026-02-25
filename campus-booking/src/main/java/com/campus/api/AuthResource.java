package com.campus.api;

import com.campus.auth.JwtUtil;
import com.campus.service.UserServiceBean;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    public static class LoginRequest {
        public String username;
        public String password;
    }

    @Inject
    private UserServiceBean users;

    @POST
    @Path("/login")
    public Response login(LoginRequest req) {
        if (req == null || !users.isValidUser(req.username, req.password)) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Invalid credentials\"}").build();
        }
        String role = users.getRole(req.username);
        String token = JwtUtil.generateToken(req.username, role);
        return Response.ok("{\"token\":\"" + token + "\",\"role\":\"" + role + "\"}").build();
    }
}