package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.Repository.UserRepository;
import com.example.demo.persistence.UserEntity;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Buscar el usuario en el repositorio
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        // Crear la lista de autoridades
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        // Añadir los roles del usuario como autoridades
        userEntity.getRoles()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name())));

        // Añadir los permisos del usuario como autoridades
        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        // Retornar el usuario con las autoridades
        return new User(userEntity.getUsername(), userEntity.getPassword(), authorityList);
    }
}