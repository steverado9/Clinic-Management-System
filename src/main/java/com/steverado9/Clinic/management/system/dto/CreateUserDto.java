package com.steverado9.Clinic.management.system.dto;


import com.steverado9.Clinic.management.system.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class CreateUserDto {

    private Long id;

    @NotEmpty(message = "name should not be empty")
    private String fullName;

    @NotEmpty(message = "email should not be empty")
    @Email
    private String email;

    @NotEmpty(message = "password should not be empty")
    private String password;

    @NotEmpty
    private Role role;

    @NotEmpty(message = "specialization should not be empty")
    private String specialization;

    public CreateUserDto() {};

    public CreateUserDto(Long id, String fullName, String email, String password, Role role, String specialization) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.specialization = specialization;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
