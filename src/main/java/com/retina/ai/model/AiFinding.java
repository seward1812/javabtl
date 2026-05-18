package com.retina.ai.model;
public record AiFinding(String label, String severity, double confidence, String boundingBox, String description) {}
