package com.gathi.sch.schedulerAdmin.api;


import com.gathi.sch.schedulerAdmin.dto.LoginResponse;
import com.gathi.sch.schedulerAdmin.dto.UserDTO;
import com.gathi.sch.schedulerAdmin.svc.AuthenticationService;
import com.gathi.sch.schedulerAdmin.svc.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }


    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDTO userDTO) {

        UserDetails user = this.authenticationService.authenticate(userDTO);
        String token = this.jwtService.generateToken(new HashMap<>(), user);
        String refreshToken = this.jwtService.generateRefreshToken(new HashMap<>(), user);

        return ResponseEntity.ok(new LoginResponse(user.getUsername(), token, refreshToken));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody UserDTO userDTO) {
        UserDetails user = this.authenticationService.signup(userDTO);
        String token = this.jwtService.generateToken(new HashMap<>(), user);
        String refreshToken = this.jwtService.generateRefreshToken(new HashMap<>(), user);

        return ResponseEntity.ok(new LoginResponse(user.getUsername(), token, refreshToken));
    }
}
