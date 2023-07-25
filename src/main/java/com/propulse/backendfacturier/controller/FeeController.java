package com.propulse.backendfacturier.controller;

import com.propulse.backendfacturier.entity.Fee;
import com.propulse.backendfacturier.entity.Operator;
import com.propulse.backendfacturier.service.FeeService;
import com.propulse.backendfacturier.service.OperatorService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/fee")
public class FeeController {
    @Autowired
    private FeeService feeService;

    /*
    @GetMapping("/all?phone=")
    public List<Fee> getAllFee(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeService.findFeeByPhone(phone);
    }

     */

    @GetMapping("/all")
    public ResponseEntity<Page<Fee>> getAllFeeOrFeeByPhone(
            @RequestParam String phone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Fee> feesPage = feeService.findFeeByPhone(phone, pageable);
        return ResponseEntity.ok(feesPage);
    }

    /*
    @GetMapping("/allFee?phone=")
    public List<Fee> findFeeByPhoneAndFeeStatus(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeService.findFeeByPhone(phone);
    }
     */

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

    /*
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

     */
    @GetMapping("/findAllFeeByUser")
    public ResponseEntity<Page<Map<String, Object>>> findAllFeeByUser(
            @RequestParam String feeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Map<String, Object>> feePage = feeService.findAllFeeByUser(feeId, pageable);
        return ResponseEntity.ok(feePage);
    }

    @GetMapping("/findAllBills")
    public ResponseEntity<Page<Map<String, Object>>> findAllBills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Map<String, Object>> feePage = feeService.findAllBills(pageable);
        return ResponseEntity.ok(feePage);
    }

    @GetMapping("/findFeeByBillNumberFalseBetweenDate")
    public ResponseEntity<Page<Map<String, Object>>> findFeeByBillNumberFalseBetweenDate(
            @RequestParam String phone,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Map<String, Object>> feePage = feeService.findFeeByBillNumberFalseBetweenDate(phone, startDate, endDate, pageable);
        return ResponseEntity.ok(feePage);
    }

    @GetMapping("/findFeeByBillNumberTrueBetweenDate")
    public ResponseEntity<Page<Map<String, Object>>> findFeeByBillNumberTrueBetweenDate(
            @RequestParam String phone,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Map<String, Object>> feePage = feeService.findFeeByBillNumberTrueBetweenDate(phone, startDate, endDate, pageable);
        return ResponseEntity.ok(feePage);
    }

    @PreAuthorize("hasAuthority('User')")
    @PostMapping("/update/{id}/{debtor}")
    public Fee completedFee(@PathVariable Long id,@PathVariable String debtor){
        return feeService.updateFee(id, debtor);
    }
    /*
    @PreAuthorize("hasAuthority('User')")
    @GetMapping("/getFee/{debtor}")
    public List<Fee> findFeeByDebtor(@PathVariable String debtor){
        return feeService.findFeeByDebtor(debtor);
    }

     */

    @PreAuthorize("hasAuthority('User')")
    @GetMapping("/findFeeByDebtor")
    public ResponseEntity<Page<Fee>> findFeeByDebtor(
            @RequestParam String debtor,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Fee> feePage = feeService.findFeeByDebtor(debtor, pageable);
        return ResponseEntity.ok(feePage);
    }

    @GetMapping("/countFeeForCurrentMonth/{phone}")
    public Long countFeeForCurrentMonthByPerson(@PathVariable String phone){
        return feeService.countFeeForCurrentMonthByPerson(phone);
    }

    @GetMapping("/sumFeeForCurrentMonthByPerson/{phone}")
    public Long sumFeeForCurrentMonthByPerson(@PathVariable String phone){
        return feeService.sumFeeForCurrentMonthByPerson(phone);
    }
    /*
    @GetMapping("/countFeeByCurrentDateAndThreeLastMonth/{phone}")
    public List<Fee> getFeesByCurrentDate(@PathVariable String phone){
        return feeService.getFeesByCurrentDate(phone);
    }

     */

    @GetMapping("/countFeeByCurrentDateAndThreeLastMonth")
    public ResponseEntity<Page<Fee>> getFeesByCurrentDate(
            @RequestParam String phone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Fee> feePage = feeService.getFeesByCurrentDate(phone, pageable);
        return ResponseEntity.ok(feePage);
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

    /*
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

     */
    @GetMapping("/getAllFeeByOperator")
    public ResponseEntity<Page<Map<String, Object>>> getAllFeeByOperator(
            @RequestParam String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Map<String, Object>> feePage = feeService.getAllFeeByOperator(role, pageable);
        return ResponseEntity.ok(feePage);
    }

    @GetMapping("/getAllFeeStatusFalseByOperator")
    public ResponseEntity<Page<Map<String, Object>>> getAllFeeStatusFalseByOperator(
            @RequestParam String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Map<String, Object>> feePage = feeService.getAllFeeStatusFalseByOperator(role, pageable);
        return ResponseEntity.ok(feePage);
    }

    @GetMapping("/getAllFeeStatusTrueByOperator")
    public ResponseEntity<Page<Map<String, Object>>> getAllFeeStatusTrueByOperator(
            @RequestParam String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Map<String, Object>> feePage = feeService.getAllFeeStatusTrueByOperator(role, pageable);
        return ResponseEntity.ok(feePage);
    }

    /*
    @GetMapping("/searchByFeeIdOrPaymentDate")
    public List<Fee>searchByFeeIdOrPaymentDate(@RequestParam(value = "feeId", defaultValue = "") String feeId, @RequestParam(value = "date", defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date paymentDate){
        return feeService.searchByFeeIdOrPaymentDate(feeId, paymentDate);
    }

     */

    @GetMapping("/searchByFeeIdOrPaymentDate")
    public ResponseEntity<Page<Fee>> searchByFeeIdOrPaymentDate(
            @RequestParam(required = false) String feeId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date paymentDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Fee> feePage = feeService.searchByFeeIdOrPaymentDate(feeId, paymentDate, pageable);
        return ResponseEntity.ok(feePage);
    }
    @GetMapping("/searchFeeByDateStatus")
    public ResponseEntity<Page<Map<String, Object>>> searchFeeByDateStatus(
            @RequestParam(required = false) String label,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(required = false) Boolean status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Map<String, Object>> feePage = feeService.searchFeeByDateStatus(label, startDate, endDate, status, pageable);
        return ResponseEntity.ok(feePage);
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
    /*
    @GetMapping("/listOfFeeAvailable")
    public List<Fee> listOfFeeAvailable(){
        return feeService.listOfFeeAvailable();
    }
     */
    @GetMapping("/listOfFeeAvailable")
    public ResponseEntity<Page<Fee>> listOfFeeAvailable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Fee> feesPage = feeService.listOfFeeAvailable(pageable);
        return ResponseEntity.ok(feesPage);
    }
    /*
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

     */
    @GetMapping("/findUsersWithUnpaidFees")
    public ResponseEntity<Page<Map<String, Object>>> findUsersWithUnpaidFees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Map<String, Object>> unpaidFeesPage = feeService.findUsersWithUnpaidFees(pageable);
        return ResponseEntity.ok(unpaidFeesPage);
    }

    @GetMapping("/findFeeGeneratedThisCurrentYear")
    public Long findFeeGeneratedThisCurrentYear(){
        return feeService.findFeeGeneratedThisCurrentYear();
    }

    @GetMapping("/listOfFeeUnPaidPerMonth")
    public ResponseEntity<Page<Fee>> listOfFeeUnPaidPerMonth(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<Fee> unpaidFeesPagePermonth = feeService.listOfFeeUnPaidPerMonth(pageable);
        return ResponseEntity.ok(unpaidFeesPagePermonth);
    }

    @GetMapping("/listOfFeePaidPerMonth")
    public ResponseEntity<Page<Fee>> listOfFeePaidPerMonth(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<Fee> paidFeesPagePermonth = feeService.listOfFeePaidPerMonth(pageable);
        return ResponseEntity.ok(paidFeesPagePermonth);
    }

    @PostMapping("/uploadInvoices")
    public Map<String, Object> uploadInvoices(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, Object> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("success", false);
            response.put("message", "Veuillez sélectionner un fichier à télécharger.");
            return response;
        }

        List<Fee> invoices = feeService.parseInvoicesFromExcel(file);

        // Traitez les factures ici (par exemple, enregistrez-les dans la base de données).

        response.put("success", true);
        response.put("message", "Téléchargement des factures réussi.");
        response.put("numberOfInvoices", invoices.size());

        return response;
    }

    /*
    @GetMapping("/downloadInvoices")
    public Map<String, Object> downloadInvoices(@RequestParam("invoiceId") long invoiceId) {
        Map<String, Object> response = new HashMap<>();

        String filePath = "src/main/resources/templates/receipt.jrxml";

        Fee invoice = feeService.findFeeById(invoiceId);

        // Traitez les factures ici (par exemple, enregistrez-les dans la base de données).

        response.put("firstname", invoice.getOperator().getName());
        response.put("lastname", invoice.getPhone());
        response.put("facture", invoice.getFeeId());

        return response;
    }

     */

    @GetMapping("/downloadInvoices")
    public ResponseEntity<byte[]> downloadInvoices(@RequestParam("numberBill") String numberBill) throws JRException {

        Fee invoice = feeService.findFeeByNumberBill(numberBill);

        // Créez un HashMap pour les paramètres du rapport.
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("firstname", invoice.getOperator().getLabel());
        parameters.put("lastname", invoice.getPhone());
        parameters.put("facture", invoice.getFeeId());
        parameters.put("price", invoice.getPrice());

        // Chargez le modèle .jrxml à partir du fichier.
        String jrxmlFilePath = "src/main/resources/templates/receipt.jrxml";
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFilePath);

        // Remplissez le rapport avec les paramètres du rapport et les données de la facture (dataSource est facultatif car les données de la facture sont déjà définies dans les paramètres).
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        // Exportez le rapport au format PDF.
        byte[] pdfBytes;
        try {
            pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            // Gérer l'erreur ici si nécessaire.
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Spécifiez les en-têtes de la réponse pour le téléchargement du fichier PDF.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "invoice.pdf");

        // Renvoie la réponse avec le contenu du fichier PDF en tant que tableau d'octets.
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }



}
