package com.arjen0203.codex.service.user.service;

import com.arjen0203.codex.domain.core.general.exceptions.NotFoundException;
import com.arjen0203.codex.domain.auth.dto.UpdateUserPassword;
import com.arjen0203.codex.domain.user.dto.CreateUser;
import com.arjen0203.codex.domain.user.dto.UserDto;
import com.arjen0203.codex.domain.user.entity.User;
import com.arjen0203.codex.service.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/** UserService that handles all User related actions. */
@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  /**
   * Gets a User by the provided UUID.
   *
   * @param id the UUID for the requested User
   * @return User
   */
  public User getById(UUID id) {
    var oUser = userRepository.findById(id);
    if (oUser.isEmpty()) {
      throw new NotFoundException("User");
    }

    return oUser.get();
  }

  /**
   * Gets a User by the provided UUID and converts it to a DTO.
   *
   * @param id the UUID for the requested User
   * @return UserDTO
   */
  public UserDto getDtoById(UUID id) {
    var user = this.getById(id);
    return modelMapper.map(user, UserDto.class);
  }

  /**
   * Gets a User by the provided email.
   *
   * @param email the email for the User
   * @return Optional of User empty if no user can be found
   */
  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  /**
   * Creates a User based on the provided RegisterDTO info.
   *
   * @param create the DTO containing the Register info
   * @return UserDto of the newly created User
   */
  public UserDto createUser(CreateUser create) {
    var user = modelMapper.map(create, User.class);

    var res = userRepository.save(user);
    return modelMapper.map(res, UserDto.class);
  }

  /**
   * Updates the password for the user with the provided email.
   *
   * @param update DTO containing the new hashed password and email for the user we wish to update
   */
  public void updatePassword(UpdateUserPassword update) {
    var oUser = userRepository.findByEmail(update.getEmail());
    if (oUser.isEmpty()) {
      throw new NotFoundException("User");
    }

    var user = oUser.get();
    user.setPassword(update.getPassword());
    userRepository.save(user);
  }
}
