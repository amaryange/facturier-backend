package com.propulse.backendfacturier.controller;

import com.propulse.backendfacturier.entity.Role;
import com.propulse.backendfacturier.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/addRole")
    public Role addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }

}
