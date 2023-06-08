package com.propulse.backendfacturier.repository;

import com.propulse.backendfacturier.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    List<Fee> findFeeByPhone(String phone);
    @Query(value = "SELECT * FROM fee f WHERE f.phone = :phone AND f.fee_status = false ", nativeQuery = true)
    List<Fee> findFeeByPhoneAndFeeStatus(String phone);

    @Query(value = "SELECT * FROM fee f WHERE f.phone = :phone AND f.fee_status = true ", nativeQuery = true)
    List<Fee> findFeeByPhoneAndFeeStatusTrue(String phone);
    @Query(value = "SELECT COUNT(*) FROM fee f WHERE f.phone = :phone", nativeQuery = true)
    long count(String phone);

    @Query(value = "SELECT COALESCE(SUM(f.price), 0) AS somme_factures " +
            "FROM fee f " +
            "WHERE f.phone = :phone AND f.fee_status = TRUE", nativeQuery = true)
    Long countFeePriceByPerson(String phone);

    @Query(value = "SELECT f.*, o.label, o.name " +
            "FROM fee f, operator o " +
            "WHERE f.fee_status = false AND f.fee_id = :feeId AND o.id = f.id", nativeQuery = true)
    List<String> findAllFeeByUser(String feeId);
    List<Fee> findFeeByDebtor(String debtor);
}
