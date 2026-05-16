package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("""
        SELECT m FROM Message m
        WHERE (m.sender = :user1 AND m.receiver = :user2)
           OR (m.sender = :user2 AND m.receiver = :user1)
        ORDER BY m.createdAt ASC
        """)
    List<Message> findConversation(
        @Param("user1") String user1,
        @Param("user2") String user2
    );

    List<Message> findByReceiverAndSenderAndReadStatusFalse(
        String receiver,
        String sender
    );

    long countByReceiverAndReadStatusFalse(String receiver);

    @Query("""
        SELECT DISTINCT
            CASE
                WHEN m.sender = :admin THEN m.receiver
                ELSE m.sender
            END
        FROM Message m
        WHERE m.sender = :admin OR m.receiver = :admin
        """)
    List<String> findChatUsers(@Param("admin") String admin);

    @Modifying
    @Transactional
    @Query("""
        UPDATE Message m
        SET m.readStatus = true
        WHERE m.receiver = :receiver
          AND m.sender = :sender
          AND m.readStatus = false
        """)
    void markMessagesAsRead(
        @Param("receiver") String receiver,
        @Param("sender") String sender
    );
}