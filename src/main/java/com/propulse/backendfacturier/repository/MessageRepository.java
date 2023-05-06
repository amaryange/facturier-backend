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

}
