package com.propulse.backendfacturier.controller;

import com.propulse.backendfacturier.entity.Fee;
import com.propulse.backendfacturier.entity.Operator;
import com.propulse.backendfacturier.service.FeeService;
import com.propulse.backendfacturier.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/fee")
public class FeeController {
    @Autowired
    private FeeService feeService;

    @GetMapping("/all?phone=")
    public List<Fee> getAllFee(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeService.findFeeByPhone(phone);
    }


    @GetMapping("/allFee?phone=")
    public List<Fee> findFeeByPhoneAndFeeStatus(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeService.findFeeByPhone(phone);
    }
    @PreAuthorize("hasAuthority('User')")
    @GetMapping("/countAllFeeBy/{phone}")
    public Long countAllFeeByPhone(@PathVariable String phone){
        return feeService.countAllFeeByPhone(phone);
    }
    /*
    @GetMapping("/getFeeByOperator/{feeId}")
    public List<String> findAllFeeByUser(@PathVariable String feeId){
        return feeService.findAllFeeByUser(feeId);
    }
     */

    @PreAuthorize("hasAuthority('User')")
    @GetMapping("/countFeeByPerson/{phone}")
    public Long countFeePriceByPerson(@PathVariable String phone){
        return feeService.countFeePriceByPerson(phone);
    }

    @GetMapping("/findAllFeeByUser/{feeId}")
    public List<Map<String, Object>> findAllFeeByUser(@PathVariable String feeId) {
        List<String> fees = feeService.findAllFeeByUser(feeId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (String fee : fees) {
            String[] values = fee.split(",");
            Map<String, Object> map = new HashMap<>();
            map.put("id", Integer.parseInt(values[0]));
            map.put("debtor", values[1]);
            map.put("feeId", values[2]);
            map.put("statusFee", Boolean.parseBoolean(values[3]));
            map.put("periodFee", values[4]);
            map.put("limitDate", values[5]);
            map.put("phone", values[6]);
            map.put("price", Integer.parseInt(values[7]));
            map.put("operatorId", Long.parseLong(values[8]));
            map.put("label", values[9]);
            map.put("name", values[10]);
            result.add(map);
        }

        return result;
    }
    @PreAuthorize("hasAuthority('User')")
    @PostMapping("/update/{id}")
    public Fee completedFee(@PathVariable Long id,@RequestBody String debtor){
        return feeService.updateFee(id, debtor);
    }

    @PreAuthorize("hasAuthority('User')")
    @GetMapping("/getFee/{debtor}")
    public List<Fee> findFeeByDebtor(@PathVariable String debtor){
        return feeService.findFeeByDebtor(debtor);
    }

    @GetMapping("/countFeeForCurrentMonth/{phone}")
    public Long countFeeForCurrentMonthByPerson(@PathVariable String phone){
        return feeService.countFeeForCurrentMonthByPerson(phone);
    }

    @GetMapping("/sumFeeForCurrentMonthByPerson/{phone}")
    public Long sumFeeForCurrentMonthByPerson(@PathVariable String phone){
        return feeService.sumFeeForCurrentMonthByPerson(phone);
    }

    @GetMapping("/countFeeByCurrentDateAndThreeLastMonth/{phone}")
    public List<Fee> getFeesByCurrentDate(@PathVariable String phone){
        return feeService.getFeesByCurrentDate(phone);
    }

    @GetMapping("/getTotalFeeAmountForCurrentMonth")
    public Long getTotalFeeAmountForCurrentMonth(@RequestParam("role") String role){
        return feeService.getTotalFeeAmountForCurrentMonth(role);
    }

    @GetMapping("/getTotalFeeAmountForMonthAndYear")
    public Long getTotalFeeAmountForMonthAndYear(@RequestParam("month") int month, @RequestParam("year") int year, @RequestParam("role") String role){
        return feeService.getTotalFeeAmountForMonthAndYear(month, year, role);
    }

    @GetMapping("/getNumberOfInvoicesForCurrentMonth")
    public Long getNumberOfInvoicesForCurrentMonth(@RequestParam("role") String role){
        return feeService.getNumberOfInvoicesForCurrentMonth(role);
    }

    @GetMapping("/getNumberOfInvoicesForMonthAndYear")
    public Long getNumberOfInvoicesForMonthAndYear(@RequestParam("month") int month, @RequestParam("year") int year, @RequestParam("role") String role){
        return feeService.getNumberOfInvoicesForMonthAndYear(month, year, role);
    }

    @GetMapping("/getAllFeeByOperator")
    public List<Map<String, Object>>getAllFeeByOperator(@RequestParam("role") String role){
        List<String> fees = feeService.getAllFeeByOperator(role);
        List<Map<String, Object>> result = new ArrayList<>();

        for (String fee : fees) {
            String[] values = fee.split(",");
            Map<String, Object> map = new HashMap<>();
            map.put("feeId", values[0]);
            map.put("paymentDate", values[1]);
            map.put("periodFee", values[2]);
            map.put("price", values[3]);
            map.put("phone", values[4]);
            result.add(map);
        }

        return result;
    }

    @GetMapping("/searchByFeeIdOrPaymentDate")
    public List<Fee>searchByFeeIdOrPaymentDate(@RequestParam(value = "feeId", defaultValue = "") String feeId, @RequestParam(value = "date", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date paymentDate){
        return feeService.searchByFeeIdOrPaymentDate(feeId, paymentDate);
    }

    @GetMapping("/numberOfFeeBuyThisYear")
    public Long numberOfFeeBuyThisYear(){
        return feeService.numberOfFeeBuyThisYear();
    }

    @GetMapping("/numberOfFeeBuyPerYear")
    public Long numberOfFeeBuyPerYear(@RequestParam("year") int year){
        return feeService.numberOfFeeBuyPerYear(year);
    }

    @GetMapping("/sumOfFeeBuyPerYear")
    public Long sumOfFeeBuyPerYear(@RequestParam("year") int year) {
        return feeService.sumOfFeeBuyPerYear(year);
    }

    @GetMapping("/listOfFeeAvailable")
    public List<Fee> listOfFeeAvailable(){
        return feeService.listOfFeeAvailable();
    }

    @GetMapping("/findUsersWithUnpaidFees")
    public List<Map<String, Object>> findUsersWithUnpaidFees(){

        List<String> fees = feeService.findUsersWithUnpaidFees();
        List<Map<String, Object>> result = new ArrayList<>();

        for (String fee : fees) {
            String[] values = fee.split(",");
            Map<String, Object> map = new HashMap<>();
            map.put("feeID", values[0]);
            map.put("price", values[1]);
            map.put("limitDate", values[2]);
            map.put("lastname", values[3]);
            map.put("firstname", values[4]);
            map.put("email", values[5]);
            map.put("phone", values[6]);
            result.add(map);
        }

        return result;

    }

    @GetMapping("/findFeeGeneratedThisCurrentYear")
    public Long findFeeGeneratedThisCurrentYear(){
        return feeService.findFeeGeneratedThisCurrentYear();
    }

    @GetMapping("/listOfFeeUnPaidPerMonth")
    public Long listOfFeeUnPaidPerMonth(){
        return feeService.listOfFeeUnPaidPerMonth();
    }

    @GetMapping("/listOfFeePaidPerMonth")
    public Long listOfFeePaidPerMonth(){
        return feeService.listOfFeePaidPerMonth();
    }

}
