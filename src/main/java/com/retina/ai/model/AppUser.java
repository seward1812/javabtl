package com.retina.ai.model;
import java.time.Instant; import java.util.*;
public class AppUser {
  public final String id; public String email; public String passwordHash; public String authProvider; public Set<Role> roles; public PatientProfile profile = new PatientProfile(); public final Instant createdAt = Instant.now();
  public AppUser(String id, String email, String passwordHash, String authProvider, Set<Role> roles){this.id=id;this.email=email;this.passwordHash=passwordHash;this.authProvider=authProvider;this.roles=roles;}
}
