package com.core.to_do_list.controllers;

import com.core.to_do_list.persistence.Entities.Usuario;
import com.core.to_do_list.services.UserInfoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserInfoServices services;

    @GetMapping("/userProfile")
    public ResponseEntity<Usuario> userProfile(@RequestHeader HttpHeaders headers){
        return ResponseEntity.ok(services.autorizarUsuario(headers));
    }

    @PutMapping("/userProfile")
    public ResponseEntity<?> edictarUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(services.editarUsuario(usuario));
    }

    @DeleteMapping("/userProfile/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        Optional<Usuario> usuario = services.obtenerUsuario(id);

        if (usuario.isPresent()){
            services.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        }else  {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario con ID " + id + " no encontrado");
        }
    }
}
