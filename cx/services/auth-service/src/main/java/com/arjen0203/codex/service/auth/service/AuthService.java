package com.arjen0203.codex.service.auth.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import com.arjen0203.codex.core.rabbit.utils.Messaging;
import com.arjen0203.codex.domain.core.general.exceptions.ConflictException;
import com.arjen0203.codex.domain.user.dto.UserDto;
import com.arjen0203.codex.service.auth.utils.JwtUtil;
import com.arjen0203.codex.domain.auth.dto.Login;
import com.arjen0203.codex.domain.auth.dto.Register;
import com.arjen0203.codex.domain.auth.exceptions.InvalidEmailOrPasswordException;
import com.arjen0203.codex.domain.user.dto.CreateUser;
import com.arjen0203.codex.domain.user.dto.RoleDto;
import com.arjen0203.codex.domain.user.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/** Service that provides all the Authorization. */
@Service
public class AuthService {
  private final ModelMapper modelMapper;
  private final JwtUtil jwtUtil;
  private final int expiration;
  private final Messaging messaging;

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
      JwtUtil jwtUtil,
      @Qualifier("expiration") int expiration,
      Messaging messaging) {
    this.modelMapper = modelMapper;
    this.jwtUtil = jwtUtil;
    this.expiration = expiration / 1000;
    this.messaging = messaging;
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
      var user = new User(); //yeet this


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
    var createUser = modelMapper.map(register, CreateUser.class);
    createUser.setPassword(BCrypt.hashpw(register.getPassword(), BCrypt.gensalt()));
    createUser.setRole(new RoleDto(0)); //For now standard role for every registration

    var response = messaging.sendAndReceive("register", createUser);
    if (response.isSuccess()) return;
    throw response.getException();
  }
}
