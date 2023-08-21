package com.propulse.backendfacturier.controller;

import com.propulse.backendfacturier.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/totalMessagePerYear")
    public Long totalMessagePerYear(){
        return messageService.totalMessagePerYear();
    }
    @GetMapping("/totalMessagePerMonth")
    public Long totalMessagePerMonth(){
        return messageService.totalMessagePerMonth();
    }

    /*
    @GetMapping("/findUserDiscussAdmin")
    public List<String> findUserDiscussAdmin(@RequestParam("mail") String adminMail){
        return messageService.findUserDiscussAdmin(adminMail);
    }

     */

    @GetMapping("/findUserDiscussAdmin")
    public List<Map<String, Object>> findUserDiscussAdmin(@RequestParam("mail") String adminMail){

        List<String> users = messageService.findUserDiscussAdmin(adminMail);
        List<Map<String, Object>> result = new ArrayList<>();

        for (String user : users) {
            String[] values = user.split(",");
            Map<String, Object> map = new HashMap<>();
            map.put("lastname", values[0]);
            map.put("firstname", values[1]);
            result.add(map);
        }

        return result;

    }

}
