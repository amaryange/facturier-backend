package com.propulse.backendfacturier.runner;

import com.propulse.backendfacturier.entity.*;
import com.propulse.backendfacturier.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private CountryService countryService;


    public MyRunner(CityService cityService, UserService userService, RoleService roleService, FeeService feeService, CountryService countryService) {
        this.cityService = cityService;
        this.userService = userService;
        this.roleService = roleService;
        this.feeService = feeService;
        this.countryService = countryService;
    }

    @Override
    public void run(String... args) throws Exception {
        createRole();
        createCountry();
        createCity();
        createFeeOne();
        createFeeFour();
        createsUser();
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
        for (int i = 0; i < 2; i++) {
            Fee fee = new Fee();
            fee.setDescription("Facture d'éléctricité");
            fee.setNameEnterprise("Compagnie Ivoirienne d'électricité");
            fee.setPrice(35000);
            fee.setPhone("0723546741");
            Date periodFee = new SimpleDateFormat("dd-MM-yyyy").parse("18-04-2023");
            fee.setPeriodFee(periodFee);
            Date limitDate = new SimpleDateFormat("dd-MM-yyyy").parse("03-05-2023");
            fee.setLimitDate(limitDate);
            feeService.addFee(fee);
        }
    }

    private void createFeeFour() throws ParseException {
        for (int i = 0; i < 2; i++) {
            Fee fee = new Fee();
            fee.setDescription("Abonnement Canal");
            fee.setNameEnterprise("Canal plus Afrique");
            fee.setPrice(35000);
            fee.setPhone("0723546744");
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

}
