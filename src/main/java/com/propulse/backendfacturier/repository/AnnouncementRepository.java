package com.propulse.backendfacturier.repository;

import com.propulse.backendfacturier.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("SELECT count(a) FROM Announcement a WHERE YEAR(a.date) = YEAR(CURRENT_DATE) AND MONTH(a.date) = MONTH(CURRENT_DATE) ")
    Long numberAnnouncementPerMonth();

    @Query("SELECT count(a) FROM Announcement a WHERE YEAR(a.date) = YEAR(CURRENT_DATE) ")
    Long numberAnnouncementPerYear();

    List<Announcement> findAll();

}
