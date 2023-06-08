package com.propulse.backendfacturier.controller;

import com.propulse.backendfacturier.entity.Fee;
import com.propulse.backendfacturier.entity.Operator;
import com.propulse.backendfacturier.service.FeeService;
import com.propulse.backendfacturier.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/getFeeByOperator/{feeId}")
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
}
