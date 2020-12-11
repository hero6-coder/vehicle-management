package blueship.vehicle.service.impl;

import blueship.vehicle.common.Constants;
import blueship.vehicle.dto.UserDto;
import blueship.vehicle.dto.VehicleDto;
import blueship.vehicle.entity.User;
import blueship.vehicle.exception.VmException;
import blueship.vehicle.repository.UserRepository;
import blueship.vehicle.service.VehicleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
  @InjectMocks
  private UserServiceImpl userService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private VehicleService vehicleService;
  @Mock
  private PasswordEncoder passwordEncoder;

  @Test
  void getUserById_NotFoundUser() {
    when(userRepository.findById(5)).thenReturn(Optional.empty());
    Assertions.assertThrows(VmException.class,() -> userService.getUserById(5));
    verify(userRepository, times(1)).findById(any(Integer.class));
  }

  @Test
  void getVehicleById_Success() {
    User user = new User();
    user.setId(5);
    when(userRepository.findById(5)).thenReturn(Optional.of(user));
    user = userService.getUserById(5);
    verify(userRepository, times(1)).findById(5);
    Assertions.assertEquals(5, user.getId());
  }

  @Test
  void getActiveUser_EmptyList() {
    when(userRepository.findAllByStatus(Constants.ACTIVE)).thenReturn(Collections.emptyList());
    List<UserDto> userDtoList = userService.getActiveUser();
    verify(userRepository, times(1)).findAllByStatus(Constants.ACTIVE);
    Assertions.assertEquals(0, userDtoList.size());
  }

  @Test
  void getActiveUser_ReturnList() {
    User user = new User();
    List<User> users = new ArrayList<>();
    User user1 = new User();
    User user2 = new User();
    users.add(user1);
    users.add(user2);

    when(userRepository.findAllByStatus(Constants.ACTIVE)).thenReturn(users);
    List<UserDto> userDtos = userService.getActiveUser();
    verify(userRepository, times(1)).findAllByStatus(Constants.ACTIVE);
    Assertions.assertEquals(2, userDtos.size());
  }

  @Test
  void getVehiclesByUser_NotFoundUser() {
    when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
    Assertions.assertThrows(VmException.class,() -> userService.getVehiclesByUser(1));
    verify(userRepository, times(1)).findById(any(Integer.class));
  }

  @Test
  void getVehiclesByUser_Success() {
    User user = new User();
    user.setId(5);
    when(userRepository.findById(5)).thenReturn(Optional.of(user));
    when(vehicleService.getVehiclesByUser(any(User.class))).thenReturn(Collections.emptyList());
    List<VehicleDto> vehicleDtos = userService.getVehiclesByUser(5);
    verify(userRepository, times(1)).findById(any(Integer.class));
    verify(vehicleService, times(1)).getVehiclesByUser(any(User.class));
    Assertions.assertEquals(0, vehicleDtos.size());
  }

  @Test
  void getVehiclesByUser_ReturnList() {
    User user = new User();
    user.setId(5);
    List<VehicleDto> vehicles = new ArrayList<>();
    VehicleDto vehicle1 = new VehicleDto();
    VehicleDto vehicle2 = new VehicleDto();
    vehicles.add(vehicle1);
    vehicles.add(vehicle2);

    when(userRepository.findById(5)).thenReturn(Optional.of(user));
    when(vehicleService.getVehiclesByUser(any(User.class))).thenReturn(vehicles);
    List<VehicleDto> vehicleDtos = userService.getVehiclesByUser(5);
    verify(userRepository, times(1)).findById(any(Integer.class));
    verify(vehicleService, times(1)).getVehiclesByUser(any(User.class));
    Assertions.assertEquals(2, vehicleDtos.size());
  }

  @Test
  void saveUser_Success() {
    User user = new User();
    user.setId(5);
    UserDto userDto = new UserDto();
    userDto.setId(5);
    userDto.setPassword("test");
    when(passwordEncoder.encode(any(String.class))).thenReturn("abc");
    when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
    userDto = userService.saveUser(userDto);
    verify(userRepository, times(1)).save(any(User.class));
    Assertions.assertEquals(5, userDto.getId());
    Assertions.assertEquals("abc", userDto.getPassword());
  }

  @Test
  void saveUser_InvalidConstraint() {
    User user = new User();
    user.setId(5);
    UserDto userDto = new UserDto();
    userDto.setId(5);
    userDto.setPassword("test");
    when(passwordEncoder.encode(any(String.class))).thenReturn("abc");
    when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);
    Assertions.assertThrows(VmException.class,() -> userService.saveUser(userDto));
    verify(userRepository, times(1)).save(any(User.class));
  }
}