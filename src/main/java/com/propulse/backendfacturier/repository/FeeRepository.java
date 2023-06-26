package com.propulse.backendfacturier.repository;

import com.propulse.backendfacturier.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    List<Fee> findFeeByPhone(String phone);
    @Query(value = "SELECT * FROM fee f WHERE f.phone = :phone AND f.fee_status = false ", nativeQuery = true)
    List<Fee> findFeeByPhoneAndFeeStatus(String phone);

    @Query(value = "SELECT * FROM fee f WHERE f.phone = :phone AND f.fee_status = true ", nativeQuery = true)
    List<Fee> findFeeByPhoneAndFeeStatusTrue(String phone);
    @Query(value = "SELECT COUNT(*) FROM fee f WHERE f.phone = :phone", nativeQuery = true)
    long count(String phone);

    @Query(value = "SELECT COALESCE(SUM(f.price), 0) AS somme_factures " +
            "FROM fee f " +
            "WHERE f.phone = :phone AND f.fee_status = TRUE", nativeQuery = true)
    Long countFeePriceByPerson(String phone);

    @Query(value = "SELECT f.*, o.label, o.name " +
            "FROM fee f, operator o " +
            "WHERE f.fee_status = false AND f.fee_id = :feeId AND o.id = f.id", nativeQuery = true)
    List<String> findAllFeeByUser(String feeId);
    List<Fee> findFeeByDebtor(String debtor);

    //-----------------------------------------------------------------------------------

    // Avoir la facture du mois en cours pour la personne connecté.
    @Query(value = "SELECT COUNT(f) " +
            "FROM fee f " +
            "WHERE f.phone = :phone " +
            "AND MONTH(f.period_fee) = MONTH(CURRENT_DATE) AND YEAR(f.period_fee) = YEAR(CURRENT_DATE)", nativeQuery = true)
    Long countFeeForCurrentMonthByPerson(String phone);

    // Liste des factures des trois derniers mois.
    @Query(value = "SELECT * " +
            "FROM fee f " +
            "WHERE f.period_fee >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH) AND f.phone = :phone " +
            "AND f.period_fee <= CURDATE() ", nativeQuery = true)
    List<Fee> getFeesByCurrentDate(String phone);

    // Faire la somme des factures pour le mois en cours
    @Query(value= "SELECT COALESCE(SUM(f.price), 0) AS sommeFacturesMoisCourant " +
            "FROM Fee f JOIN f.operator o " +
            "WHERE MONTH(f.periodFee) = MONTH(CURRENT_DATE()) AND YEAR(f.periodFee) = YEAR(CURRENT_DATE()) AND o.label = :role ")
    Long getTotalFeeAmountForCurrentMonth(String role);

    // Faire la somme des factures pour le mois et l'année que l'utilisateur veut
    @Query(value="SELECT COALESCE(SUM(f.price), 0) AS sommeFacturesMoisCourantChoix " +
            "FROM Fee f JOIN f.operator o " +
            "WHERE MONTH(f.periodFee) = :month AND YEAR(f.periodFee) = :year AND o.label = :role")
    Long getTotalFeeAmountForMonthAndYear(int month, int year, String role);

    // Faire le compte des factures pour le mois en cours
    @Query(value = "SELECT COUNT(f) FROM Fee f JOIN f.operator o " +
            "WHERE MONTH(f.periodFee) = MONTH(CURRENT_DATE()) AND YEAR(f.periodFee) = YEAR(CURRENT_DATE()) AND o.label = :role ")
    Long getNumberOfInvoicesForCurrentMonth(String role);

    // Faire le compte des factures pour le mois et l'année que l'utilisateur veut
    @Query(value = "SELECT COUNT(f) FROM Fee f JOIN f.operator o WHERE MONTH(f.periodFee) = :month AND YEAR(f.periodFee) = :year AND o.label = :role ")
    Long getNumberOfInvoicesForMonthAndYear(int month, int year, String role);

    // Faire le listing des factures
    @Query(value = "SELECT f.feeId, f.paymentDate, f.periodFee, f.price, f.phone FROM Fee f " +
            "JOIN f.operator o " +
            "WHERE o.label = :role ")
    List<String> getAllFeeByOperator(String role);


    // Faire une recherche sur des factures
    @Query(value = "SELECT f FROM Fee f WHERE f.feeId = :feeId OR f.paymentDate = :paymentDate")
    List<Fee> searchByFeeIdOrPaymentDate(String feeId, @DateTimeFormat(pattern = "yyyy-MM-dd") Date paymentDate);

    // Nombre de factures payées sur l'année en cours
    @Query("SELECT COUNT(f) FROM Fee f WHERE f.feeStatus = true AND YEAR(f.paymentDate) = YEAR(CURRENT_DATE())")
    Long numberOfFeeBuyThisYear();

    // Nombre de factures payées sur l'année (au choix)
    @Query("SELECT COUNT(f) FROM Fee f WHERE f.feeStatus = true AND YEAR(f.paymentDate) =  :year ")
    Long numberOfFeeBuyPerYear(int year);

    // Somme des factures payées pour une année donnée
    @Query("SELECT COALESCE(SUM(f.price), 0) FROM Fee f WHERE f.feeStatus = true AND YEAR(f.paymentDate) =  :year ")
    Long sumOfFeeBuyPerYear(int year);

    // Liste des factures disponibles
    @Query("SELECT f FROM Fee f WHERE f.feeStatus = false ")
    List<Fee> listOfFeeAvailable();

    // Liste des clients n’ayant pas payé leur facture apres la date limite
    @Query("SELECT f.feeId, f.price, f.limitDate, u.lastname, u.firstname, u.email, u.phone FROM Fee f " +
            "INNER JOIN User u ON f.phone = u.phone " +
            "WHERE f.feeStatus = false AND f.limitDate < CURRENT_DATE()")
    List<String> findUsersWithUnpaidFees();

    // Nombre de factures générées (sur l’année)
    @Query("SELECT COUNT(f) FROM Fee f")
    Long findFeeGeneratedThisCurrentYear();

    // Liste des factures non payées par mois
    @Query("SELECT COUNT(f) FROM Fee f WHERE f.feeStatus = false AND f.limitDate < MONTH(CURRENT_DATE()) ")
    Long listOfFeeUnPaidPerMonth();

    // Liste des factures payées par mois
    @Query("SELECT COUNT(f) FROM Fee f WHERE f.feeStatus = true AND f.limitDate < MONTH(CURRENT_DATE()) ")
    Long listOfFeePaidPerMonth();



}
