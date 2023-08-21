package com.propulse.backendfacturier.service;

import com.propulse.backendfacturier.entity.Announcement;
import com.propulse.backendfacturier.entity.Message;
import com.propulse.backendfacturier.entity.Operator;
import com.propulse.backendfacturier.repository.AnnouncementRepository;
import com.propulse.backendfacturier.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementService {


    @Autowired
    private AnnouncementRepository announcementRepository;

    public Announcement createAnnouncement(
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("domain")String domain,
            @RequestParam("name")String name,
            @RequestParam("email")String email
    ) throws IOException {

        // Vérifier si le fichier est vide
        if (picture.isEmpty()) {
            throw new IllegalArgumentException("Fichier vide");
        }

        // Enregistrer le fichier sur le disque
        String fileName = StringUtils.cleanPath(picture.getOriginalFilename());
        Path path = Paths.get("./announcements");
        Files.createDirectories(path);
        try (InputStream inputStream = picture.getInputStream()) {
            Files.copy(inputStream, path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        }

        Announcement announcement = new Announcement();
        announcement.setName(name);
        announcement.setEmail(email);
        announcement.setPicture(fileName);
        announcement.setDomain(domain);

        return announcementRepository.save(announcement);

    }

    public Long numberAnnouncementPerMonth(){
        return announcementRepository.numberAnnouncementPerMonth();
    }

    public Long numberAnnouncementPerYear(){
        return announcementRepository.numberAnnouncementPerYear();
    }

    public List<Announcement> findAll(){
        return announcementRepository.findAll();
    }


}
