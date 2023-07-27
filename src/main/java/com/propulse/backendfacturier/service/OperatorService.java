package com.propulse.backendfacturier.service;

import com.propulse.backendfacturier.entity.Operator;
import com.propulse.backendfacturier.repository.OperatorRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OperatorService {

    @Autowired
    private OperatorRepository operatorRepository;

    public Operator addOperator(@RequestBody Operator operator){
        return operatorRepository.save(operator);
    }

    public Operator updateOperator(@PathVariable Long id, @RequestBody Operator operator){
        operator.setId(id);
        return operatorRepository.save(operator);
    }

    public Operator createOperator(
            @RequestParam("file")MultipartFile file,
            @RequestParam("label")String label,
            @RequestParam("name")String name
            ) throws IOException {

        // Vérifier si le fichier est vide
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Fichier vide");
        }

        // Enregistrer le fichier sur le disque
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get("./uploads");
        Files.createDirectories(path);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        }

        Operator operator = new Operator();
        operator.setName(name);
        operator.setLabel(label);
        operator.setPicture(fileName);

        return operatorRepository.save(operator);

    }

    public Page<Map<String, Object>> getAllOperators(Pageable pageable){
        return operatorRepository.findAllOperators(pageable);
    }

    public Long numberOfOperatorInCurrentYear(){
        return operatorRepository.numberOfOperatorInCurrentYear();
    }

    public Long numberOfOperatorForOneYear(int year){
        return operatorRepository.numberOfOperatorForOneYear(year);
    }

}
