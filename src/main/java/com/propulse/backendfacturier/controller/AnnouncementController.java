package com.propulse.backendfacturier.controller;

import com.propulse.backendfacturier.entity.Announcement;
import com.propulse.backendfacturier.entity.Operator;
import com.propulse.backendfacturier.repository.AnnouncementRepository;
import com.propulse.backendfacturier.requestEntity.OperatorRequest;
import com.propulse.backendfacturier.service.AnnouncementService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private AnnouncementRepository announcementRepository;

    @PostMapping("/add-new")
    public Map<String, Object> createAnnouncement(
            @RequestParam("picture") MultipartFile picture,
            @RequestParam("domain")String domain,
            @RequestParam("name")String name,
            @RequestParam("email")String email
    ) throws IOException {

        announcementService.createAnnouncement(picture, domain, name, email);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Annonce ajoutée avec succès");
        return response;

    }

    @GetMapping("/get-photo/{announcementId}")
    public void getProfilePhoto(@PathVariable Long announcementId, HttpServletResponse response) throws FileNotFoundException {

        Announcement announcement = announcementRepository.findById(announcementId).orElseThrow(() -> new IllegalArgumentException("Annonce introuvable"));

        // Récupérer le chemin d'accès du fichier
        Path filePath = Paths.get("./announcements/" + announcement.getPicture());

        // Vérifier si le fichier existe
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("Fichier introuvable");
        }

        // Définir le type MIME de la réponse
        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        // Écrire les données de l'image dans le flux de sortie HTTP
        try (InputStream inputStream = Files.newInputStream(filePath)) {
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/numberAnnouncementPerMonth")
    public Long numberAnnouncementPerMonth(){
        return announcementService.numberAnnouncementPerMonth();
    }

    @GetMapping("/numberAnnouncementPerYear")
    public Long numberAnnouncementPerYear(){
        return announcementService.numberAnnouncementPerYear();
    }

    @GetMapping("/findAll")
    public List<Announcement> findAll(){
        return announcementService.findAll();
    }


}
