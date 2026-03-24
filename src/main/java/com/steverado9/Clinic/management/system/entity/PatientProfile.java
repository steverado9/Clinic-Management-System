package com.steverado9.Clinic.management.system.entity;

import jakarta.persistence.*;

@Entity
public class PatientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PatientProfile() {}

    public PatientProfile(String fullName, String phoneNumber, User user) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
