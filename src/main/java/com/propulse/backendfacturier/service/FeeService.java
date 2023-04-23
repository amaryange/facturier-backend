package com.propulse.backendfacturier.service;

import com.propulse.backendfacturier.entity.Fee;
import com.propulse.backendfacturier.repository.FeeRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class FeeService {

    @Autowired
    private FeeRepository feeRepository;

    public Fee addFee(@RequestBody Fee fee){
        return feeRepository.save(fee);
    }

    public Fee updateFee(@PathVariable Long id, @RequestBody Fee fee){
        fee.setId(id);
        return feeRepository.save(fee);
    }

    public List<Fee> findFeeByPhone(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeRepository.findFeeByPhone(phone);
    }

}
