package com.propulse.backendfacturier.service;

import com.propulse.backendfacturier.entity.Message;
import com.propulse.backendfacturier.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(Message message){
        return messageRepository.save(message);
    }

    public List<Message> getAllMessageSender(String sender){
        return messageRepository.findMessageBySenderEmail(sender);
    }

    public List<Message> getAllMessageReceipt(String receipt){
        return messageRepository.findMessageByReceiptEmail(receipt);
    }

    public List<Message> findSenderAndReceiptEmails(String sender, String receipt){
        return messageRepository.findSenderAndReceiptEmails(sender, receipt);
    }

    public Long totalMessagePerYear() {
        return messageRepository.totalMessagePerYear();
    }

    public Long totalMessagePerMonth() {
        return messageRepository.totalMessagePerMonth();
    }

    public List<String> findUserDiscussAdmin(String adminMail){
        return messageRepository.findUserDiscussAdmin(adminMail);
    }

}
