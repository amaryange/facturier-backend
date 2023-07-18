package com.propulse.backendfacturier.repository;

import com.propulse.backendfacturier.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Long> {
    //Obtenir les infos de l'utilisateur par son email.
    User findUserByEmail(String email);
    @Query(value = "SELECT u.lastname, u.firstname, u.email, u.phone, u.street, r.name FROM User u JOIN u.roles r WHERE u.email = :email")
    List<String> getUserInfoByEmail(String email);

    User findUserByPhone(String phone);
    /*
    @Query(value = "SELECT u.email, u.lastname, u.firstname FROM users u", nativeQuery = true)
    List<String> findAllUser();
     */

    @Query(value = "SELECT u.email AS email, u.lastname AS lastname, u.firstname AS firstname FROM users u ORDER BY u.lastname ASC, u.firstname ASC",
            countQuery = "SELECT COUNT(*) FROM users u",
            nativeQuery = true)
    Page<Map<String, Object>> findAllUsers(Pageable pageable);

    // Liste des utilisateurs avec le rôle de Support.
    /*
    @Query(value = "SELECT u.email, u.lastname, u.firstname, r.name \n" +
            "FROM users u \n" +
            "JOIN users_roles ur ON u.id = ur.users_id \n" +
            "JOIN role r ON ur.roles_id = r.id \n" +
            "WHERE r.name = 'Support'", nativeQuery = true)
    List<String> findAllUserSupport();

     */

    @Query(value = "SELECT u.email, u.lastname, u.firstname, r.name AS role_support " +
            "FROM users u " +
            "JOIN users_roles ur ON u.id = ur.users_id " +
            "JOIN role r ON ur.roles_id = r.id " +
            "WHERE r.name = 'Support' " +
            "ORDER BY u.lastname ASC, u.firstname ASC",
            countQuery = "SELECT COUNT(*) " +
                    "FROM users u " +
                    "JOIN users_roles ur ON u.id = ur.users_id " +
                    "JOIN role r ON ur.roles_id = r.id " +
                    "WHERE r.name = 'Support'",
            nativeQuery = true)
    Page<Map<String, Object>> findAllUserSupport(Pageable pageable);

    //Liste des comptes actifs
    @Query("SELECT u.lastname AS lastname, u.firstname AS firstname, u.email AS email, u.active AS active, u.dateAddedUser AS dateAddedUser FROM User u WHERE u.active = true")
    Page<Map<String, Object>> listOfActivedUser(Pageable pageable);


    /*
    @Query("SELECT u.lastname, u.firstname, u.email, u.active, u.dateAddedUser FROM User u WHERE u.active = true ")
    List<String> listOfActivedUser();

     */

    //Liste des opérateurs
    /*
    @Query(value = "SELECT u.email, u.lastname, u.firstname, r.name \n" +
            "FROM users u \n" +
            "JOIN users_roles ur ON u.id = ur.users_id \n" +
            "JOIN role r ON ur.roles_id = r.id \n" +
            "WHERE r.name != 'Support' AND r.name != 'Admin' AND r.name != 'User' AND u.active = true ", nativeQuery = true)
    List<String> listOfUsersOperator();

     */

    @Query(value = "SELECT u.email, u.lastname, u.firstname, r.name " +
            "FROM users u " +
            "JOIN users_roles ur ON u.id = ur.users_id " +
            "JOIN role r ON ur.roles_id = r.id " +
            "WHERE r.name != 'Support' AND r.name != 'Admin' AND r.name != 'User' AND u.active = true " +
            "ORDER BY u.lastname ASC, u.firstname ASC",
            countQuery = "SELECT COUNT(*) " +
                    "FROM users u " +
                    "JOIN users_roles ur ON u.id = ur.users_id " +
                    "JOIN role r ON ur.roles_id = r.id " +
                    "WHERE r.name != 'Support' AND r.name != 'Admin' AND r.name != 'User' AND u.active = true",
            nativeQuery = true)
    Page<Object[]> listOfUsersOperator(Pageable pageable);


}
