package com.example.employeemanagementsystem.app;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class EmployeemanagementSystemApp extends Application {
    // Scans for @Path-annotated classes in the package
}