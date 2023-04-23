package com.propulse.backendfacturier.repository;

import com.propulse.backendfacturier.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    User findUserByPhone(String phone);
}
