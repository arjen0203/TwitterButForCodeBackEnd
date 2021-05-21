package com.arjen0203.codex.service.auth.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

import com.arjen0203.codex.domain.core.general.exceptions.ConflictException;
import com.arjen0203.codex.domain.core.general.exceptions.UnauthorizedException;
import com.arjen0203.codex.domain.core.general.exceptions.InvalidArgumentException;
import com.arjen0203.codex.service.auth.repositories.InviteTokenRepository;
import com.arjen0203.codex.service.auth.repositories.ResetPasswordRepository;
import com.arjen0203.codex.service.auth.utils.JwtUtil;
import com.arjen0203.codex.domain.auth.dto.Invite;
import com.arjen0203.codex.domain.auth.dto.Login;
import com.arjen0203.codex.domain.auth.dto.Register;
import com.arjen0203.codex.domain.auth.dto.RequestPasswordReset;
import com.arjen0203.codex.domain.auth.dto.ResetPasswordDto;
import com.arjen0203.codex.domain.auth.dto.UpdateUserPassword;
import com.arjen0203.codex.domain.auth.entity.InviteToken;
import com.arjen0203.codex.domain.auth.entity.ResetPassword;
import com.arjen0203.codex.domain.auth.exceptions.InvalidEmailOrPasswordException;
import com.arjen0203.codex.domain.auth.exceptions.InviteTokenInvalidException;
import com.arjen0203.codex.domain.auth.exceptions.ResetPasswordTokenInvalidException;
import com.arjen0203.codex.domain.user.dto.CreateUser;
import com.arjen0203.codex.domain.user.dto.RoleDto;
import com.arjen0203.codex.domain.user.dto.UserDto;
import com.arjen0203.codex.domain.user.entity.User;
import com.arjen0203.codex.domain.user.exceptions.EmailAlreadyInUseException;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/** Service that provides all the Authorization. */
@Service
public class AuthService {
  private final InviteTokenRepository inviteTokenRepository;
  private final ResetPasswordRepository resetPasswordRepository;

  private final ModelMapper modelMapper;
  private final JwtUtil jwtUtil;
  private final int expiration;

  @Value("${auth.cookie.httpOnly}")
  private boolean cookieHttpOnly = false;

  @Value("${auth.cookie.path}")
  private String cookiePath = "";

  @Value("${auth.cookie.domain}")
  private String cookieDomain = "";

  @Value("${auth.cookie.secure}")
  private boolean cookieSecure = false;

  /** Constructs the Auth-Service. */
  public AuthService(
      ModelMapper modelMapper,
      InviteTokenRepository inviteTokenRepository,
      ResetPasswordRepository resetPasswordRepository,
      JwtUtil jwtUtil,
      @Qualifier("expiration") int expiration) {
    this.modelMapper = modelMapper;
    this.inviteTokenRepository = inviteTokenRepository;
    this.resetPasswordRepository = resetPasswordRepository;
    this.jwtUtil = jwtUtil;
    this.expiration = expiration / 1000;
  }

  /** Scheduled task to remove all Invite requests that are more than 2 hours old. */
  @Scheduled(fixedRate = 1000 * 60 * 5)
  @Transactional
  public void clearInvites() {
    var time = Instant.now().minus(2, ChronoUnit.HOURS);
    inviteTokenRepository.deleteByTimeStampBefore(time);
  }

  /** Scheduled task to remove all Reset Password requests that are more than 20 minutes old. */
  @Scheduled(fixedRate = 1000 * 60 * 5)
  @Transactional
  public void clearResetPassword() {
    var time = Instant.now().minus(20, ChronoUnit.MINUTES);
    resetPasswordRepository.deleteByTimeStampBefore(time);
  }

  /**
   * Authorizes a provided JWT.
   *
   * @param jwt the JWT provided by the User
   * @return HashMap containing headers with the User's claims
   */
  public Map<String, String> auth(String jwt) {
    var claims = jwtUtil.validateToken(jwt);
    var map = new HashMap<String, String>();
    map.put("userId", claims.get("userId", String.class));
    map.put("email", claims.get("sub", String.class));
    map.put("role", claims.get("role", String.class));
    return map;
  }

  /**
   * Construct an Authorization cookie with the provided jwt and expiration.
   *
   * @param jwt the token we want to put in the cookie
   * @param expiration the expiration time
   * @return a cookie
   */
  private Cookie constructAuthorizationCookie(String jwt, int expiration) {
    var cookie = new Cookie("Authorization", jwt);
    cookie.setHttpOnly(cookieHttpOnly);
    cookie.setDomain(cookieDomain);
    cookie.setPath(cookiePath);
    cookie.setSecure(cookieSecure);
    cookie.setMaxAge(expiration);
    return cookie;
  }

  /**
   * Logs a User in.
   *
   * @param login DTO containing the User's login info
   * @return a cookie
   */
  public Cookie login(Login login) {
    try {
      //var user = messaging.sendAndReceive("user-get-by-email", login.getEmail(), User.class);
      //todo change login here to use rabbitMQ
      var user = new User();


      if (!BCrypt.checkpw(login.getPassword(), user.getPassword())) {
        throw new InvalidEmailOrPasswordException();
      }

      var jwt = jwtUtil.generateToken(user);
      return constructAuthorizationCookie(jwt, login.isRememberMe() ? expiration : -1);
    } catch (ResponseStatusException ex) {
      if (ex.getRawStatusCode() == 404) {
        // User could not be found so we change the error to invalid email or password
        throw new InvalidEmailOrPasswordException();
      }
      throw ex;
    }
  }

  /**
   * Logs a use out by giving them a authorization cookie with an expiration time of 0.
   *
   * @return a cookie
   */
  public Cookie logout() {
    return constructAuthorizationCookie("", 0);
  }

  /**
   * Register a User.
   *
   * @param register DTO containing the User's register info
   */
  public void register(Register register) {
    var oInviteToken = inviteTokenRepository.findById(register.getInviteToken());
    if (oInviteToken.isEmpty()) {
      throw new InviteTokenInvalidException();
    }

    var inviteToken = oInviteToken.get();
    if (!inviteToken.getEmail().equals(register.getEmail())) {
      throw new UnauthorizedException(
          "The provided email doesn't match the email used for your invite");
    }

    // Make a CreateUserDTO with the hashed password and the role from the InviteToken
    var createUser = modelMapper.map(register, CreateUser.class);

    createUser.setPassword(BCrypt.hashpw(register.getPassword(), BCrypt.gensalt()));
    createUser.setRole(new RoleDto(inviteToken.getRoleId()));

    //messaging.sendAndReceive("user-create", createUser, UserDto.class);
    //todo change this to use rabbitmq and no invite token necesarry


    inviteTokenRepository.deleteById(inviteToken.getToken());
  }

  /**
   * Creates an invite for a User by email.
   *
   * @param jwt The JWT for the User that is trying to invite someone
   * @param invite DTO containing the info required to create an Invite
   */
  public void inviteUser(String jwt, Invite invite) {
    if (invite.getRoleId() == 0) {
      throw new InvalidArgumentException("Invites must contain a valid role");
    }

    var inviterRole = jwtUtil.validateToken(jwt).get("role", String.class);
    if (!inviterRole.equals("Admin")) {
      throw new UnauthorizedException("Only Admins can invite other users");
    }

    boolean exists = false;
    //    messaging.sendAndReceive("user-exists-by-email", invite.getEmail(), boolean.class);
    //todo change to rabbitmq


    if (exists) {
      throw new EmailAlreadyInUseException();
    }

    var inviteToken = modelMapper.map(invite, InviteToken.class);
    inviteToken.setTimeStamp(Instant.now());

    try {
      inviteTokenRepository.save(inviteToken);
      //messaging.send("email-send-invite", inviteToken);
      //todo change this to rabbitmq and no more invite token
    } catch (DataIntegrityViolationException ex) {
      throw new ConflictException("Invite already exists");
    }
  }

  /**
   * Request a password reset for the requester.
   *
   * @param reset a DTO containing the information needed to do a Password Reset
   */
  public void requestPasswordReset(RequestPasswordReset reset) {
    boolean exists = false;
        //messaging.sendAndReceive("user-exists-by-email", reset.getEmail(), boolean.class);
        //todo change to rabbitmq

    if (!exists) {
      // Dont leak the fact that the email address doesn't exist
      return;
    }

    try {
      var resetPassword = modelMapper.map(reset, ResetPassword.class);
      resetPassword.setTimeStamp(Instant.now());

      resetPasswordRepository.save(resetPassword);
      //messaging.send("email-send-password-reset", resetPassword);
      //todo change to rabbitmq
    } catch (DataIntegrityViolationException err) {
      throw new ConflictException("Reset password request has already been made");
    }
  }

  /**
   * Actually reset the password for the requester.
   *
   * @param reset a DTO containing the new password and a Reset Password token
   */
  public void resetPassword(ResetPasswordDto reset) {
    var oResetPasswordToken = resetPasswordRepository.findById(reset.getToken());
    if (oResetPasswordToken.isEmpty()) {
      throw new ResetPasswordTokenInvalidException();
    }

    var resetPasswordToken = oResetPasswordToken.get();
    var hash = BCrypt.hashpw(reset.getPassword(), BCrypt.gensalt());
    var updatePassword = new UpdateUserPassword(resetPasswordToken.getEmail(), hash);

    boolean success = false;
        //messaging.sendAndReceive("user-update-password", updatePassword, boolean.class);
        //change to rabbitMQ
    if (success) {
      resetPasswordRepository.deleteById(resetPasswordToken.getToken());
    }
  }
}
