package com.steverado9.Clinic.management.system.entity;

import jakarta.persistence.*;

@Entity
public class ReceptionProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ReceptionProfile() {}

    public ReceptionProfile(String fullName, User user) {
        this.fullName = fullName;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
