package com.core.to_do_list.services;

import com.core.to_do_list.config.JwtService;
import com.core.to_do_list.exceptions.RecouserNotFoundException;
import com.core.to_do_list.model.UserLogin;
import com.core.to_do_list.persistence.Entities.Usuario;
import com.core.to_do_list.persistence.Repositories.UserInfoRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@ComponentScan
public class UserInfoServices implements UserDetailsService {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Autowired
    private UserInfoServices services;

    @Autowired
    private JwtService jwtService;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

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

    public Map<String, String> loginByUserName(UserLogin credentials) throws RecouserNotFoundException{
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        Optional<Usuario> user = repositorie.findByname(username);

        if (!user.isPresent()){
            throw new RecouserNotFoundException("Usuario no encontrado en la base de datos");
        }
        else {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = jwtService.generateToken(credentials);

            Map<String, String> responce = new HashMap<>();

            responce.put("Token : ", token);

            return responce;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> user = repositorie.findByname(username);
        Usuario usuario = user.get();

        return new User(usuario.getName(), usuario.getPassword(), new ArrayList<>());
    }

    public Usuario autorizarUsuario(HttpHeaders headers) throws RecouserNotFoundException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = ((User) authentication.getPrincipal()).getUsername();

        Optional<Usuario> optionalUser = repositorie.findByname(username);

        if (optionalUser.isEmpty()) {
            throw new RecouserNotFoundException("Usuario no encontrado");
        }

        return optionalUser.get();
    }

    public Usuario editarUsuario(Usuario usuario) throws RecouserNotFoundException{

        Optional<Usuario> usuariID = repositorie.findById(usuario.getId());

        if (!usuariID.isPresent()) {
            throw new RecouserNotFoundException("Usuario no encontrado en la base de datos");
        }else {
            Usuario user = usuariID.get();
            user.setName(usuario.getName());
            user.setEmail(usuario.getEmail());
            user.setPassword(usuario.getPassword());

            return repositorie.save(user);
        }
    }

    public void eliminarUsuario(Long id) throws RecouserNotFoundException {
        Optional<Usuario> usuario = repositorie.findById(id);
        if (!usuario.isPresent()) {
            throw new RecouserNotFoundException("Usuario no encontrado en la base de datos");
        }
        repositorie.deleteById(id);
    }
}
