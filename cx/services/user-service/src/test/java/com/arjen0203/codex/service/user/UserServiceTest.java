package com.arjen0203.codex.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import com.arjen0203.codex.domain.core.general.exceptions.NotFoundException;
import com.arjen0203.codex.service.user.repositories.UserRepository;
import com.arjen0203.codex.service.user.service.UserService;
import com.arjen0203.codex.domain.auth.dto.UpdateUserPassword;
import com.arjen0203.codex.domain.user.dto.CreateUser;
import com.arjen0203.codex.domain.user.dto.RoleDto;
import com.arjen0203.codex.domain.user.dto.UserDto;
import com.arjen0203.codex.domain.user.entity.Role;
import com.arjen0203.codex.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class UserServiceTest {
  private static final UUID MOCK_UUID = UUID.randomUUID();
  private static final String MOCK_EMAIL = "rens@gmail.com";
  private static final Role MOCK_ROLE = new Role(1, "Admin");

  private final User mockUser =
      new User(UUID.randomUUID(), "username", MOCK_ROLE, MOCK_EMAIL, "password");
  private final UserRepository mockUserRepository = mock(UserRepository.class);
  private UserDto mockUserDto;
  private UserService userService;

  @BeforeEach
  void setUp() {
    var modelMapper = new ModelMapper();
    mockUserDto = modelMapper.map(mockUser, UserDto.class);
    userService = new UserService(mockUserRepository, modelMapper);
  }

  @Test
  void testGetDtoByIdNotFound() {
    when(mockUserRepository.findById(MOCK_UUID)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> userService.getDtoById(MOCK_UUID));
  }

  @Test
  void testGetDtoById() {
    when(mockUserRepository.findById(MOCK_UUID)).thenReturn(Optional.of(mockUser));
    var result = userService.getDtoById(MOCK_UUID);
    assertEquals(mockUserDto, result);
  }

  @Test
  void testGetUserByEmailNotFound() {
    when(mockUserRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.empty());
    var result = userService.getUserByEmail(MOCK_EMAIL);
    assertEquals(Optional.empty(), result);
  }

  @Test
  void testGetUser() {
    when(mockUserRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(mockUser));
    var result = userService.getUserByEmail(MOCK_EMAIL);
    assertEquals(mockUser, result.get());
  }

  @Test
  void createUser() {
    when(mockUserRepository.save(any())).thenReturn(mockUser);

    var result =
        userService.createUser(
            new CreateUser("username", MOCK_EMAIL, "password", new RoleDto(1, "Admin")));
    assertEquals(mockUserDto, result);
    verify(mockUserRepository, times(1)).save(any());
  }
}
