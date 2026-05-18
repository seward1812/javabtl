package com.retina.ai.dto;
import com.retina.ai.model.RiskLevel; import com.retina.ai.model.Role; import java.math.BigDecimal; import java.util.Set;
public final class Requests { private Requests(){}
 public record Register(String email,String password,String fullName,Set<Role> roles){} public record Login(String email,String password){} public record SocialLogin(String provider,String providerToken,String email,String fullName){}
 public record UploadAnalysis(String patientId,String imageType,java.util.List<ImageInput> images){} public record ImageInput(String fileName,String contentType,long sizeBytes){}
 public record DoctorReview(String conclusion,String notes,RiskLevel adjustedRiskLevel,boolean confirmed){} public record AiFeedback(String analysisId,String feedback,boolean aiWasAccurate){}
 public record SendMessage(String senderId,String recipientId,String body){} public record Purchase(String planName,int analyses,BigDecimal amount){}
}
