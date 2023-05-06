package com.propulse.backendfacturier.runner;

import com.propulse.backendfacturier.entity.*;
import com.propulse.backendfacturier.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Component
public class MyRunner implements CommandLineRunner {

    private CityService cityService;
    private UserService userService;
    private RoleService roleService;
    private FeeService feeService;
    private OperatorService operatorService;
    private CountryService countryService;
    private MessageService messageService;

    public MyRunner(CityService cityService, UserService userService, RoleService roleService, FeeService feeService, OperatorService operatorService, CountryService countryService, MessageService messageService) {
        this.cityService = cityService;
        this.userService = userService;
        this.roleService = roleService;
        this.feeService = feeService;
        this.operatorService = operatorService;
        this.countryService = countryService;
        this.messageService = messageService;
    }

    @Override
    public void run(String... args) throws Exception {
        createOperatorCIE();
        createOperatorSODECI();
        createOperatorCANAL();
        createRole();
        createCountry();
        createCity();
        createFeeOne();
        createFeeFour();
        createsUser();
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

    private void createFeeOne() throws ParseException {
        for (int i = 0; i < 1; i++) {
            Operator operator= new Operator(1L);
            Fee fee = new Fee();
            fee.setPrice(35000);
            fee.setPhone("0723546741");
            fee.setFeeId("01034576899876"+i);
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
            fee.setOperator(operator);
            Date periodFee = new SimpleDateFormat("dd-MM-yyyy").parse("18-04-2023");
            fee.setPeriodFee(periodFee);
            Date limitDate = new SimpleDateFormat("dd-MM-yyyy").parse("03-05-2023");
            fee.setLimitDate(limitDate);
            feeService.addFee(fee);
        }
    }

    private void createRole(){
        Arrays.asList("Admin", "User", "Support")
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
            message.setReceiptEmail("fact5@gmail.com");
            message.setMessage("Bonjour"+i);
            messageService.sendMessage(message);

        }
    }

    private void createReceiptMessage(){
        for (int i = 0; i < 3; i++) {
            Message message = new Message();
            message.setSenderEmail("fact5@gmail.com");
            message.setReceiptEmail("fact"+i+"@gmail.com");
            message.setMessage("Bonjour"+i);
            messageService.sendMessage(message);
        }
    }

}
