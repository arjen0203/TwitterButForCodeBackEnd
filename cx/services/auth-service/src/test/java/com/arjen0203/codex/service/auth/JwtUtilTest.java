package com.arjen0203.codex.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import com.arjen0203.codex.domain.auth.exceptions.InvalidJwtException;
import com.arjen0203.codex.domain.user.entity.Role;
import com.arjen0203.codex.domain.user.entity.User;
import com.arjen0203.codex.service.auth.utils.JwtUtil;
import org.junit.jupiter.api.Test;

class JwtUtilTest {
  private static final UUID MOCK_UUID = UUID.randomUUID();
  private static final String MOCK_USERNAME = "rens";
  private static final String MOCK_EMAIL = "rens@gmail.com";
  private static final String MOCK_PASSWORD = "password";
  private static final String MOCK_JWT_SECRET = "A".repeat(64);
  private static final Role MOCK_ROLE = new Role(1, "Admin");

  private final JwtUtil jwtUtil = new JwtUtil(MOCK_JWT_SECRET, 604_800_000);
  private User mockUser = new User(MOCK_UUID, MOCK_USERNAME, MOCK_ROLE, MOCK_EMAIL, MOCK_PASSWORD);

  @Test
  void validateTokenError() {
    var token = "A".repeat(128);
    assertThrows(InvalidJwtException.class, () -> jwtUtil.validateToken(token));
  }

  @Test
  void testGenerateToken() {
    var jwt = jwtUtil.generateToken(mockUser);
    var result = jwtUtil.validateToken(jwt);

    assertEquals(MOCK_UUID.toString(), result.get("userId", String.class));
    assertEquals(MOCK_EMAIL, result.get("sub", String.class));
    assertEquals(MOCK_ROLE.getName(), result.get("role", String.class));
  }
}
