package com.propulse.backendfacturier.controller;

import com.propulse.backendfacturier.entity.Country;
import com.propulse.backendfacturier.entity.Operator;
import com.propulse.backendfacturier.service.CountryService;
import com.propulse.backendfacturier.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/operator")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    @GetMapping("/all")
    public List<Operator> getAllOperators(){
        return operatorService.getAllOperators();
    }

}
