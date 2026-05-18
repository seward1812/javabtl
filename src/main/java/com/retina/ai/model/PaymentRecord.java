package com.retina.ai.model;
import java.math.BigDecimal; import java.time.Instant;
public record PaymentRecord(String id, String userId, String planName, BigDecimal amount, String status, Instant paidAt) {}
