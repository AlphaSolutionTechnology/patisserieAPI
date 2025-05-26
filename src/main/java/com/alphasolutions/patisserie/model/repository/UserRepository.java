package com.alphasolutions.patisserie.model.repository;

import com.alphasolutions.patisserie.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserCode(String userCode);

    User findUserByUsername(String username);

    User findUserByUserCode(String userCode);

    User findUserByPhoneNumber(String phoneNumber);
}
