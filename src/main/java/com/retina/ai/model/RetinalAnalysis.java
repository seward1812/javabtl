package com.retina.ai.model;
import java.time.Instant; import java.util.*;
public class RetinalAnalysis {
  public final String id; public final String patientId; public AnalysisStatus status=AnalysisStatus.QUEUED; public RiskLevel riskLevel=RiskLevel.LOW; public double riskScore;
  public List<AnalysisImage> images=new ArrayList<>(); public List<AiFinding> findings=new ArrayList<>(); public String aiConclusion; public String recommendation; public String annotatedImageUrl;
  public String doctorId; public String doctorConclusion; public String doctorNotes; public boolean doctorConfirmed; public final Instant createdAt=Instant.now(); public Instant readyAt;
  public RetinalAnalysis(String id,String patientId){this.id=id;this.patientId=patientId;}
}
