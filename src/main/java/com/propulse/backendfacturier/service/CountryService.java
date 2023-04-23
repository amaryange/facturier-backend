package com.propulse.backendfacturier.service;

import com.propulse.backendfacturier.entity.City;
import com.propulse.backendfacturier.entity.Country;
import com.propulse.backendfacturier.repository.CityRepository;
import com.propulse.backendfacturier.repository.CountryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Service
@AllArgsConstructor
@NoArgsConstructor
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public Country addCountry(@RequestBody Country country){
        return countryRepository.save(country);
    }

    public Country getOneCountry(@PathVariable Long id){
        return countryRepository.findById(id).get();
    }

    public List<Country> getAllCountry(){
        return countryRepository.findAll();
    }

}
