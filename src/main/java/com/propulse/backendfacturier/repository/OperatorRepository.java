package com.propulse.backendfacturier.repository;

import com.propulse.backendfacturier.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperatorRepository extends JpaRepository<Operator, Long> {

    // Nombre de Facturier enregistré (sur l’année)
    @Query("SELECT COUNT(o) FROM Operator o WHERE YEAR(o.dateAdded) = YEAR(CURRENT_DATE()) ")
    Long numberOfOperatorInCurrentYear();

    // Nombre de Facturier enregistré (sur une année au choix)
    @Query("SELECT COUNT(o) FROM Operator o WHERE YEAR(o.dateAdded) = :year ")
    Long numberOfOperatorForOneYear(int year);

}
