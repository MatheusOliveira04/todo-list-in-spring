package me.matheus.todo_list.controllers;

import me.matheus.todo_list.config.jwt.JwtUtil;
import me.matheus.todo_list.config.jwt.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class JwtController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager auth;

    @PostMapping("/token")
    public String authenticateAndGetToken(@RequestBody LoginDTO loginDto) {
        Authentication authentication = auth.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(loginDto.email());
        } else {
            throw new UsernameNotFoundException("Invalid user");
        }

    }
}
