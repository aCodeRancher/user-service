package com.codespacelab.user.service;

import com.codespacelab.user.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private List<UserDto> users;

    public UserService() {
        UserDto user1 = new UserDto(123L, "Peter", true);
        UserDto user2 = new UserDto(456L, "Louise", true);

        users = new ArrayList<>(Arrays.asList(user1, user2));
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public UserDto getUser(Long id) {
        Optional<UserDto> userOptional = users.stream().filter(user -> user.getId().equals(id)).findFirst();
        return userOptional.isPresent() ? userOptional.get() : null;
    }

    public UserDto addUser(UserDto userDto) {
        UserDto newUser = new UserDto(users.size() + 100L, userDto.getName(), true);
        users.add(newUser);
        return newUser;
    }

    public UserDto updateUser(UserDto userDto) {
        Optional<UserDto> userOptional = users.stream()
                .filter(user -> user.getId().equals(userDto.getId()))
                .map(user -> {
                    user.setName(userDto.getName());
                    user.setActive(userDto.isActive());
                    return user;
                }).findFirst();

        return userOptional.isPresent() ? userOptional.get() : null;
    }

    public boolean deleteUser(Long id) {
        final int originalSize = users.size();
        users = users.stream().filter(user -> !user.getId().equals(id)).collect(Collectors.toList());

        return originalSize > users.size();
    }

    public boolean validateUser(Long id) {
        Optional<UserDto> userOptional = users.stream().filter(user -> user.getId().equals(id)).findFirst();
        return userOptional.isPresent() ? userOptional.get().isActive() : false;
    }
}
