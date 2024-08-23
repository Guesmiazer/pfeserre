package com.example.serreagricole.entitie;

public class LoginResponse {
    private int id;
    private Role role;

    public LoginResponse(int id, Role role) {
        this.id = id;
        this.role = role;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
