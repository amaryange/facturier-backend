package com.propulse.backendfacturier.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.propulse.backendfacturier.dto.UserDTO;
import com.propulse.backendfacturier.entity.*;
import com.propulse.backendfacturier.helper.JWTHelper;
import com.propulse.backendfacturier.service.FeeService;
import com.propulse.backendfacturier.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    private JWTHelper jwtHelper;

    public UserController(UserService userService, FeeService feeService, JWTHelper jwtHelper) {
        this.userService = userService;
        this.feeService = feeService;
        this.jwtHelper = jwtHelper;
    }

    @PostMapping("/add")
    public User addUser(@RequestBody UserDTO userDTO){
        User user = convertDtoToEntity(userDTO);
        return userService.addUser(user);
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
    @GetMapping("/fees")
    public Map<String, Object> findFeeByPhone(@RequestParam(name = "phone", defaultValue = "")String phone) {
        Map<String, Object> map = new HashMap<>();
        User user = userService.getUserByPhone(phone);
        List<Fee> fees = feeService.findFeeByPhone(phone);
        List<Fee> array = new ArrayList<>();
        if(user==null){
            map.put("factures", array);
        } else {
            if(fees.isEmpty()){
                map.put("factures", array);
            } else {
                map.put("factures", fees);
            }
        }
        return map;

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
