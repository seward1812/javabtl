package com.retina.ai.service;
import com.retina.ai.config.OpenAiConfig; import com.retina.ai.model.*; import java.net.URI; import java.net.http.*; import java.nio.charset.StandardCharsets; import java.time.Duration; import java.util.*;
public class OpenAiVisionService {
 private final OpenAiConfig cfg; public OpenAiVisionService(OpenAiConfig cfg){this.cfg=cfg;}
 public AiDecision analyze(List<AnalysisImage> images,List<AiFinding> findings,double score,RiskLevel risk){
  if(!cfg.enabled()||cfg.apiKey()==null||cfg.apiKey().isBlank()) return fallback(findings,score,risk);
  try { String prompt="Bạn là trợ lý AI hỗ trợ bác sĩ nhãn khoa. Tóm tắt nguy cơ, vùng mạch máu ảnh hưởng và khuyến nghị an toàn. Không thay thế bác sĩ. findings="+findings;
   String body="{\"model\":\""+json(cfg.model())+"\",\"input\":\""+json(prompt)+"\"}";
   HttpRequest req=HttpRequest.newBuilder(URI.create(cfg.baseUrl()+"/responses")).timeout(Duration.ofSeconds(20)).header("Authorization","Bearer "+cfg.apiKey()).header("Content-Type","application/json").POST(HttpRequest.BodyPublishers.ofString(body,StandardCharsets.UTF_8)).build();
   HttpResponse<String> res=HttpClient.newHttpClient().send(req,HttpResponse.BodyHandlers.ofString());
   if(res.statusCode()/100==2) return new AiDecision("OpenAI Responses API đã tạo diễn giải hỗ trợ bác sĩ. Raw response rút gọn: "+res.body().substring(0,Math.min(300,res.body().length())), recommendation(risk), true);
  } catch(Exception ignored) { }
  return fallback(findings,score,risk);
 }
 private AiDecision fallback(List<AiFinding> f,double s,RiskLevel r){String labels=f.stream().map(AiFinding::label).distinct().reduce((a,b)->a+", "+b).orElse("không phát hiện bất thường rõ"); return new AiDecision("AI mô phỏng ghi nhận "+labels+". Điểm rủi ro "+Math.round(s*100)+"%, mức "+r+". Bác sĩ cần xác nhận.", recommendation(r), false);} 
 private String recommendation(RiskLevel r){return switch(r){case LOW->"Theo dõi định kỳ và duy trì kiểm soát bệnh nền.";case MODERATE->"Đặt lịch tư vấn trong vài tuần.";case HIGH->"Ưu tiên bác sĩ xem xét sớm.";case CRITICAL->"Liên hệ cơ sở y tế ngay.";};}
 private String json(String s){return s.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n");}
 public record AiDecision(String conclusion,String recommendation,boolean generatedByOpenAi){}
}
