package com.core.to_do_list.controllers;

import com.core.to_do_list.config.JwtService;
import com.core.to_do_list.exceptions.RecouserNotFoundException;
import com.core.to_do_list.model.UserLogin;
import com.core.to_do_list.persistence.Entities.Usuario;
import com.core.to_do_list.persistence.Repositories.UserInfoRepositorie;
import com.core.to_do_list.services.UserInfoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    // Cambiar los @Autowired para utilizar inicialisacion mediante constructor

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoServices services;


    @PostMapping("/login")
     public ResponseEntity<?> login(@RequestBody UserLogin credentials) throws RecouserNotFoundException {
        Map<String, String> response = services.loginByUserName(credentials);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Usuario user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(services.agregarNuevoUsuario(user));

    }
}
