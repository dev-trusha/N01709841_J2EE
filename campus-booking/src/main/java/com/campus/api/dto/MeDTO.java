package com.campus.api.dto;

public class MeDTO {
    public String user;
    public boolean isAdmin;
    public String scheme;

    public MeDTO() {}
    public MeDTO(String user, boolean isAdmin, String scheme) {
        this.user = user;
        this.isAdmin = isAdmin;
        this.scheme = scheme;
    }
}