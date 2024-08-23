package com.example.serreagricole.repository;

import com.example.serreagricole.entitie.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
}
