package com.propulse.backendfacturier.repository;

import com.propulse.backendfacturier.entity.Fee;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    List<Fee> findFeeByPhone(String phone);

    @Query(
            value = "SELECT * FROM users u, fee f WHERE u.phone = f.phone",
            nativeQuery = true)
    List<Fee> getAllFeeByPhone();
}
