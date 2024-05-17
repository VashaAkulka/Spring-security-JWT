package ru.sermyazhko.Springsecurity.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sermyazhko.Springsecurity.dto.UserDTO;
import ru.sermyazhko.Springsecurity.entity.User;
import ru.sermyazhko.Springsecurity.service.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping("/user/hello")
    public ResponseEntity<String> sayHello(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok("Hello, " + user.getUsername());
    }

    @GetMapping("admin/user/all")
    public ResponseEntity<List<UserDTO>> getListUser() {
        return ResponseEntity.ok(userService.getListUser());
    }
}
