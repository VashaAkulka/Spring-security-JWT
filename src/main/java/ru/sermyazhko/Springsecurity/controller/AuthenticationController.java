package ru.sermyazhko.Springsecurity.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sermyazhko.Springsecurity.dto.UserDTO;
import ru.sermyazhko.Springsecurity.exception.NoSuchUserException;
import ru.sermyazhko.Springsecurity.exception.UserAlreadyExistsException;
import ru.sermyazhko.Springsecurity.service.UserService;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<String> registrationUser(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.saveUser(userDTO));
        } catch (UserAlreadyExistsException exception) {
            return ResponseEntity.badRequest()
                    .body(exception.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.loginUser(userDTO));
        } catch (NoSuchUserException exception) {
            return ResponseEntity.badRequest()
                    .body(exception.getMessage());
        }
    }
}
