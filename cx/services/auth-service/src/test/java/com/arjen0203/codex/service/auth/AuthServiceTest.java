package com.arjen0203.codex.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import com.arjen0203.codex.core.rabbit.utils.Messaging;
import com.arjen0203.codex.service.auth.utils.JwtUtil;
import com.arjen0203.codex.domain.auth.dto.Login;
import com.arjen0203.codex.domain.auth.dto.Register;
import com.arjen0203.codex.domain.auth.exceptions.InvalidJwtException;
import com.arjen0203.codex.domain.user.dto.RoleDto;
import com.arjen0203.codex.domain.user.entity.Role;
import com.arjen0203.codex.domain.user.entity.User;
import com.arjen0203.codex.service.auth.service.AuthService;
import io.jsonwebtoken.Claims;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.modelmapper.ModelMapper;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class AuthServiceTest {
  private static final String MOCK_EMAIL = "test@gmail.com";
  private static final String MOCK_USERNAME = "username";
  private static final String MOCK_PASSWORD = "password";
  private static final String MOCK_JWT = "JWT";
  private static final String BETWEEN = " should be between ";
  private static final String MISSING = " should not be empty";
  private static final String INVALID_EMAIL = "Email should be a valid email address";
  private static final String USERNAME_SIZE = "4 and 32 characters";
  private static final String PASSWORD_SIZE = "8 and 64 characters";

  private final Role mockRole = new Role(1, "Admin");
  private final RoleDto mockRoleDto = new RoleDto(1, "Admin");

  private final Login mockLogin = new Login(MOCK_EMAIL, MOCK_PASSWORD, false);
  private final Register mockRegister =
      new Register(MOCK_USERNAME, MOCK_EMAIL, MOCK_PASSWORD);
  private final User mockUser =
      new User(UUID.randomUUID(), MOCK_USERNAME, mockRole, MOCK_EMAIL, MOCK_PASSWORD);

  private final JwtUtil mockJwtUtil = mock(JwtUtil.class);
  private final Claims mockClaims = mock(Claims.class);

  private LocalValidatorFactoryBean localValidatorFactory = new LocalValidatorFactoryBean();
  private AuthService authService;
  private Messaging messaging;

  private static Stream<Arguments> badUsernames() {
    return Stream.of(
        Arguments.of(null, "Username" + MISSING),
        Arguments.of("", "Usernames" + BETWEEN + USERNAME_SIZE),
        Arguments.of("123", "Usernames" + BETWEEN + USERNAME_SIZE),
        Arguments.of(" ".repeat(4), "Username" + MISSING),
        Arguments.of("a".repeat(33), "Usernames" + BETWEEN + USERNAME_SIZE));
  }

  private static Stream<Arguments> badPasswords() {
    return Stream.of(
        Arguments.of(null, "Password" + MISSING),
        Arguments.of("", "Password" + MISSING),
        Arguments.of("1234567", "Passwords" + BETWEEN + PASSWORD_SIZE),
        Arguments.of(" ".repeat(8), "Password" + MISSING),
        Arguments.of("a".repeat(65), "Passwords" + BETWEEN + PASSWORD_SIZE));
  }

  private static Stream<Arguments> badEmails() {
    return Stream.of(
        Arguments.of(null, "Email" + MISSING),
        Arguments.of("", "Email" + MISSING),
        Arguments.of("acb@", INVALID_EMAIL),
        Arguments.of("@gmail.com", INVALID_EMAIL),
        Arguments.of("rensgmail.com", INVALID_EMAIL));
  }

  @BeforeEach
  void setUp() {
    authService =
        new AuthService(
            new ModelMapper(),
            mockJwtUtil,
            0,
            messaging);

    localValidatorFactory.setProviderClass(HibernateValidator.class);
    localValidatorFactory.afterPropertiesSet();
  }

  @Test
  void testAuthWithInvalidJwt() {
    when(mockJwtUtil.validateToken(anyString())).thenThrow(new InvalidJwtException());
    assertThrows(InvalidJwtException.class, () -> authService.auth("Invalid JWT"));
  }

  @Test
  void testAuth() {
    when(mockJwtUtil.validateToken(anyString())).thenReturn(mockClaims);
    when(mockClaims.get("sub", String.class)).thenReturn(MOCK_EMAIL);

    var result = authService.auth(MOCK_JWT);
    assertEquals(MOCK_EMAIL, result.get("email"));
  }

  @ParameterizedTest
  @MethodSource("badEmails")
  void testLoginWithBadEmail(String email, String errorMsg) {
    var login = new Login(email, MOCK_PASSWORD, false);
    var constraintViolations = localValidatorFactory.validate(login);

    assertFalse(constraintViolations.isEmpty());

    var violations = Arrays.asList(constraintViolations.toArray(ConstraintViolationImpl[]::new));
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(errorMsg)));
  }

  @ParameterizedTest
  @MethodSource("badPasswords")
  void testLoginWithBadPasswords(String password, String errorMsg) {
    var login = new Login(MOCK_EMAIL, password, false);
    var constraintViolations = localValidatorFactory.validate(login);

    assertFalse(constraintViolations.isEmpty());

    var violations = Arrays.asList(constraintViolations.toArray(ConstraintViolationImpl[]::new));
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(errorMsg)));
  }

//  @Test
//  void testLoginFailedToGetUser() {
//    when(mockMessaging.sendAndReceive(eq("user-get-by-email"), eq(MOCK_EMAIL), any()))
//        .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User could not be found"));
//    assertThrows(InvalidEmailOrPasswordException.class, () -> authService.login(mockLogin));
//  }

//  @Test
//  void testLoginIncorrectPassword() {
//    final var incorrectPassword = "incorrectPassword";
//
//    when(mockMessaging.sendAndReceive(eq("user-get-by-email"), eq(MOCK_EMAIL), any()))
//        .thenReturn(mockUser);
//
//    try (var bCrypt = mockStatic(BCrypt.class)) {
//      bCrypt.when(() -> BCrypt.checkpw(incorrectPassword, MOCK_PASSWORD)).thenReturn(false);
//      final var login = new Login(MOCK_EMAIL, incorrectPassword, false);
//
//      assertThrows(InvalidEmailOrPasswordException.class, () -> authService.login(login));
//    }
//  }

//  @Test
//  void testLoginSuccess() {
//    when(mockMessaging.sendAndReceive(eq("user-get-by-email"), eq(MOCK_EMAIL), any()))
//        .thenReturn(mockUser);
//    when(mockJwtUtil.generateToken(mockUser)).thenReturn(MOCK_JWT);
//
//    try (var bCrypt = mockStatic(BCrypt.class)) {
//      bCrypt.when(() -> BCrypt.checkpw(MOCK_PASSWORD, MOCK_PASSWORD)).thenReturn(true);
//      assertEquals("Authorization", authService.login(mockLogin).getName());
//    }
//  }

//  private void testRegister(Register register, String errorMsg) {
//    try {
//      authService.register(register);
//    } catch (UnprocessableEntityException ex) {
//      assertEquals(errorMsg, ex.getReason());
//    }
//  }

  @ParameterizedTest
  @MethodSource("badUsernames")
  void testRegisterWithBadUsername(String username, String errorMsg) {
    var register = new Register(username, MOCK_EMAIL, MOCK_PASSWORD);
    var constraintViolations = localValidatorFactory.validate(register);

    assertFalse(constraintViolations.isEmpty());

    var violations = Arrays.asList(constraintViolations.toArray(ConstraintViolationImpl[]::new));
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(errorMsg)));
  }

  @ParameterizedTest
  @MethodSource("badEmails")
  void testRegisterWithBadEmail(String email, String errorMsg) {
    var register = new Register(MOCK_USERNAME, email, MOCK_PASSWORD);
    var constraintViolations = localValidatorFactory.validate(register);

    assertFalse(constraintViolations.isEmpty());

    var violations = Arrays.asList(constraintViolations.toArray(ConstraintViolationImpl[]::new));
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(errorMsg)));
  }

  @ParameterizedTest
  @MethodSource("badPasswords")
  void testRegisterWithBadPasswords(String password, String errorMsg) {
    var register = new Register(MOCK_USERNAME, MOCK_EMAIL, password);
    var constraintViolations = localValidatorFactory.validate(register);

    assertFalse(constraintViolations.isEmpty());

    var violations = Arrays.asList(constraintViolations.toArray(ConstraintViolationImpl[]::new));
    assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(errorMsg)));
  }

//  @Test
//  void testRegisterWithInviteTokenWithIncorrectEmail() {
//    var inviteToken = new InviteToken(UUID.randomUUID(), "other-email@gmail.com", 1, Instant.now());
//
//    when(mockInviteTokenRepository.findById(any())).thenReturn(Optional.of(inviteToken));
//    assertThrows(UnauthorizedException.class, () -> authService.register(mockRegister));
//  }

//  @Test
//  void testRegisterWhenUserCreateFails() {
//    var inviteToken = new InviteToken(UUID.randomUUID(), MOCK_EMAIL, 1, Instant.now());
//
//    when(mockInviteTokenRepository.findById(any())).thenReturn(Optional.of(inviteToken));
//    when(mockMessaging.sendAndReceive(eq("user-create"), any(), any()))
//        .thenThrow(InternalServerException.class);
//    assertThrows(InternalServerException.class, () -> authService.register(mockRegister));
//  }

//  @Test
//  void testRegisterSuccess() {
//    var inviteToken = new InviteToken(UUID.randomUUID(), MOCK_EMAIL, 1, Instant.now());
//
//    when(mockInviteTokenRepository.findById(any())).thenReturn(Optional.of(inviteToken));
//    when(mockMessaging.sendAndReceive(eq("user-create"), any(), any()))
//        .thenReturn(new UserDto(UUID.randomUUID(), MOCK_USERNAME, mockRoleDto, MOCK_EMAIL));
//
//    authService.register(mockRegister);
//    verify(mockMessaging, times(1)).sendAndReceive(eq("user-create"), any(), any());
//    verify(mockInviteTokenRepository, times(1)).deleteById(inviteToken.getToken());
//  }

//  @Test
//  void testInviteWithNonAdminRole() {
//    var invite = new Invite(MOCK_EMAIL, 2);
//
//    when(mockJwtUtil.validateToken(any())).thenReturn(mockClaims);
//    when(mockClaims.get("role", String.class)).thenReturn("User");
//
//    assertThrows(UnauthorizedException.class, () -> authService.inviteUser(MOCK_JWT, invite));
//  }

//  @Test
//  void testInviteWithEmailInUse() {
//    var invite = new Invite(MOCK_EMAIL, 2);
//
//    when(mockJwtUtil.validateToken(any())).thenReturn(mockClaims);
//    when(mockClaims.get("role", String.class)).thenReturn("Admin");
//    when(mockMessaging.sendAndReceive(eq("user-exists-by-email"), any(), any())).thenReturn(true);
//
//    assertThrows(EmailAlreadyInUseException.class, () -> authService.inviteUser(MOCK_JWT, invite));
//  }

//  @Test
//  void testInviteWhenAlreadyExists() {
//    var invite = new Invite(MOCK_EMAIL, 1);
//
//    when(mockJwtUtil.validateToken(any())).thenReturn(mockClaims);
//    when(mockClaims.get("role", String.class)).thenReturn("Admin");
//    when(mockMessaging.sendAndReceive(eq("user-exists-by-email"), any(), any())).thenReturn(false);
//    when(mockInviteTokenRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
//
//    assertThrows(ConflictException.class, () -> authService.inviteUser(MOCK_JWT, invite));
//  }

//  @Test
//  void testInvite() {
//    var invite = new Invite(MOCK_EMAIL, 1);
//
//    when(mockJwtUtil.validateToken(any())).thenReturn(mockClaims);
//    when(mockClaims.get("role", String.class)).thenReturn("Admin");
//    when(mockMessaging.sendAndReceive(eq("user-exists-by-email"), any(), any())).thenReturn(false);
//
//    authService.inviteUser(MOCK_JWT, invite);
//    verify(mockInviteTokenRepository, times(1)).save(any());
//    verify(mockMessaging, times(1)).send(eq("email-send-invite"), any());
//  }
//
//  @Test
//  void testRequestPasswordResetWithInvalidEmail() {
//    var reset = new RequestPasswordReset(MOCK_EMAIL);
//    when(mockMessaging.sendAndReceive(eq("user-exists-by-email"), any(), any())).thenReturn(false);
//
//    authService.requestPasswordReset(reset);
//    verify(mockResetPasswordRepository, never()).save(any());
//  }
//
//  @Test
//  void testRequestPasswordResetWhenAlreadyExists() {
//    var reset = new RequestPasswordReset(MOCK_EMAIL);
//    when(mockMessaging.sendAndReceive(eq("user-exists-by-email"), any(), any())).thenReturn(true);
//    when(mockResetPasswordRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
//
//    assertThrows(ConflictException.class, () -> authService.requestPasswordReset(reset));
//  }

//  @Test
//  void testRequestPasswordReset() {
//    var reset = new RequestPasswordReset(MOCK_EMAIL);
//    when(mockMessaging.sendAndReceive(eq("user-exists-by-email"), any(), any())).thenReturn(true);
//
//    authService.requestPasswordReset(reset);
//
//    verify(mockResetPasswordRepository, times(1)).save(any());
//    verify(mockMessaging, times(1)).send(eq("email-send-password-reset"), any());
//  }

//  @Test
//  void testResetPasswordUpdateFail() {
//    var reset = new ResetPasswordDto(MOCK_PASSWORD, UUID.randomUUID());
//    var resetPassword = new ResetPassword(reset.getToken(), MOCK_EMAIL, Instant.now());
//
//    when(mockResetPasswordRepository.findById(reset.getToken()))
//        .thenReturn(Optional.of(resetPassword));
//    when(mockMessaging.sendAndReceive(eq("user-update-password"), any(), any())).thenReturn(false);
//
//    authService.resetPassword(reset);
//    verify(mockResetPasswordRepository, never()).deleteById(reset.getToken());
//  }

//  @Test
//  void testResetPassword() {
//    var reset = new ResetPasswordDto(MOCK_PASSWORD, UUID.randomUUID());
//    var resetPassword = new ResetPassword(reset.getToken(), MOCK_EMAIL, Instant.now());
//
//    when(mockResetPasswordRepository.findById(reset.getToken()))
//        .thenReturn(Optional.of(resetPassword));
//    when(mockMessaging.sendAndReceive(eq("user-update-password"), any(), any())).thenReturn(true);
//
//    authService.resetPassword(reset);
//    verify(mockResetPasswordRepository, times(1)).deleteById(reset.getToken());
//  }
}
