package com.example.demo.models.User;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "usuario")
public class Usuario {

    @Column(name = "nombre", nullable = false, length = 70)
    private String username;

    @Column(name = "primer_apellido", nullable = false, length = 60)
    private String firstname;

    @Column(name = "segundo_apellido", nullable = false, length = 60)
    private String lastname;

    @Column(name = "contraseña", nullable = false, length = 100)
    private String contraseña;

    @Column(name = "pais", nullable = false, length = 80)
    private String country;
}
