package com.propulse.backendfacturier.repository;

import com.propulse.backendfacturier.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findMessageByReceiptEmail(String receiptEmail);
    List<Message> findMessageBySenderEmail(String senderEmail);
    @Query(value="SELECT * FROM message m WHERE (m.sender_email = :sender AND m.receipt_email = :receipt) OR (m.sender_email = :receipt AND m.receipt_email = :sender) ORDER BY m.date ASC", nativeQuery = true)
    List<Message> findSenderAndReceiptEmails(@Param("sender") String sender, @Param("receipt") String receipt);

    @Query("SELECT DISTINCT u.firstname AS firstname, u.lastname AS lastname " +
            "FROM User u " +
            "INNER JOIN Message m ON u.email = m.senderEmail " +
            "OR u.email = m.receiptEmail " +
            "WHERE u.email != :adminMail "
    )
    List<String> findUserDiscussAdmin(String adminMail);

    @Query("SELECT COUNT(m) FROM Message m WHERE YEAR(m.date) = YEAR(CURRENT_DATE) ")
    Long totalMessagePerYear();

    @Query("SELECT COUNT(m) FROM Message m WHERE MONTH(m.date) = MONTH(CURRENT_DATE) AND YEAR(m.date) = YEAR(CURRENT_DATE) ")
    Long totalMessagePerMonth();

}
