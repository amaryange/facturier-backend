package com.propulse.backendfacturier.controller;

import com.propulse.backendfacturier.entity.City;
import com.propulse.backendfacturier.entity.Country;
import com.propulse.backendfacturier.service.CityService;
import com.propulse.backendfacturier.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/country")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping("/all")
    public List<Country> getAllCountry(){
        return countryService.getAllCountry();
    }
}
