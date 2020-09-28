package com.codespacelab.user.controller;

import com.codespacelab.user.model.UserDto;
import com.codespacelab.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping
    public UserDto getUser(
            @RequestParam(name = "id") Long id
    ) {
        return userService.getUser(id);
    }

    @PostMapping
    public UserDto addUser(
            @Validated @RequestBody UserDto userDto
    ) {
        return userService.addUser(userDto);
    }

    @PutMapping
    public UserDto updateUser(
            @Validated @RequestBody UserDto userDto
    ) {
        return userService.updateUser(userDto);
    }

    @DeleteMapping
    public boolean deleteUser(
            @RequestParam(name = "id") Long id
    ) {
        return userService.deleteUser(id);
    }

    @GetMapping("/validate")
    public boolean validateUser(
           @Valid @Min(1) @RequestParam(name = "id") Long id
    ) {
        return userService.validateUser(id);
    }
}
