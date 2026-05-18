package com.retina.ai.model;
import java.time.Instant;
public class Subscription { public final String userId; public final String planName; public int remainingAnalyses; public final Instant expiresAt; public Subscription(String u,String p,int r,Instant e){userId=u;planName=p;remainingAnalyses=r;expiresAt=e;} public void consumeOne(){if(remainingAnalyses>0) remainingAnalyses--;} }
