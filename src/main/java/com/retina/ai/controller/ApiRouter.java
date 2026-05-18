package com.retina.ai.controller;
import com.retina.ai.dto.Requests; import com.retina.ai.model.RiskLevel; import com.retina.ai.service.PlatformService; import com.retina.ai.util.Json; import com.sun.net.httpserver.*; import java.io.*; import java.net.URI; import java.nio.charset.StandardCharsets; import java.util.*;
public class ApiRouter {
 private final PlatformService service; public ApiRouter(PlatformService service){this.service=service;}
 public HttpServer start(int port) throws IOException {HttpServer server=HttpServer.create(new java.net.InetSocketAddress(port),0); server.createContext("/api",this::handle); server.start(); return server;}
 private void handle(HttpExchange ex) throws IOException {try{URI u=ex.getRequestURI(); String path=u.getPath(); Map<String,String> q=query(u.getRawQuery()); Object out;
  if(path.equals("/api/health")) out=Map.of("status","UP","service","retina-ai-platform");
  else if(path.equals("/api/demo/register")) out=service.register(new Requests.Register(q.getOrDefault("email","patient@example.com"),q.getOrDefault("password","secret"),q.getOrDefault("name","Demo Patient"),Set.of(com.retina.ai.model.Role.USER)));
  else if(path.equals("/api/demo/upload")) out=service.upload(new Requests.UploadAnalysis(q.get("userId"),q.getOrDefault("imageType","FUNDUS"),List.of(new Requests.ImageInput("fundus-left.jpg","image/jpeg",123456),new Requests.ImageInput("oct-right.png","image/png",654321))));
  else if(path.equals("/api/user/analyses")) out=service.history(q.get("userId"));
  else if(path.startsWith("/api/user/analyses/")&&path.endsWith("/report.csv")){String id=path.substring("/api/user/analyses/".length(),path.length()-"/report.csv".length()); bytes(ex,"text/csv",service.csv(id).getBytes(StandardCharsets.UTF_8)); return;}
  else if(path.startsWith("/api/user/analyses/")&&path.endsWith("/report.pdf")){String id=path.substring("/api/user/analyses/".length(),path.length()-"/report.pdf".length()); bytes(ex,"application/pdf",service.pdf(id)); return;}
  else if(path.equals("/api/doctor/performance")) out=service.performance();
  else if(path.equals("/api/doctor/patients")) out=service.patients(q.get("doctorId"),q.get("query"),q.containsKey("riskLevel")?RiskLevel.valueOf(q.get("riskLevel")):null);
  else out=Map.of("message","Endpoint demo. Use service classes for full FR-1..FR-21 workflow.","path",path);
  bytes(ex,"application/json",Json.write(out).getBytes(StandardCharsets.UTF_8));
 }catch(Exception e){ex.sendResponseHeaders(400,0); ex.getResponseBody().write(Json.write(Map.of("error",e.getMessage())).getBytes(StandardCharsets.UTF_8)); ex.close();}}
 private void bytes(HttpExchange ex,String type,byte[] data)throws IOException{ex.getResponseHeaders().set("Content-Type",type); ex.sendResponseHeaders(200,data.length); ex.getResponseBody().write(data); ex.close();}
 private Map<String,String> query(String raw){Map<String,String> m=new HashMap<>(); if(raw==null||raw.isBlank())return m; for(String p:raw.split("&")){String[] kv=p.split("=",2); m.put(java.net.URLDecoder.decode(kv[0],StandardCharsets.UTF_8),kv.length>1?java.net.URLDecoder.decode(kv[1],StandardCharsets.UTF_8):"");} return m;}
}
