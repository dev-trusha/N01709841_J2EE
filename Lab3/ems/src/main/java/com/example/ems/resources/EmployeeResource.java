package com.example.ems.resources;

import com.example.ems.models.Employee;
import com.example.ems.services.EmployeeService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @Inject
    private EmployeeService employeeService;

    // ---------------- GET ALL ----------------
    @GET
    public List<Employee> getAllEmployees() {
        return employeeService.getAll();
    }

    // ---------------- GET BY ID ----------------
    @GET
    @Path("/{id}")
    public Response getEmployee(@PathParam("id") int id) {
        Employee emp = employeeService.getById(id);
        if (emp == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(emp).build();
    }

    // ---------------- CREATE ----------------
    @POST
    public Response addEmployee(@Context SecurityContext sc, Employee employee) {
        if (sc == null || !sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Admin only").build();
        }
        Employee created = employeeService.create(employee);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    // ---------------- UPDATE ----------------
    @PUT
    @Path("/{id}")
    public Response updateEmployee(@Context SecurityContext sc, @PathParam("id") int id, Employee employee) {
        if (sc == null || !sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Admin only").build();
        }
        Employee updated = employeeService.update(id, employee);
        if (updated == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(updated).build();
    }

    // ---------------- DELETE ----------------
    @DELETE
    @Path("/{id}")
    public Response deleteEmployee(@Context SecurityContext sc, @PathParam("id") int id) {
        if (sc == null || !sc.isUserInRole("admin")) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Admin only").build();
        }
        boolean deleted = employeeService.delete(id);
        if (!deleted) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.noContent().build();
    }
}
