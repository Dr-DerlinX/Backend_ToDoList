package com.core.to_do_list.services;

import com.core.to_do_list.persistence.Entities.Usuario;
import com.core.to_do_list.persistence.Repositories.UserInfoRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@ComponentScan
public class UserInfoServices implements UserDetailsService {

    @Autowired
    private UserInfoRepositorie repositorie;

    public Optional<Usuario> obtenerUsuario(Long id){
        return repositorie.findById(id);
    }

    public String agregarNuevoUsuario(Usuario usuario){
        Optional<Usuario> existingUser = repositorie.findByname(usuario.getName());
        Optional<Usuario> existingEmail = repositorie.findByemail(usuario.getEmail());

        if (existingUser.isPresent())  return "Usuario ya registrado, intenta con un nuevo usuario";
        if (existingEmail.isPresent())  return "Correo ya registrado, intenta con un nuevo correo";

        repositorie.save(usuario);
        return "Usuario guardado Correctamente...!";

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> user = repositorie.findByname(username);
        Usuario usuario = user.get();

        return new User(usuario.getName(), usuario.getPassword(), new ArrayList<>());
    }

    public Usuario autorizarUsuario(HttpHeaders headers) throws UsernameNotFoundException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = ((User) authentication.getPrincipal()).getUsername();

        Optional<Usuario> optionalUser = repositorie.findByname(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return optionalUser.get();
    }

    public Usuario editarUsuario(Usuario usuario){

        Optional<Usuario> usuariID = repositorie.findById(usuario.getId());

        if (usuariID.isPresent()){
            Usuario user = usuariID.get();
            user.setName(usuario.getName());
            user.setEmail(usuario.getEmail());
            user.setPassword(usuario.getPassword());

            return repositorie.save(user);
        }else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

    }

    public void eliminarUsuario(Long id){
        repositorie.deleteById(id);
    }
}
