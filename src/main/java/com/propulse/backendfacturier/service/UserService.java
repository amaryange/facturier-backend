package com.propulse.backendfacturier.service;

import com.propulse.backendfacturier.entity.Fee;
import com.propulse.backendfacturier.entity.Message;
import com.propulse.backendfacturier.entity.Role;
import com.propulse.backendfacturier.entity.User;
import com.propulse.backendfacturier.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

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
    @Autowired
    private JavaMailSender mailSender;


    // Caractères autorisés pour le mot de passe
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789#@*/";

    // Longueur par défaut du mot de passe
    private static final int DEFAULT_PASSWORD_LENGTH = 8;



    public static String generatePassword(int length) {
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }

    // fonction permettant de générer un mot de passe aléatoire
    public static String generatePassword() {
        return generatePassword(DEFAULT_PASSWORD_LENGTH);
    }

    public void sendEmail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("meless@amary.dev");
        message.setTo(to);
        message.setText(content);
        message.setSubject(subject);

        mailSender.send(message);

        System.out.println("Mail envoyé.");
    }

    public User addUser(@RequestBody User user){
        Role role = roleService.getOneRole(2L);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        addRoleToUser(user, role);
        return userRepository.save(user);
    }

    //Envoyez un mail aux administrateur et support créer
    public User addSupportAndBiller(@RequestBody User user, @RequestParam("roleId") Long roleId)  {
        Role role = roleService.getOneRole(roleId);
        String password = generatePassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        //String userName = user.getLastname();
        //String userEmail = user.getEmail();
        //sendEmail(userEmail, "Mot de passe mesfactures.ci", "Bonjour " + userName + ", voici votre mot de passe : "+password);
        addRoleToUser(user, role);
        return userRepository.save(user);
    }

    public User updateUser(@PathVariable Long id,@RequestBody String lastname,
                          @RequestBody String firstname,
                          @RequestBody String index,
                          @RequestBody String phone,
                          @RequestBody String email
                          ){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setEmail(email);
            existingUser.setLastname(lastname);
            existingUser.setFirstname(firstname);
            existingUser.setIndex(index);
            existingUser.setPhone(phone);
            // mettez à jour tous les autres champs que vous souhaitez modifier
            User savedFee = userRepository.save(existingUser);
            return savedFee;
        } else {
            return null;
        }
    }

    public void addRoleToUser(User user, Role role) {
        user.getRoles().add(role);
        role.getUsers().add(user);
    }

    public User getUserByPhone(@RequestParam(name = "phone", defaultValue = "")String phone){
        return userRepository.findUserByPhone(phone);
    }

    public Page<Map<String, Object>> findAllUsers(Pageable pageable) {
        return userRepository.findAllUsers(pageable);
    }

    /*
    public List<String> getAllUsers(){
        return userRepository.findAllUser();
    }
     */

    /*
    public List<String> findAllUserSupport(){
        return userRepository.findAllUserSupport();
    }
     */

    public Page<Map<String, Object>> findAllUserSupport(Pageable pageable) {
        return userRepository.findAllUserSupport(pageable);
    }

    public User getOneUser(@PathVariable Long id){
        return userRepository.findById(id).get();
    }

    public User loadUserByEmail(String email){ return userRepository.findUserByEmail(email); }

    public List<String> getUserInfoByEmail(String email){ return userRepository.getUserInfoByEmail(email); }

    /*
    public List<String> listOfActivedUser(){
        return userRepository.listOfActivedUser();
    }
     */

    public Page<Map<String, Object>> listOfActivedUser(Pageable pageable) {
        return userRepository.listOfActivedUser(pageable);
    }
    /*
    public List<String> listOfUsersOperator(){
        return userRepository.listOfUsersOperator();
    }
     */

    public Page<Object[]> listOfUsersOperator(Pageable pageable) {
        return userRepository.listOfUsersOperator(pageable);
    }

}
