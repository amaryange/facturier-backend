package com.propulse.backendfacturier.service;

import com.propulse.backendfacturier.entity.City;
import com.propulse.backendfacturier.entity.Role;
import com.propulse.backendfacturier.repository.CityRepository;
import com.propulse.backendfacturier.repository.RoleRepository;
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
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public City addCity(@RequestBody City city){
        return cityRepository.save(city);
    }

    public City getOneCity(@PathVariable Long id){
        return cityRepository.findById(id).get();
    }

    public List<City> getAllCity(){
        return cityRepository.findAll();
    }

}
