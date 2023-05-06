package com.propulse.backendfacturier.repository;

import com.propulse.backendfacturier.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
}
