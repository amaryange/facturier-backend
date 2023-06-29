package com.propulse.backendfacturier.controller;

import com.propulse.backendfacturier.entity.City;
import com.propulse.backendfacturier.entity.Country;
import com.propulse.backendfacturier.service.CityService;
import com.propulse.backendfacturier.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/country")
public class CountryController {
    @Autowired
    private CountryService countryService;
    @PostMapping("/add")
    public Country addCountry(@RequestBody Country country){
        return countryService.addCountry(country);
    }


    @GetMapping("/all")
    public List<Country> getAllCountry(){
        return countryService.getAllCountry();
    }
}
