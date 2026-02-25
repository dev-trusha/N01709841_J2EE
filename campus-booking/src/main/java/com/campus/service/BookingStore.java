package com.campus.service;

import com.campus.model.Resource;
import com.campus.model.Reservation;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Startup
@Singleton
public class BookingStore {

    private final Map<Long, Resource> resources = new LinkedHashMap<>();
    private final Map<Long, Reservation> reservations = new LinkedHashMap<>();

    private final AtomicLong resId = new AtomicLong(0);
    private final AtomicLong bookId = new AtomicLong(0);

    // ----- Resource -----
    public synchronized List<Resource> allResources() {
        return new ArrayList<>(resources.values());
    }

    public synchronized Resource getResource(Long id) {
        return resources.get(id);
    }

    public synchronized Resource createResource(Resource r) {
        long id = resId.incrementAndGet();
        Resource x = new Resource();
        x.id = id;
        x.name = r.name;
        x.type = r.type;
        x.location = r.location;
        x.status = (r.status == null || r.status.isBlank()) ? "ACTIVE" : r.status;
        resources.put(id, x);
        return x;
    }

    public synchronized Resource updateResource(Long id, Resource in) {
        Resource x = resources.get(id);
        if (x == null) return null;
        x.name = in.name;
        x.type = in.type;
        x.location = in.location;
        x.status = in.status;
        return x;
    }

    public synchronized boolean deleteResource(Long id) {
        if (!resources.containsKey(id)) return false;
        // also cancel any future reservations if you want (optional)
        resources.remove(id);
        return true;
    }

    // ----- Reservation -----
    public synchronized List<Reservation> allReservations() {
        return new ArrayList<>(reservations.values());
    }

    public synchronized List<Reservation> reservationsForUser(String username) {
        List<Reservation> out = new ArrayList<>();
        for (Reservation r : reservations.values()) {
            if (username.equals(r.bookedBy)) out.add(r);
        }
        return out;
    }

    public synchronized boolean isAvailable(Long resourceId, String start, String end) {
        LocalDateTime s = LocalDateTime.parse(start);
        LocalDateTime e = LocalDateTime.parse(end);

        for (Reservation r : reservations.values()) {
            if (!"BOOKED".equals(r.status)) continue;
            if (!resourceId.equals(r.resourceId)) continue;

            LocalDateTime rs = LocalDateTime.parse(r.startTime);
            LocalDateTime re = LocalDateTime.parse(r.endTime);

            // overlap: rs < e && re > s
            if (rs.isBefore(e) && re.isAfter(s)) return false;
        }
        return true;
    }

    public synchronized Reservation createReservation(Long resourceId, String username, Reservation in) {
        Resource resource = resources.get(resourceId);
        if (resource == null) return null;

        if (!isAvailable(resourceId, in.startTime, in.endTime)) {
            throw new IllegalArgumentException("Resource already booked for this time range.");
        }

        long id = bookId.incrementAndGet();
        Reservation r = new Reservation();
        r.id = id;
        r.resourceId = resourceId;
        r.resourceName = resource.name;
        r.bookedBy = username;
        r.startTime = in.startTime;
        r.endTime = in.endTime;
        r.purpose = in.purpose;
        r.status = "BOOKED";

        reservations.put(id, r);
        return r;
    }

    public synchronized boolean cancelReservation(Long bookingId, String username, boolean isAdmin) {
        Reservation r = reservations.get(bookingId);
        if (r == null) return false;

        if (!isAdmin && !username.equals(r.bookedBy)) {
            throw new SecurityException("Not allowed to cancel this reservation.");
        }

        r.status = "CANCELLED";
        return true;
    }
}