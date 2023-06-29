package com.propulse.backendfacturier.controller;

import com.propulse.backendfacturier.entity.City;
import com.propulse.backendfacturier.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping("/add")
    public City save(@RequestBody City city){
        return cityService.addCity(city);
    }

    @GetMapping("/all")
    public List<City> getAllCity(){
        return cityService.getAllCity();
    }

}
