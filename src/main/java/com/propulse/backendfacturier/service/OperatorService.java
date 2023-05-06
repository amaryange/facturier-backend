package com.propulse.backendfacturier.service;

import com.propulse.backendfacturier.entity.Operator;
import com.propulse.backendfacturier.repository.OperatorRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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

    public List<Operator> getAllOperators(){
        return operatorRepository.findAll();
    }

}
