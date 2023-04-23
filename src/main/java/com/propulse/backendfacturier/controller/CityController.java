package com.propulse.backendfacturier.controller;

import com.propulse.backendfacturier.entity.City;
import com.propulse.backendfacturier.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/all")
    public List<City> getAllCity(){
        return cityService.getAllCity();
    }

}
