package Pradanoia.services;

import Pradanoia.payloads.entities.UserDto;

import java.util.List;

public interface UserService {

    UserDto registerNewUser(UserDto user);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer id);

    UserDto getUserById(Integer id);

    List<UserDto> getAllUsers();

    void deleteUser(Integer id);
}
