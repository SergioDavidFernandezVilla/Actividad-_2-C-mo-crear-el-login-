package com.example.demo;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.Repository.UserRepository;
import com.example.demo.persistence.PermissionEntity;
import com.example.demo.persistence.RoleEntity;
import com.example.demo.persistence.RoleEnum;
import com.example.demo.persistence.UserEntity;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean
CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder){
    return args -> {

        /* PERMISOS */
        PermissionEntity createPermission = PermissionEntity.builder()
        .name("CREAR")
        .build();

        PermissionEntity readPermission = PermissionEntity.builder()
        .name("LEER")
        .build();

        PermissionEntity editPermission = PermissionEntity.builder()
        .name("EDITAR")
        .build();

        PermissionEntity deletePermission = PermissionEntity.builder()
        .name("BORRAR")
        .build();

        /* ROLES */
        RoleEntity roleAdmin = RoleEntity.builder()
        .roleEnum(RoleEnum.ADMIN)
        .permissionList(Set.of(createPermission, readPermission, editPermission,deletePermission))
        .build();

        RoleEntity roleUser = RoleEntity.builder()
        .roleEnum(RoleEnum.USER)
        .permissionList(Set.of(readPermission))
        .build();

        RoleEntity roleDeveloper = RoleEntity.builder()
        .roleEnum(RoleEnum.DEVELOPER)
        .permissionList(Set.of(createPermission, readPermission, editPermission,deletePermission))
        .build();


        /* LISTA USUARIOS */
        UserEntity userSantiago = UserEntity.builder()
        .username("santiago")
        .password(passwordEncoder.encode("contraseña"))
        .country("mexico")
        .email("santiago@gmail.com")
        .firstname("Lopez")
        .lastname("perez")
        .roles(Set.of(roleAdmin))
        .build();

        UserEntity userDeveloper = UserEntity.builder()
        .username("developer")
        .password(passwordEncoder.encode("contraseña"))
        .country("mexico")
        .email("developer@gmail.com")
        .firstname("xd")
        .lastname("perez")
        .roles(Set.of(roleDeveloper))
        .build();

        UserEntity userUser = UserEntity.builder()
        .username("User")
        .password(passwordEncoder.encode("contraseña"))  
        .country("mexico")
        .email("User@gmail.com")
        .firstname("xd")
        .lastname("perez")
        .roles(Set.of(roleUser))
        .build();

        userRepository.saveAll(List.of(userSantiago, userDeveloper, userUser));
    };


  }
}