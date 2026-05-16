package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Message;
import com.example.demo.repository.MessageRepository;

@RestController
@RequestMapping("/messages")
@CrossOrigin
public class MessageController {

    @Autowired
    private MessageRepository repository;

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        return repository.save(message);
    }

    @GetMapping("/conversation")
    public List<Message> conversation(
        @RequestParam String user1,
        @RequestParam String user2
    ) {
        return repository.findConversation(user1, user2);
    }

    @GetMapping("/users")
    public List<String> user(@RequestParam String admin) {
        return repository.findChatUsers(admin);
    }

    @GetMapping("/unread-count")
    public long unreadCount(@RequestParam String username) {
        return repository.countByReceiverAndReadStatusFalse(username);
    }

    @PutMapping("/read")
    public void markRead(
        @RequestParam String username,
        @RequestParam String sender
    ) {
        repository.markMessagesAsRead(username, sender);
    }
}