package com.example.campussecondhand.service;

import com.example.campussecondhand.entity.Message;
import com.example.campussecondhand.repository.MessageRepository;
import com.example.campussecondhand.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Message> findByReceiverId(Long userId) {
        List<Message> messages = messageRepository.findByReceiverId(userId);
        messages.forEach(m -> {
            if (m.getSender() == null) {
                m.setSender(userRepository.selectById(m.getSenderId()));
            }
        });
        return messages;
    }

    public List<Message> findBySenderId(Long userId) {
        List<Message> messages = messageRepository.findBySenderId(userId);
        messages.forEach(m -> {
            if (m.getReceiver() == null) {
                m.setReceiver(userRepository.selectById(m.getReceiverId()));
            }
        });
        return messages;
    }

    public List<Message> findConversation(Long user1Id, Long user2Id) {
        return messageRepository.findConversation(user1Id, user2Id);
    }

    public Message findById(Long id) {
        return messageRepository.selectById(id);
    }

    public int countUnread(Long userId) {
        return messageRepository.countUnread(userId);
    }

    public Message send(Message message) {
        message.setCreatedTime(LocalDateTime.now());
        message.setIsRead(0);
        messageRepository.insert(message);
        return message;
    }

    public boolean markAsRead(Long id) {
        Message message = messageRepository.selectById(id);
        if (message != null) {
            message.setIsRead(1);
            messageRepository.updateById(message);
            return true;
        }
        return false;
    }

    public boolean markAllAsRead(Long userId) {
        List<Message> messages = messageRepository.findByReceiverId(userId);
        messages.forEach(m -> {
            m.setIsRead(1);
            messageRepository.updateById(m);
        });
        return true;
    }

    public int delete(Long id) {
        return messageRepository.deleteById(id);
    }
}
