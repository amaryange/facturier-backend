package com.propulse.backendfacturier.service;

import com.propulse.backendfacturier.entity.Role;
import com.propulse.backendfacturier.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role addRole(@RequestBody Role role){
        return roleRepository.save(role);
    }

    public Role getOneRole(@PathVariable Long id){
        return roleRepository.findById(id).get();
    }

    public Role findRoleByName(String name){ return roleRepository.findRoleByName(name);}

    public Role findByName(String name){
        return roleRepository.findByName(name);
    }

}
