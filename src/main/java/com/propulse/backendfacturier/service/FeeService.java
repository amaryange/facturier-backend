package com.propulse.backendfacturier.service;

import com.propulse.backendfacturier.entity.Fee;
import com.propulse.backendfacturier.repository.FeeRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
            Date currentDate = new Date();
            existingFee.setPaymentDate(currentDate);
            // mettez à jour tous les autres champs que vous souhaitez modifier
            Fee savedFee = feeRepository.save(existingFee);
            return savedFee;
        } else {
            return null;
        }
    }

    /*
    public List<Fee> findFeeByPhone(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeRepository.findFeeByPhone(phone);
    }

     */

    public Page<Fee> findFeeByPhone(String phone, Pageable pageable) {
        return feeRepository.findFeeByPhone(phone, pageable);
    }

    /*
    public List<Fee> findFeeByPhoneAndFeeStatus(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeRepository.findFeeByPhoneAndFeeStatus(phone);
    }
     */

    public Page<Map<String, Object>> findFeeByPhoneAndFeeStatus(String phone, Pageable pageable) {
        return feeRepository.findFeeByPhoneAndFeeStatus(phone, pageable);
    }

    public Long sumFeeByPhoneAndFeeStatus(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeRepository.sumFeeByPhoneAndFeeStatus(phone);
    }

    /*
    public List<Fee> findFeeByPhoneAndFeeStatusTrue(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeRepository.findFeeByPhoneAndFeeStatusTrue (phone);
    }

     */

    public Page<Fee> findFeeByPhoneAndFeeStatusTrue(String phone, Pageable pageable) {
        return feeRepository.findFeeByPhoneAndFeeStatusTrue(phone, pageable);
    }

    public Long numberFeeByPhoneAndFeeStatusTrue(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeRepository.numberFeeByPhoneAndFeeStatusTrue(phone);
    }

    public Long findFeeByPhoneAndFeeStatusTrueCurrentMonth(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeRepository.findFeeByPhoneAndFeeStatusTrueCurrentMonth(phone);
    }

    public Long findFeeByPhoneAndFeeStatusTrueCurrentYear(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeRepository.findFeeByPhoneAndFeeStatusTrueCurrentYear(phone);
    }

    public Long countAllFeeByPhone(@PathVariable String phone){
        return feeRepository.count(phone);
    }

    public Long countFeePriceByPerson(@PathVariable String phone){
        return feeRepository.countFeePriceByPerson(phone);
    }

    /*
    public List<String> findAllFeeByUser(@PathVariable String feeId){
        return feeRepository.findAllFeeByUser(feeId);
    }

     */

    public Page<Map<String, Object>> findAllFeeByUser(String feeId, Pageable pageable) {
        return feeRepository.findAllFeeByUser(feeId, pageable);
    }
    /*
    public List<Fee> findFeeByDebtor(@PathVariable String debtor){
        return feeRepository.findFeeByDebtor(debtor);
    }

     */
    public Page<Fee> findFeeByDebtor(String debtor, Pageable pageable) {
        return feeRepository.findFeeByDebtor(debtor, pageable);
    }

    public Long countFeeForCurrentMonthByPerson(@PathVariable String phone){
        return feeRepository.countFeeForCurrentMonthByPerson(phone);
    }

    public Long sumFeeForCurrentMonthByPerson(@PathVariable String phone){
        return feeRepository.sumFeeForCurrentMonthByPerson(phone);
    }
    /*
    public List<Fee> getFeesByCurrentDate(@PathVariable String phone){
        return feeRepository.getFeesByCurrentDate(phone);
    }
     */

    public Page<Fee> getFeesByCurrentDate(String phone, Pageable pageable) {
        return feeRepository.getFeesByCurrentDate(phone, pageable);
    }

    public Long getTotalFeeAmountForCurrentMonth(@RequestParam("role") String role){
        return feeRepository.getTotalFeeAmountForCurrentMonth(role);
    }

    public Long getTotalFeeAmountForMonthAndYear(@RequestParam("month") int month, @RequestParam("year") int year, @RequestParam("role") String role){
        return feeRepository.getTotalFeeAmountForMonthAndYear(month, year, role);
    }

    public Long getNumberOfInvoicesForCurrentMonth(@RequestParam("role") String role){
        return feeRepository.getNumberOfInvoicesForCurrentMonth(role);
    }

    public Long getNumberOfInvoicesForMonthAndYear(@RequestParam("month") int month, @RequestParam("year") int year, @RequestParam("role") String role){
        return feeRepository.getNumberOfInvoicesForMonthAndYear(month, year, role);
    }

    /*
    public List<String> getAllFeeByOperator(@RequestParam("role") String role){
        return feeRepository.getAllFeeByOperator(role);
    }
     */

    public Page<Map<String, Object>> getAllFeeByOperator(String role, Pageable pageable) {
        return feeRepository.getAllFeeByOperator(role, pageable);
    }

    /*
    public List<Fee> searchByFeeIdOrPaymentDate(@RequestParam("feeId") String feeId, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date paymentDate){
        return feeRepository.searchByFeeIdOrPaymentDate(feeId, paymentDate);
    }
     */

    public Page<Fee> searchByFeeIdOrPaymentDate(String feeId, Date paymentDate, Pageable pageable) {
        return feeRepository.searchByFeeIdOrPaymentDate(feeId, paymentDate, pageable);
    }


    public Long numberOfFeeBuyThisYear(){
        return feeRepository.numberOfFeeBuyThisYear();
    }

    public Long numberOfFeeBuyPerYear(@RequestParam("year") int year){
        return feeRepository.numberOfFeeBuyPerYear(year);
    }

    public Long sumOfFeeBuyPerYear(@RequestParam("year") int year){
        return feeRepository.sumOfFeeBuyPerYear(year);
    }
    /*
    public List<Fee> listOfFeeAvailable(){
        return feeRepository.listOfFeeAvailable();
    }

     */

    public Page<Fee> listOfFeeAvailable(Pageable pageable) {
        return feeRepository.listOfFeeAvailable(pageable);
    }
    /*
    public List<String> findUsersWithUnpaidFees(){
        return feeRepository.findUsersWithUnpaidFees();
    }

     */

    public Page<Map<String, Object>> findUsersWithUnpaidFees(Pageable pageable) {
        return feeRepository.findUsersWithUnpaidFees(pageable);
    }

    public Long findFeeGeneratedThisCurrentYear(){
        return feeRepository.findFeeGeneratedThisCurrentYear();
    }

    public Page<Fee> listOfFeeUnPaidPerMonth(Pageable pageable){
        return feeRepository.listOfFeeUnPaidPerMonth(pageable);
    }

    public Page<Fee> listOfFeePaidPerMonth(Pageable pageable){
        return feeRepository.listOfFeePaidPerMonth(pageable);
    }


}
