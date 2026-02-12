package com.example.employeemanagementsystem.resources;

import com.example.employeemanagementsystem.auth.JwtUtil;
import com.example.employeemanagementsystem.services.UserServiceBean;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
    private UserServiceBean userService;

    @POST
    @Path("/login")
    public Response login(LoginRequest req) {
        if (!userService.isValidUser(req.username, req.password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String token = JwtUtil.generateToken(req.username, userService.getUserRole(req.username));
        return Response.ok("{\"token\":\"" + token + "\"}").build();
    }
}
