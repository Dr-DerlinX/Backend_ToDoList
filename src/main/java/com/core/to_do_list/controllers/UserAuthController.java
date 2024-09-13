package com.core.to_do_list.controllers;

import com.core.to_do_list.config.JwtService;
import com.core.to_do_list.model.UserLogin;
import com.core.to_do_list.persistence.Entities.Usuario;
import com.core.to_do_list.persistence.Repositories.UserInfoRepositorie;
import com.core.to_do_list.services.UserInfoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Autowired
    private UserInfoServices services;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserInfoRepositorie repositorie;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin credentials){
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        Optional<Usuario> user = repositorie.findByname(username);

        if (user.isPresent()){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = jwtService.generateToken(credentials);

            Map<String, String> responce = new HashMap<>();
            responce.put("Token : ", token);
            return ResponseEntity.ok(responce);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Usuario user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(services.agregarNuevoUsuario(user));

    }

}
