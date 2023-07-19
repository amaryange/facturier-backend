package com.propulse.backendfacturier.repository;

import com.propulse.backendfacturier.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    /*
    List<Fee> findFeeByPhone(String phone);
     */

    Page<Fee> findFeeByPhone(String phone, Pageable pageable);
    /*
    @Query(value = "SELECT * FROM fee f WHERE f.phone = :phone AND f.fee_status = false ", nativeQuery = true)
    List<Fee> findFeeByPhoneAndFeeStatus(String phone);

     */

    @Query(value = "SELECT f.feeId AS feeId, f.periodFee AS periodFee, f.limitDate AS limitDate, f.price AS price, " +
            "f.phone AS phone, f.debtor AS debtor, f.paymentDate AS paymentDate, o.name AS name , o.label AS label, " +
            "o.id AS idPicture FROM Fee f JOIN f.operator o WHERE f.phone = :phone AND f.feeStatus = false",
            countQuery = "SELECT COUNT(f) FROM Fee f WHERE f.phone = :phone AND f.feeStatus = false")
    Page<Map<String, Object>> findFeeByPhoneAndFeeStatus(String phone, Pageable pageable);


    // Total du mantant des factures en attentes pour un utilisateur
    @Query(value = "SELECT COALESCE(SUM(f.price), 0) AS total_factures_attente FROM fee f WHERE f.phone = :phone AND f.fee_status = false ", nativeQuery = true)
    Long sumFeeByPhoneAndFeeStatus(String phone);

    /*
    @Query(value = "SELECT * FROM fee f WHERE f.phone = :phone AND f.fee_status = true ", nativeQuery = true)
    List<Fee> findFeeByPhoneAndFeeStatusTrue(String phone);

     */

    @Query(value = "SELECT * FROM fee f WHERE f.phone = :phone AND f.fee_status = true",
            countQuery = "SELECT COUNT(*) FROM fee f WHERE f.phone = :phone AND f.fee_status = true",
            nativeQuery = true)
    Page<Fee> findFeeByPhoneAndFeeStatusTrue(String phone, Pageable pageable);

    // ### Nombre de factures payées pour un utilisateur
    @Query(value = "SELECT COUNT(*) AS number_factures_payes FROM fee f WHERE f.phone = :phone AND f.fee_status = true ", nativeQuery = true)
    Long numberFeeByPhoneAndFeeStatusTrue(String phone);

    // Total mensuel du mantant des factures payées pour un utilisateur
    @Query(value = "SELECT COALESCE(SUM(f.price), 0) AS total_mensuel FROM fee f WHERE f.phone = :phone AND f.fee_status = true AND MONTH(f.payment_date) = MONTH(CURRENT_DATE) AND YEAR(f.payment_date) = YEAR(CURRENT_DATE) ", nativeQuery = true)
    Long findFeeByPhoneAndFeeStatusTrueCurrentMonth(String phone);

    // Total annuel du mantant des factures payées pour un utilisateur
    @Query(value = "SELECT COALESCE(SUM(f.price), 0) AS total_annuel FROM fee f WHERE f.phone = :phone AND f.fee_status = true AND YEAR(f.payment_date) = YEAR(CURRENT_DATE) ", nativeQuery = true)
    Long findFeeByPhoneAndFeeStatusTrueCurrentYear(String phone);

    @Query(value = "SELECT COUNT(*) FROM fee f WHERE f.phone = :phone", nativeQuery = true)
    long count(String phone);

    @Query(value = "SELECT COALESCE(SUM(f.price), 0) AS somme_factures " +
            "FROM fee f " +
            "WHERE f.phone = :phone AND f.fee_status = TRUE", nativeQuery = true)
    Long countFeePriceByPerson(String phone);
    /*
    @Query(value = "SELECT f.*, o.label, o.name " +
            "FROM fee f, operator o " +
            "WHERE f.fee_status = false AND f.fee_id = :feeId AND o.id = f.id", nativeQuery = true)
    List<String> findAllFeeByUser(String feeId);

     */

    @Query(value = "SELECT f.debtor AS debtor, f.limit_date AS limitDate, f.payment_date AS paymentDate, f.period_fee AS periodFee, f.phone AS phone, f.price AS price, o.label AS label_operator, o.name AS name_operator " +
            "FROM fee f, operator o " +
            "WHERE f.fee_status = false AND f.fee_id = :feeId AND o.id = f.id",
            countQuery = "SELECT COUNT(f.debtor) " +
                    "FROM fee f, operator o " +
                    "WHERE f.fee_status = false AND f.fee_id = :feeId AND o.id = f.id",
            nativeQuery = true)
    Page<Map<String, Object>> findAllFeeByUser(String feeId, Pageable pageable);

    /*
    List<Fee> findFeeByDebtor(String debtor);

     */
    Page<Fee> findFeeByDebtor(String debtor, Pageable pageable);

    //-----------------------------------------------------------------------------------

    // Avoir la facture du mois en cours pour la personne connecté.
    @Query(value = "SELECT COUNT(f) " +
            "FROM Fee f " +
            "WHERE f.phone = :phone " +
            "AND MONTH(f.periodFee) = MONTH(CURRENT_DATE) AND YEAR(f.periodFee) = YEAR(CURRENT_DATE)")
    Long countFeeForCurrentMonthByPerson(String phone);

    // Avoir la somme des factures d'un utilisateur pour le mois en cours.
    @Query(value = "SELECT COALESCE(SUM(f.price), 0) AS somme_mois_courant " +
            "FROM fee f " +
            "WHERE f.phone = :phone " +
            "AND MONTH(f.period_fee) = MONTH(CURRENT_DATE) AND YEAR(f.period_fee) = YEAR(CURRENT_DATE)", nativeQuery = true)
    Long sumFeeForCurrentMonthByPerson(String phone);

    // Liste des factures des trois derniers mois.
    /*
    @Query(value = "SELECT * " +
            "FROM fee f " +
            "WHERE f.period_fee >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH) AND f.phone = :phone " +
            "AND f.period_fee <= CURDATE() ", nativeQuery = true)
    List<Fee> getFeesByCurrentDate(String phone);
     */

    // Liste des factures des trois derniers mois.
    @Query(value = "SELECT * " +
            "FROM fee f " +
            "WHERE f.period_fee >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH) AND f.phone = :phone " +
            "AND f.period_fee <= CURDATE()",
            countQuery = "SELECT COUNT(*) " +
                    "FROM fee f " +
                    "WHERE f.period_fee >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH) AND f.phone = :phone " +
                    "AND f.period_fee <= CURDATE()",
            nativeQuery = true)
    Page<Fee> getFeesByCurrentDate(String phone, Pageable pageable);


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
    /*
    @Query(value = "SELECT f.feeId, f.paymentDate, f.periodFee, f.price, f.phone FROM Fee f " +
            "JOIN f.operator o " +
            "WHERE o.label = :role ")
    List<String> getAllFeeByOperator(String role);

     */

    // Faire le listing des factures
    @Query(value = "SELECT f.feeId AS feeId, f.paymentDate AS paymentDate, f.periodFee AS periodFee, f.price AS price, f.phone AS phone " +
            "FROM Fee f " +
            "JOIN f.operator o " +
            "WHERE o.label = :role",
            countQuery = "SELECT COUNT(f) " +
                    "FROM Fee f " +
                    "JOIN f.operator o " +
                    "WHERE o.label = :role")
    Page<Map<String, Object>> getAllFeeByOperator(String role, Pageable pageable);



    // Faire une recherche sur des factures
    /*
    @Query(value = "SELECT f FROM Fee f WHERE f.feeId = :feeId OR f.paymentDate = :paymentDate")
    List<Fee> searchByFeeIdOrPaymentDate(String feeId, @DateTimeFormat(pattern = "yyyy-MM-dd") Date paymentDate);
     */

    // Faire une recherche sur des factures
    @Query(value = "SELECT f FROM Fee f WHERE f.feeId = :feeId OR f.paymentDate = :paymentDate",
            countQuery = "SELECT COUNT(f) FROM Fee f WHERE f.feeId = :feeId OR f.paymentDate = :paymentDate")
    Page<Fee> searchByFeeIdOrPaymentDate(String feeId, @DateTimeFormat(pattern = "yyyy-MM-dd") Date paymentDate, Pageable pageable);


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
    /*
    @Query("SELECT f FROM Fee f WHERE f.feeStatus = false ")
    List<Fee> listOfFeeAvailable();

     */

    @Query("SELECT f FROM Fee f WHERE f.feeStatus = false")
    Page<Fee> listOfFeeAvailable(Pageable pageable);

    // Liste des clients n’ayant pas payé leur facture apres la date limite
    /*
    @Query("SELECT f.feeId, f.price, f.limitDate, u.lastname, u.firstname, u.email, u.phone FROM Fee f " +
            "INNER JOIN User u ON f.phone = u.phone " +
            "WHERE f.feeStatus = false AND f.limitDate < CURRENT_DATE()")
    List<String> findUsersWithUnpaidFees();

     */

    // Liste des clients n’ayant pas payé leur facture apres la date limite
    @Query("SELECT f.feeId AS feeID, f.price AS price, f.limitDate AS limitDate, u.lastname AS lastname, u.firstname AS firstname, u.email AS email, u.phone AS phone, f.feeStatus AS feeStatus FROM Fee f " +
            "INNER JOIN User u ON f.phone = u.phone " +
            "WHERE f.feeStatus = false AND f.limitDate < CURRENT_DATE()")
    Page<Map<String, Object>> findUsersWithUnpaidFees(Pageable pageable);


    // Nombre de factures générées (sur l’année)
    @Query("SELECT COUNT(f) FROM Fee f")
    Long findFeeGeneratedThisCurrentYear();

    // nombre de factures non payées par mois
    //@Query("SELECT COUNT(f) FROM Fee f WHERE f.feeStatus = false AND f.limitDate < MONTH(CURRENT_DATE()) ")
    @Query("SELECT COUNT(f) FROM Fee f WHERE f.feeStatus = false AND MONTH(f.limitDate) = MONTH(CURRENT_DATE) ")
    Long numberOfFeeUnPaidPerMonth();

    // Liste des factures non payées par mois
    @Query(value = "SELECT f FROM Fee f WHERE f.feeStatus = false AND MONTH(f.limitDate) = MONTH(CURRENT_DATE) ",
            countQuery = "SELECT COUNT(f) FROM Fee f WHERE f.feeStatus = false AND MONTH(f.limitDate) = MONTH(CURRENT_DATE)")
    Page<Fee> listOfFeeUnPaidPerMonth(Pageable pageable);

    // Liste des factures payées par mois
    @Query(value = "SELECT f FROM Fee f WHERE f.feeStatus = true AND MONTH(f.limitDate) = MONTH(CURRENT_DATE) ",
            countQuery = "SELECT COUNT(f) FROM Fee f WHERE f.feeStatus = true AND MONTH(f.limitDate) = MONTH(CURRENT_DATE)")
    Page<Fee> listOfFeePaidPerMonth(Pageable pageable);
    // nombre de factures non payées par mois
    @Query("SELECT COUNT(f) FROM Fee f WHERE f.feeStatus = true AND MONTH(f.limitDate) < MONTH(CURRENT_DATE()) ")
    Long numberOfFeePaidPerMonth();



}
