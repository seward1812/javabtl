package com.retina.ai.dto;
import com.retina.ai.model.*; import java.time.Instant; import java.util.*;
public final class Responses { private Responses(){}
 public record Auth(String token,String userId,String email,Set<Role> roles){}
 public record Analysis(String id,String patientId,AnalysisStatus status,RiskLevel riskLevel,double riskScore,List<AnalysisImage> images,List<AiFinding> findings,String aiConclusion,String recommendation,String annotatedImageUrl,boolean doctorConfirmed,String doctorConclusion,String doctorNotes,Instant createdAt,Instant readyAt){}
 public record Performance(long totalAnalyses,long reviewedAnalyses,double averageRiskScore,long highRiskCount){}
}
