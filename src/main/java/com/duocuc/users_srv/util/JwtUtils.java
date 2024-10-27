package com.duocuc.users_srv.util;

import com.duocuc.users_srv.config.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

  @Autowired
  private JwtConfig jwtConfig;

  // Método para obtener la clave secreta en formato Key
  private Key getSigningKey() {
    byte[] keyBytes = Base64.getDecoder().decode(jwtConfig.getSecret());
    return Keys.hmacShaKeyFor(keyBytes); // Usa una clave de al menos 512 bits
  }

  public String generateJwtToken(Authentication authentication) {
    return Jwts.builder()
        .setSubject(authentication.getName())
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtConfig.getExpirationMs()))
        .signWith(getSigningKey(), SignatureAlgorithm.HS512)
        .compact();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  public String getUsernameFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
}
