package com.example.socialdata.repository;

import com.example.socialdata.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByAlias(String alias);

    boolean existsByAlias(@NotBlank String alias);

    boolean existsByEmail(@Email @NotBlank String email);
}
