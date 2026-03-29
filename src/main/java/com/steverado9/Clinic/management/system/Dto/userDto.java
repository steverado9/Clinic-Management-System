package com.steverado9.Clinic.management.system.Dto;


import com.steverado9.Clinic.management.system.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class userDto {

    private Long id;

    @NotEmpty(message = "email should not be empty")
    @Email
    private String email;

    @NotEmpty(message = "password should not be empty")
    private String password;

    @NotEmpty
    private Role role;

    public userDto() {};

    public userDto(String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
