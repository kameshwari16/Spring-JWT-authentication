package com.example.springsecurityjwt.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    public String getFirstname() {
        return firstName;
    }

    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }
}
