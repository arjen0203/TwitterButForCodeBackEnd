package com.arjen0203.codex.service.auth.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.crypto.SecretKey;

import com.arjen0203.codex.domain.auth.exceptions.InvalidJwtException;
import com.arjen0203.codex.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Util class for validating using JWT. */
@Service
public class JwtUtil {
  private final SecretKey secretKey;
  private final int expiration;

  /** Constructs the JwtUtil with the given Secret key and expiration date. */
  public JwtUtil(@Value("${auth.jwt.secret}") String key, @Qualifier("expiration") int expiration) {
    secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    this.expiration = expiration;
  }

  /**
   * Extracts all the claims from a token.
   *
   * @param token the provided token
   * @return Optional of Claims empty if invalid otherwise contains the claims for the User
   */
  private Optional<Claims> extractAllClaims(String token) {
    try {
      return Optional.of(
          Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody());
    } catch (JwtException ex) {
      return Optional.empty();
    }
  }

  /**
   * Checks if the provided claims from the token are expired.
   *
   * @param claims the claims from the token
   * @return bool isExpired
   */
  private boolean isTokenExpired(Claims claims) {
    return claims.getExpiration().before(new Date());
  }

  /**
   * Builds the claims that we will add to the token and creates the token.
   *
   * @param user the user
   * @return String containing the token
   */
  public String generateToken(User user) {
    var claims = new HashMap<String, Object>();
    claims.put("userId", user.getId());
    claims.put("username", user.getUsername());
    claims.put("role", user.getRole().getName());

    return createToken(claims, user.getEmail());
  }

  /**
   * Creates the JWT from the provided claims.
   *
   * @param claims the User's claims
   * @param subject the User's subject (email in our case)
   * @return String containing the token
   */
  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * Validates the provided JWT.
   *
   * @param token the JWT
   * @return Claims containing all the claims for the user
   */
  public Claims validateToken(String token) {
    var oClaims = extractAllClaims(token);
    if (oClaims.isEmpty()) {
      throw new InvalidJwtException();
    }
    var claims = oClaims.get();
    if (isTokenExpired(claims)) {
      throw new InvalidJwtException();
    }
    return claims;
  }
}
