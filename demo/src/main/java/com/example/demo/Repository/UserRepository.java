package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.persistence.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);

}
