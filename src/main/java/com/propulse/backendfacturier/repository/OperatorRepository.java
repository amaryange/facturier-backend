package com.propulse.backendfacturier.repository;

import com.propulse.backendfacturier.entity.Operator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
    @Query(value = "SELECT o.id as id, o.label as label, o.name as name, o.picture as picture from Operator o ")
    Page<Map<String, Object>> findAllOperators(Pageable pageable);

    // Nombre de Facturier enregistré (sur l’année)
    @Query("SELECT COUNT(o) FROM Operator o WHERE YEAR(o.dateAdded) = YEAR(CURRENT_DATE()) ")
    Long numberOfOperatorInCurrentYear();

    // Nombre de Facturier enregistré (sur une année au choix)
    @Query("SELECT COUNT(o) FROM Operator o WHERE YEAR(o.dateAdded) = :year ")
    Long numberOfOperatorForOneYear(int year);

}
