package com.picpay.simple.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.picpay.simple.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDocumentOrEmail(String document, String email);  
    Optional<User> findById(Long id);

}
