package com.propulse.backendfacturier.runner;

import com.propulse.backendfacturier.dto.OperatorDTO;
import com.propulse.backendfacturier.entity.*;
import com.propulse.backendfacturier.repository.OperatorRepository;
import com.propulse.backendfacturier.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class MyRunner implements CommandLineRunner {

    private CityService cityService;
    private UserService userService;
    private RoleService roleService;
    private FeeService feeService;
    private OperatorService operatorService;
    private CountryService countryService;
    private MessageService messageService;
    private OperatorRepository operatorRepository;

    public MyRunner(CityService cityService, UserService userService, RoleService roleService, FeeService feeService, OperatorService operatorService, CountryService countryService, MessageService messageService, OperatorRepository operatorRepository) {
        this.cityService = cityService;
        this.userService = userService;
        this.roleService = roleService;
        this.feeService = feeService;
        this.operatorService = operatorService;
        this.countryService = countryService;
        this.messageService = messageService;
        this.operatorRepository = operatorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //createOperatorCIE();
        //createOperatorSODECI();
        //createOperatorCANAL();
        createOperatorWithImage();
        createRole();
        createCountry();
        createCity();
        createFeeOne();
        createFeeFour();
        createsUser();
        createsSupport();
        createSendMessage();
        createReceiptMessage();
    }

    private void createCountry(){
        Arrays.asList("Côte d'Ivoire", "Ghana", "Burkina faso", "Cameroun","Togo", "Mali")
                .forEach(country -> countryService.addCountry(new Country(country)));
    }

    private void createCity(){
        Arrays.asList("Abidjan", "Yamoussoukro", "Dabou", "Man","Danané")
                .forEach(city -> cityService.addCity(new City(city)));
    }

    private void createsUser(){
        for (int i = 0; i < 10; i++) {
            City city = cityService.getOneCity(1L);
            Country country = countryService.getOneCountry(1L);
            User user = new User();
            user.setFirstname("fact"+i+"FN");
            user.setLastname("fact"+i+"LN");
            user.setEmail("fact"+i+"@gmail.com");
            user.setPhone("072354674"+i);
            user.setStreet("street"+i);
            user.setIndex("225");
            user.setPassword("1234");
            user.setCity(city);
            user.setCountry(country);
            userService.addUser(user);
        }
    }

    private void createsSupport() throws MessagingException {
        for (int i = 0; i < 1; i++) {
            City city = cityService.getOneCity(1L);
            Country country = countryService.getOneCountry(1L);
            User user = new User();
            user.setFirstname("factSupport"+i+"FN");
            user.setLastname("factSupport"+i+"LN");
            //user.setEmail("factsupport"+i+"@gmail.com");
            user.setEmail("melessangekevin@gmail.com");
            user.setPhone("072354679"+i);
            user.setStreet("street"+i);
            user.setIndex("225");
            user.setPassword("1234");
            user.setCity(city);
            user.setCountry(country);
            userService.addSupportAndBiller(user, 3L);
        }
    }

    private void createFeeOne() throws ParseException {
        for (int i = 0; i < 1; i++) {
            Operator operator= new Operator(1L);
            Fee fee = new Fee();
            fee.setPrice(35000);
            fee.setPhone("0723546741");
            fee.setFeeId("01034576899876"+i);
            fee.setNumberBill("0103457689987624432"+i);
            fee.setOperator(operator);
            Date periodFee = new SimpleDateFormat("dd-MM-yyyy").parse("18-04-2023");
            fee.setPeriodFee(periodFee);
            Date limitDate = new SimpleDateFormat("dd-MM-yyyy").parse("03-05-2023");
            fee.setLimitDate(limitDate);
            feeService.addFee(fee);
        }
    }

    private void createFeeFour() throws ParseException {
        for (int i = 1; i < 2; i++) {
            Operator operator= new Operator(2L);
            Fee fee = new Fee();
            fee.setPrice(35000);
            fee.setPhone("0723546744");
            fee.setFeeId("01034576899876"+i);
            fee.setNumberBill("0103457689987624454"+i);
            fee.setOperator(operator);
            Date periodFee = new SimpleDateFormat("dd-MM-yyyy").parse("18-04-2023");
            fee.setPeriodFee(periodFee);
            Date limitDate = new SimpleDateFormat("dd-MM-yyyy").parse("03-05-2023");
            fee.setLimitDate(limitDate);
            feeService.addFee(fee);
        }
    }

    private void createRole(){
        Arrays.asList("Admin", "User", "Support", "CIE", "SODECI")
                .forEach(role -> roleService.addRole(new Role(role)));
    }

    private void createOperatorCIE(){
        Operator operator = new Operator();
        operator.setName("Compagnie Ivoirienne d'électricité");
        operator.setLabel("CIE");
        operatorService.addOperator(operator);
    }

    private void createOperatorSODECI(){
        Operator operator = new Operator();
        operator.setName("SODECI");
        operator.setLabel("SODECI");
        operatorService.addOperator(operator);
    }

    private void createOperatorCANAL(){
        Operator operator = new Operator();
        operator.setName("CANAL");
        operator.setLabel("CANAL");
        operatorService.addOperator(operator);
    }

    private void createSendMessage(){
        for (int i = 0; i < 3; i++) {
            Message message = new Message();
            message.setSenderEmail("fact"+i+"@gmail.com");
            message.setReceiptEmail("factSupport0LN@gmail.com");
            message.setMessage("Bonjour"+i);
            messageService.sendMessage(message);
        }
    }

    private void createReceiptMessage(){
        for (int i = 0; i < 3; i++) {
            Message message = new Message();
            message.setSenderEmail("factSupport0LN@gmail.com");
            message.setReceiptEmail("fact"+i+"@gmail.com");
            message.setMessage("Bonjour"+i);
            messageService.sendMessage(message);
        }
    }

    private void createOperatorWithImage(){

        List<OperatorDTO> operators = new ArrayList<>();

        // Ajouter les informations des opérateurs avec leurs images correspondantes
        operators.add(new OperatorDTO("cie.png", "Compagnie Ivoirienne d'électricité", "CIE"));
        operators.add(new OperatorDTO("sodeci.png", "Société de distribution d'eau en Côte d'Ivoire", "SODECI"));
        operators.add(new OperatorDTO("canal.png", "CANAL", "CANAL"));
        operators.add(new OperatorDTO("startimes.png", "STARTIMES", "STARTIMES"));
        operators.add(new OperatorDTO("orange.png", "Orange", "Orange"));

        for (OperatorDTO operatorDTO : operators) {
            String imagePath = "uploads/" + operatorDTO.getImageFileName();

            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));

                Operator operator = new Operator();
                operator.setName(operatorDTO.getName());
                operator.setLabel(operatorDTO.getLabel());
                operator.setPicture(StringUtils.getFilename(imagePath));

                operatorRepository.save(operator);

                System.out.println("Opérateur " + operatorDTO.getName() + " créé avec succès !");
            } catch (IOException e) {
                System.out.println("Erreur lors de la création de l'opérateur " + operatorDTO.getName() + " : " + e.getMessage());
            }
        }

    }

}
