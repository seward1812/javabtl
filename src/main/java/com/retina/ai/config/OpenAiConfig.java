package com.retina.ai.config;
public record OpenAiConfig(String apiKey, String baseUrl, String model, boolean enabled) {
 public static OpenAiConfig fromEnv(){return new OpenAiConfig(System.getenv().getOrDefault("OPENAI_API_KEY",""), System.getenv().getOrDefault("OPENAI_BASE_URL","https://api.openai.com/v1"), System.getenv().getOrDefault("OPENAI_MODEL","gpt-5.2"), Boolean.parseBoolean(System.getenv().getOrDefault("OPENAI_ENABLED","true")));}
}
