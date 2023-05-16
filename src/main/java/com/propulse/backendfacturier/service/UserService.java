package com.propulse.backendfacturier.service;

import com.propulse.backendfacturier.entity.Fee;
import com.propulse.backendfacturier.entity.Message;
import com.propulse.backendfacturier.entity.Role;
import com.propulse.backendfacturier.entity.User;
import com.propulse.backendfacturier.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public User addUser(@RequestBody User user){
        Role role = roleService.getOneRole(2L);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        addRoleToUser(user, role);
        return userRepository.save(user);
    }

    public User addSupport(@RequestBody User user){
        Role role = roleService.getOneRole(3L);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        addRoleToUser(user, role);
        return userRepository.save(user);
    }

    public void addRoleToUser(User user, Role role) {
        user.getRoles().add(role);
        role.getUsers().add(user);
    }

    public User getUserByPhone(@RequestParam(name = "phone", defaultValue = "")String phone){
        return userRepository.findUserByPhone(phone);
    }

    public List<String> getAllUsers(){
        return userRepository.findAllUser();
    }

    public List<String> findAllUserSupport(){
        return userRepository.findAllUserSupport();
    }

    public User getOneUser(@PathVariable Long id){
        return userRepository.findById(id).get();
    }

    public User loadUserByEmail(String email){ return userRepository.findUserByEmail(email); }

}
