package com.propulse.backendfacturier.controller;

import com.propulse.backendfacturier.entity.Country;
import com.propulse.backendfacturier.entity.Operator;
import com.propulse.backendfacturier.repository.OperatorRepository;
import com.propulse.backendfacturier.service.CountryService;
import com.propulse.backendfacturier.service.OperatorService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/operator")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;
    @Autowired
    private OperatorRepository operatorRepository;

    @GetMapping("/all")
    public List<Operator> getAllOperators(){
        return operatorService.getAllOperators();
    }
    @PreAuthorize("hasAuthority('User')")
    @PostMapping("/add-new")
    public Map<String, Object> createOperator(
            @RequestParam("file") MultipartFile file,
            @RequestParam("label")String label,
            @RequestParam("name")String name
    ) throws IOException {

        operatorService.createOperator(file, label, name);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Operateur ajouté avec succès");
        return response;

    }
    //@PreAuthorize("hasAuthority('User')")
    @GetMapping("/get-photo/{operatorId}")
    public void getProfilePhoto(@PathVariable Long operatorId, HttpServletResponse response) throws FileNotFoundException {

        Operator operator = operatorRepository.findById(operatorId).orElseThrow(() -> new IllegalArgumentException("Operateur introuvable"));

        // Récupérer le chemin d'accès du fichier
        Path filePath = Paths.get("./uploads/" + operator.getPicture());

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

    @GetMapping("/numberOfOperatorInCurrentYear")
    public Long numberOfOperatorInCurrentYear(){
        return operatorService.numberOfOperatorInCurrentYear();
    }

    @GetMapping("/numberOfOperatorForOneYear")
    public Long numberOfOperatorForOneYear(@RequestParam("year") int year){
        return operatorService.numberOfOperatorForOneYear(year);
    }

}
