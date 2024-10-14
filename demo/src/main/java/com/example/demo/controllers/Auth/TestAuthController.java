package com.example.demo.controllers.Auth;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.UserRegistrationDTO;
import com.example.demo.persistence.RoleEntity;
import com.example.demo.persistence.UserEntity;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.models.User.LoginRequest;


@RestController
@RequestMapping("/api/auth")
public class TestAuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/hello-segured")
    public String helloSegured() {
        return "Hello segured";
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserEntity user) {
        // Verificar si el usuario ya existe
        if (userRepository.findUserEntityByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("El usuario ya existe.");
        }

        // Codificar la contraseña antes de guardar el usuario
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Manejo de roles
        Set<RoleEntity> roles = new HashSet<>();
        for (RoleEntity role : user.getRoles()) {
            // Asegúrate de que cada rol existe en la base de datos, o crear uno nuevo
            roles.add(role);
        }
        user.setRoles(roles);

        // Guardar el usuario
        userRepository.save(user);

        return "Usuario registrado exitosamente.";
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Realiza la autenticación
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // Si la autenticación es exitosa, puedes acceder a los detalles del usuario autenticado
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Aquí podrías devolver la información del usuario o un mensaje de éxito
            return ResponseEntity.ok("Autenticación exitosa para el usuario: " + userDetails.getUsername());

        } catch (AuthenticationException e) {
            // Si la autenticación falla, devolver un error
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación: " + e.getMessage());
        }
    }
}