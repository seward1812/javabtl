package com.retina.ai.model;
import java.time.Instant;
public record ChatMessage(String id, String threadId, String senderId, String recipientId, String body, Instant sentAt) {}
