package com.propulse.backendfacturier.repository;

import com.propulse.backendfacturier.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    User findUserByPhone(String phone);
    @Query(value = "SELECT u.email, u.lastname, u.firstname FROM users u", nativeQuery = true)
    List<String> findAllUser();

    @Query(value = "SELECT u.email, u.lastname, u.firstname, r.name \n" +
            "FROM users u \n" +
            "JOIN users_roles ur ON u.id = ur.users_id \n" +
            "JOIN role r ON ur.roles_id = r.id \n" +
            "WHERE r.name = 'Support'", nativeQuery = true)
    List<String> findAllUserSupport();
}
