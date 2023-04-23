package com.propulse.backendfacturier.repository;

import com.propulse.backendfacturier.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
