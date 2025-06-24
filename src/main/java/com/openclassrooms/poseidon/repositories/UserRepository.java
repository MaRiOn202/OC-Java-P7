package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {


    User findByUsername(String username);
}
