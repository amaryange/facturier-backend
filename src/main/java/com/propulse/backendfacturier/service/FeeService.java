package com.propulse.backendfacturier.service;

import com.propulse.backendfacturier.entity.Fee;
import com.propulse.backendfacturier.entity.Operator;
import com.propulse.backendfacturier.repository.FeeRepository;
import com.propulse.backendfacturier.repository.OperatorRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class FeeService {

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    public Fee addFee(@RequestBody Fee fee){
        return feeRepository.save(fee);
    }

    public Fee findFeeById(long id){
        return feeRepository.findFeeById(id);
    }

    public Fee findFeeByNumberBill(String numberBill){
        return feeRepository.findFeeByNumberBill(numberBill);
    }

    public Fee updateFee(@PathVariable Long id,@PathVariable String debtor){
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

    public Page<Map<String, Object>> findAllBills(Pageable pageable) {
        return feeRepository.findAllBills(pageable);
    }

    public Page<Map<String, Object>> findFeeByBillNumberFalseBetweenDate(String phone,
                                                            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                           Pageable pageable) {
        return feeRepository.findFeeByBillNumberFalseBetweenDate(phone, startDate, endDate, pageable);
    }

    public Page<Map<String, Object>> findFeeByBillNumberTrueBetweenDate(String phone,
                                                                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                         @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                                         Pageable pageable) {
        return feeRepository.findFeeByBillNumberTrueBetweenDate(phone, startDate, endDate, pageable);
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

    public Page<Map<String, Object>> findFeeByBillNumberFalse(String billNumber, Pageable pageable) {
        return feeRepository.findFeeByBillNumberFalse(billNumber, pageable);
    }

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

    public Long numberFeeByPhoneAndFeeStatusFalse(@RequestParam(name = "phone", defaultValue = "")String phone){
        return feeRepository.numberFeeByPhoneAndFeeStatusFalse(phone);
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

    public Page<Map<String, Object>> getAllFeeStatusFalseByOperator(String role, Pageable pageable) {
        return feeRepository.getAllFeeStatusFalseByOperator(role, pageable);
    }

    public Page<Map<String, Object>> getAllFeeStatusTrueByOperator(String role, Pageable pageable) {
        return feeRepository.getAllFeeStatusTrueByOperator(role, pageable);
    }

    /*
    public List<Fee> searchByFeeIdOrPaymentDate(@RequestParam("feeId") String feeId, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date paymentDate){
        return feeRepository.searchByFeeIdOrPaymentDate(feeId, paymentDate);
    }
     */

    public Page<Fee> searchByFeeIdOrPaymentDate(String feeId, Date paymentDate, Pageable pageable) {
        return feeRepository.searchByFeeIdOrPaymentDate(feeId, paymentDate, pageable);
    }

    public Page<Map<String, Object>> searchFeeByDateStatus(String label,
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                           Boolean status,
                                                           Pageable pageable) {
        return feeRepository.searchFeeByDateStatus(label, startDate, endDate, status, pageable);
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

    // Ajout des factures grâce à un fichier excel
    public List<Fee> parseInvoicesFromExcel(MultipartFile file) throws IOException {
        List<Fee> invoices = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0); // Supposons que les factures sont sur la première feuille.

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Ignorez la première ligne si elle contient des en-têtes.

        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Fee invoice = new Fee();

            // Récupérez les données de chaque colonne du fichier Excel et définissez-les dans l'objet InvoiceEntity.
            // ...
            Cell cell = row.getCell(0); // Première colonne
            if (cell.getCellType() == CellType.NUMERIC) {
                invoice.setFeeId(String.valueOf((long) cell.getNumericCellValue()));
            } else if (cell.getCellType() == CellType.STRING) {
                invoice.setFeeId(cell.getStringCellValue());
            }

            cell = row.getCell(1); // Deuxième colonne
            if (cell.getCellType() == CellType.NUMERIC) {
                int numericValue = (int) cell.getNumericCellValue();
                if (numericValue == 1) {
                    invoice.setFeeStatus(true); // La valeur est 0, donc le booléen est faux
                } else {
                    invoice.setFeeStatus(false); // La valeur est 1, donc le booléen est vrai
                    // Gérer le cas où la valeur n'est ni 0 ni 1, selon vos besoins.
                    // Par exemple, vous pouvez définir une valeur par défaut ou générer une exception.
                }
            }

            cell = row.getCell(2); // Troisième colonne pour la date
            String dateString = "";
            if (cell != null) {
                DataFormatter formatter = new DataFormatter();
                dateString = formatter.formatCellValue(cell).trim();
            }

            Date limitDate = null;
            if (!dateString.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    limitDate = sdf.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Ou lancez une exception pour signaler une erreur de format de date
                    // throw new RuntimeException("Format de date incorrect. Utilisez le format 'yyyy-MM-dd'");
                }
            }

            // À ce stade, la variable 'limitDate' contiendra la date à partir de la cellule sous forme d'objet Date.
            // Vous pouvez l'utiliser pour définir la propriété appropriée de votre objet 'invoice'.
            invoice.setLimitDate(limitDate);


            cell = row.getCell(3); // Quatrième colonne pour la date
            String dateStrings = "";
            if (cell != null) {
                DataFormatter formatter = new DataFormatter();
                dateStrings = formatter.formatCellValue(cell).trim();
            }

            Date periodFee = null;
            if (!dateStrings.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    periodFee = sdf.parse(dateStrings);
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Ou lancez une exception pour signaler une erreur de format de date
                    // throw new RuntimeException("Format de date incorrect. Utilisez le format 'yyyy-MM-dd'");
                }
            }

            // À ce stade, la variable 'limitDate' contiendra la date à partir de la cellule sous forme d'objet Date.
            // Vous pouvez l'utiliser pour définir la propriété appropriée de votre objet 'invoice'.
            invoice.setPeriodFee(periodFee);



            cell = row.getCell(4); // Cinquième colonne pour le téléphone
            String phone = "";
            if (cell != null) {
                DataFormatter formatter = new DataFormatter();
                Workbook workbooks = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbooks.getCreationHelper().createFormulaEvaluator();
                Cell evaluatedCell = evaluator.evaluateInCell(cell); // Évalue la cellule pour obtenir la valeur visible dans Excel
                phone = formatter.formatCellValue(evaluatedCell).trim();
            }
            // À ce stade, la variable 'phone' contiendra la valeur du téléphone sous forme de chaîne de caractères.
            // Vous pouvez l'utiliser pour définir la propriété appropriée de votre objet 'invoice'.
            invoice.setPhone(phone);

            cell = row.getCell(5); // Sixième colonne pour le prix
            if (cell.getCellType() == CellType.NUMERIC) {
                int price = (int) cell.getNumericCellValue(); // Lire la valeur de la cellule en tant que chaîne de caractères
                invoice.setPrice(price);
            }

            cell = row.getCell(6); // Septième colonne pour la chaîne de caractères
            if (cell.getCellType() == CellType.NUMERIC) {
                long idOperator = (long) cell.getNumericCellValue(); // Lire la valeur de la cellule comme un long
                Optional<Operator> optionalOperator = operatorRepository.findById(idOperator);
                if (optionalOperator.isPresent()) {
                    Operator operator = optionalOperator.get();
                    invoice.setOperator(operator);
                } else {
                    // Gérer le cas où l'opérateur avec l'identifiant spécifié n'a pas été trouvé.
                    // Par exemple, vous pouvez générer une exception ou définir une valeur par défaut.
                }
            }

            Cell cell1 = row.getCell(7); // Septième colonne
            if (cell1.getCellType() == CellType.NUMERIC) {
                invoice.setNumberBill(String.valueOf((long) cell1.getNumericCellValue()));
            } else if (cell1.getCellType() == CellType.STRING) {
                invoice.setFeeId(cell1.getStringCellValue());
            }

            /*
            // Ajoutez ici la validation des données avant d'enregistrer la facture.
            if (!isValidInvoice(invoice)) {
                // Gérez le cas où la facture n'est pas valide selon vos besoins.
                // Par exemple, vous pouvez ignorer la facture, générer une exception ou laisser l'utilisateur savoir qu'il y a des problèmes avec la facture.
                continue;
            }

             */

            invoices.add(invoice);

        }

        workbook.close();
        //System.out.println(invoices);

        // Enregistrez les factures dans la base de données en utilisant le repository JPA.
        return feeRepository.saveAll(invoices);
    }

    // Méthode pour valider la facture
    private boolean isValidInvoice(Fee invoice) {
        // Vérifiez que les champs obligatoires ne sont pas vides ou nuls
        if (invoice.getFeeId() == null || invoice.getPaymentDate() == null || invoice.getPeriodFee() == null || invoice.getPhone() == null || invoice.getOperator() == null) {
            return false;
        }

        // Vérifiez les valeurs des attributs spécifiques (par exemple, price et periodFee) pour s'assurer qu'ils ne sont pas nuls.
        if (invoice.getPrice() <= 0) {
            return false;
        }

        // Ajoutez d'autres validations selon vos besoins
        // Par exemple, vérifiez que les montants sont positifs, que les dates sont valides, etc.

        return true;
    }

}
