package ru.sermyazhko.Springsecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sermyazhko.Springsecurity.dto.UserDTO;
import ru.sermyazhko.Springsecurity.entity.Role;
import ru.sermyazhko.Springsecurity.entity.User;
import ru.sermyazhko.Springsecurity.exception.NoSuchUserException;
import ru.sermyazhko.Springsecurity.exception.UserAlreadyExistsException;
import ru.sermyazhko.Springsecurity.mapper.UserMapper;
import ru.sermyazhko.Springsecurity.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public String saveUser(UserDTO userDTO) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with username " + userDTO.getUsername() + " already exists");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setAuthorities(Set.of(Role.ROLE_USER));

        user = userRepository.save(user);
        return jwtService.generateToken(user);
    }

    public String loginUser(UserDTO userDTO) throws NoSuchUserException {
        User user = userRepository.findByUsername(userDTO.getUsername()).orElseThrow(() -> new NoSuchUserException(userDTO.getUsername() + " not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getUsername(),
                        userDTO.getPassword()
                )
        );

        return jwtService.generateToken(user);
    }

    public List<UserDTO> getListUser() {
        return UserMapper.INSTANCE.userListToUserDTOList(userRepository.findAll());
    }
}
