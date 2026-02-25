package com.campus.model;

public class Reservation {
    public Long id;
    public Long resourceId;
    public String resourceName;

    public String bookedBy;   // username from token
    public String startTime;  // "yyyy-MM-ddTHH:mm"
    public String endTime;
    public String status;     // BOOKED/CANCELLED
    public String purpose;
}