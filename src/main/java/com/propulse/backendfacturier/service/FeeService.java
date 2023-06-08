package com.propulse.backendfacturier.service;

import com.propulse.backendfacturier.entity.Fee;
import com.propulse.backendfacturier.repository.FeeRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class FeeService {

    @Autowired
    private FeeRepository feeRepository;

    public Fee addFee(@RequestBody Fee fee){
        return feeRepository.save(fee);
    }

    public Fee updateFee(@PathVariable Long id,@RequestBody String debtor){
        Optional<Fee> optionalFee = feeRepository.findById(id);
        if (optionalFee.isPresent()) {
            Fee existingFee = optionalFee.get();
            existingFee.setFeeStatus(true);
            existingFee.setDebtor(debtor);
            // mettez à jour tous les autres champs que vous souhaitez modifier
            Fee savedFee = feeRepository.save(existingFee);
            return savedFee;
        } else {
            return null;
        }
    }

    public List<Fee> findFeeByPhone(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeRepository.findFeeByPhone(phone);
    }
    public List<Fee> findFeeByPhoneAndFeeStatus(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeRepository.findFeeByPhoneAndFeeStatus(phone);
    }

    public List<Fee> findFeeByPhoneAndFeeStatusTrue(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeRepository.findFeeByPhoneAndFeeStatusTrue(phone);
    }

    public Long countAllFeeByPhone(@PathVariable String phone){
        return feeRepository.count(phone);
    }

    public Long countFeePriceByPerson(@PathVariable String phone){
        return feeRepository.countFeePriceByPerson(phone);
    }

    public List<String> findAllFeeByUser(@PathVariable String feeId){
        return feeRepository.findAllFeeByUser(feeId);
    }

    public List<Fee> findFeeByDebtor(@PathVariable String debtor){
        return feeRepository.findFeeByDebtor(debtor);
    }
}
