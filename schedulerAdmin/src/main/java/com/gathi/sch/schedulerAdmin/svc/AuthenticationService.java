package com.gathi.sch.schedulerAdmin.svc;

import com.gathi.sch.schedulerAdmin.domain.User;
import com.gathi.sch.schedulerAdmin.dto.UserDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthenticationService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public UserDetails authenticate(UserDTO userDTO) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDTO.getUsername(),
                userDTO.getPassword()
        ));

        return userService.loadUserByUsername(userDTO.getUsername());
    }

    public UserDetails signup(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getUsername());
        user.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        user.setName(userDTO.getFullName());
        return userService.save(user);
    }
}
