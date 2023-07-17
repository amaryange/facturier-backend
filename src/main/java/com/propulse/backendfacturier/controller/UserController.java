package com.propulse.backendfacturier.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.propulse.backendfacturier.dto.UserDTO;
import com.propulse.backendfacturier.entity.*;
import com.propulse.backendfacturier.helper.JWTHelper;
import com.propulse.backendfacturier.repository.UserRepository;
import com.propulse.backendfacturier.service.FeeService;
import com.propulse.backendfacturier.service.MessageService;
import com.propulse.backendfacturier.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.propulse.backendfacturier.constant.JWTUtil.AUTH_HEADER;
import static com.propulse.backendfacturier.constant.JWTUtil.SECRET;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private UserService userService;
    private FeeService feeService;
    private MessageService messageService;
    private JWTHelper jwtHelper;
    private UserRepository userRepository;

    public UserController(UserService userService, FeeService feeService, MessageService messageService, JWTHelper jwtHelper, UserRepository userRepository) {
        this.userService = userService;
        this.feeService = feeService;
        this.messageService = messageService;
        this.jwtHelper = jwtHelper;
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public Map<String, Object> addUser(@RequestBody UserDTO userDTO){
        User user = convertDtoToEntity(userDTO);
        userService.addUser(user);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Utilisateur ajouté avec succès");
        return map;
    }

    @PostMapping("/add-support-or-biller")
    public Map<String, Object> addSupportAndBiller(@RequestBody UserDTO userDTO, @RequestParam("roleId") Long roleId) throws MessagingException {
        User user = convertDtoToEntity(userDTO);
        String userEmail = user.getEmail();
        String userName = user.getLastname();

        // Appel à la méthode sendEmail avec les arguments appropriés
        userService.sendEmail(userEmail, "Mot de passe mesfactures.ci","Bonjour " + userName + ", voici votre mot de passe :");

        userService.addSupportAndBiller(user, roleId);

        Map<String, Object> map = new HashMap<>();
        map.put("message", "Utilisateur ajouté avec succès");
        return map;
    }
    /*
    @GetMapping("/getAllUsers")
    public List<String> getAllUsers(){
        return userService.getAllUsers();
    }
     */

    @PreAuthorize("hasAuthority('User')")
    @PostMapping("/update/{id}")
    public User updateUser(@PathVariable Long id,@RequestParam("lastname") String lastname,
                           @RequestParam("firstname") String firstname,
                           @RequestParam("index") String index,
                           @RequestParam("phone") String phone,
                           @RequestParam("email") String email){
        return userService.updateUser(id, lastname, firstname, index, phone, email);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<Page<Object[]>> getAllUsers(Pageable pageable) {
        Page<Object[]> usersPage = userService.findAllUsers(pageable);
        return ResponseEntity.ok(usersPage);
    }

    /*
    @GetMapping("/getAllUsers")
    public List<Map<String, Object>> getAllUsers() {
        List<String> users = userRepository.findAllUser();
        //Page<Object[]> usersPage = userService.findAllUsers(pageable);
        List<Map<String, Object>> result = new ArrayList<>();

        for (String user : users) {
            String[] values = user.split(",");
            Map<String, Object> map = new HashMap<>();
            map.put("email", values[0]);
            map.put("lastname", values[1]);
            map.put("firstname", values[2]);
            result.add(map);
        }

        return result;
    }

     */

    /*
    @GetMapping("/getAllUserSupport")
    public List<Map<String, Object>> findAllUserSupport() {
        List<String> supports = userRepository.findAllUserSupport();
        List<Map<String, Object>> result = new ArrayList<>();

        for (String support : supports) {
            String[] values = support.split(",");
            Map<String, Object> map = new HashMap<>();
            map.put("email", values[0]);
            map.put("lastname", values[1]);
            map.put("firstname", values[2]);
            map.put("role", values[3]);
            result.add(map);
        }

        return result;
    }
     */

    @GetMapping("/supportUsers")
    public ResponseEntity<Page<Object[]>> getSupportUsers(Pageable pageable) {
        Page<Object[]> usersPage = userService.findAllUserSupport(pageable);
        return ResponseEntity.ok(usersPage);
    }

    @PostMapping("/sendMessage")
    public Message sendMessage(@RequestBody Message sendMessage){
        return messageService.sendMessage(sendMessage);
    }
    @GetMapping("/messages")
    public List<Message> findSenderAndReceiptEmails(@RequestParam(name = "sender", defaultValue = "")String sender,
                                                    @RequestParam(name = "receipt", defaultValue = "")String receipt){
        return messageService.findSenderAndReceiptEmails(sender, receipt);
    }

    private User convertDtoToEntity(UserDTO userDTO){

        User user = new User();

        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setLastname(userDTO.getLastname());
        user.setFirstname(userDTO.getFirstname());
        user.setIndex(userDTO.getIndex());
        user.setPhone(userDTO.getPhone());
        user.setStreet(user.getStreet());
        user.setPassword(userDTO.getPassword());
        user.setCity(new City(userDTO.getCity()));
        user.setCountry(new Country(userDTO.getCountry()));
        return user;

    }
    /*
    @GetMapping("/fees")
    public Map<String, Object> findFeeByPhone(@RequestParam(name = "phone", defaultValue = "")String phone) {
        Map<String, Object> map = new HashMap<>();
        User user = userService.getUserByPhone(phone);
        List<Fee> fees = feeService.findFeeByPhoneAndFeeStatus(phone);
        List<Fee> array = new ArrayList<>();
        if(user==null){
            map.put("factures", array);
        } else {
            if(fees.isEmpty()){
                map.put("factures", array);
            } else {
                List<Map<String, Object>> feeList = new ArrayList<>();
                for(Fee fee : fees){
                    Map<String, Object> feeMap = new HashMap<>();
                    feeMap.put("operator", fee.getOperator());
                    feeList.add(feeMap);
                }
                map.put("factures", feeList);
            }
        }
        return map;
    }

     */

    @GetMapping("/fees")
    public ResponseEntity<Page<Fee>> findFeeByPhoneAndFeeStatus(
            @RequestParam String phone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Fee> feePage = feeService.findFeeByPhoneAndFeeStatus(phone, pageable);
        return ResponseEntity.ok(feePage);
    }

    @GetMapping("/fees/outstanding")
    public Long sumFeeByPhoneAndFeeStatus(@RequestParam(name = "phone", defaultValue = "")String phone) {
        return feeService.sumFeeByPhoneAndFeeStatus(phone);
    }
    /*
    @PreAuthorize("hasAuthority('User')")
    @GetMapping("/fees/true")
    public Map<String, Object> findFeeByPhoneTrue(@RequestParam(name = "phone", defaultValue = "")String phone) {
        Map<String, Object> map = new HashMap<>();
        User user = userService.getUserByPhone(phone);
        List<Fee> fees = feeService.findFeeByPhoneAndFeeStatusTrue(phone);
        List<Fee> array = new ArrayList<>();
        if(user==null){
            map.put("factures", array);
        } else {
            if(fees.isEmpty()){
                map.put("factures", array);
            } else {
                List<Map<String, Object>> feeList = new ArrayList<>();
                for(Fee fee : fees){
                    Map<String, Object> feeMap = new HashMap<>();
                    feeMap.put("operator", fee.getOperator());
                    feeList.add(feeMap);
                }
                map.put("factures", feeList);
            }
        }
        return map;
    }

     */

    @GetMapping("/fees/true")
    public ResponseEntity<Page<Fee>> findFeeByPhoneTrue(
            @RequestParam String phone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Fee> feePage = feeService.findFeeByPhoneAndFeeStatusTrue(phone, pageable);
        return ResponseEntity.ok(feePage);
    }

    @GetMapping("/fees/number")
    public Long numberFeeByPhoneAndFeeStatusTrue(@RequestParam(name = "phone", defaultValue = "")String phone) {
        return feeService.numberFeeByPhoneAndFeeStatusTrue(phone);
    }

    @GetMapping("/fees/month")
    public Long sumFeeByPhoneAndFeeStatusTrueCurrentMonth(@RequestParam(name = "phone", defaultValue = "")String phone) {
        return feeService.findFeeByPhoneAndFeeStatusTrueCurrentMonth(phone);
    }

    @GetMapping("/fees/year")
    public Long sumFeeByPhoneAndFeeStatusTrueCurrentYear(@RequestParam(name = "phone", defaultValue = "")String phone) {
        return feeService.findFeeByPhoneAndFeeStatusTrueCurrentYear(phone);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('Admin')")
    public boolean checkIfEmailExists(@RequestParam(name = "email", defaultValue = "") String email) {
        return userService.loadUserByEmail(email) != null;
    }

    @GetMapping("/find")
    public User loadInstructorByEmail(@RequestParam(name = "email", defaultValue = "") String email) {
        return userService.loadUserByEmail(email);
    }

    @GetMapping("/getInfo")
    public List<Map<String, Object>> getUserInfoByEmail(@RequestParam(name = "email", defaultValue = "") String email) {

        List<String> users = userService.getUserInfoByEmail(email);
        List<Map<String, Object>> result = new ArrayList<>();

        for (String user : users) {
            String[] values = user.split(",");
            Map<String, Object> map = new HashMap<>();
            map.put("lastname", values[0]);
            map.put("firstname", values[1]);
            map.put("email", values[2]);
            map.put("phone", values[3]);
            map.put("street", values[4]);
            map.put("role", values[5]);
            result.add(map);
        }

        return result;

    }

    /*
    @GetMapping("/listOfActivedUser")
    public List<Map<String, Object>> listOfActivedUser(){

        List<String> users = userService.listOfActivedUser();
        List<Map<String, Object>> result = new ArrayList<>();

        for (String user : users) {
            String[] values = user.split(",");
            Map<String, Object> map = new HashMap<>();
            map.put("lastname", values[0]);
            map.put("firstname", values[1]);
            map.put("email", values[2]);
            map.put("active", values[3]);
            map.put("dateAddedUser", values[4]);
            result.add(map);
        }

        return result;

    }
     */

    @GetMapping("/listOfActivedUser")
    public ResponseEntity<Page<Object[]>> listOfActivedUser(Pageable pageable) {
        Page<Object[]> usersPage = userService.listOfActivedUser(pageable);
        return ResponseEntity.ok(usersPage);
    }

    /*
    @GetMapping("/listOfUsersOperator")
    public List<Map<String, Object>> listOfUsersOperator(){

        List<String> users = userService.listOfUsersOperator();
        List<Map<String, Object>> result = new ArrayList<>();

        for (String user : users) {
            String[] values = user.split(",");
            Map<String, Object> map = new HashMap<>();
            map.put("email", values[0]);
            map.put("lastname", values[1]);
            map.put("firstname", values[2]);
            map.put("role", values[3]);
            result.add(map);
        }

        return result;

    }

     */

    @GetMapping("/listOfUsersOperator")
    public ResponseEntity<Page<Object[]>> getOperatorUsers(Pageable pageable) {
        Page<Object[]> usersPage = userService.listOfUsersOperator(pageable);
        return ResponseEntity.ok(usersPage);
    }

    @GetMapping("/refresh-token")
    public void generateNewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jwtRefreshToken = jwtHelper.extractTokenFromHeaderIfExists(request.getHeader(AUTH_HEADER));
        if (jwtRefreshToken != null) {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtRefreshToken);
            String email = decodedJWT.getSubject();
            User user = userService.loadUserByEmail(email);
            String jwtAccessToken = jwtHelper.generateAccessToken(user.getEmail(), user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), jwtHelper.getTokensMap(jwtAccessToken, jwtRefreshToken));
        } else {
            throw new RuntimeException("Refresh token required");
        }
    }

}
