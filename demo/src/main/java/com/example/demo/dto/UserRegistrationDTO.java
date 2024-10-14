package com.example.demo.dto;

import java.util.Set;

import com.example.demo.persistence.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    
    private String username;
    private String password;
    private String country;
    private String email;
    private String firstname;
    private String lastname;
    private Set<RoleEnum> roles;
}
