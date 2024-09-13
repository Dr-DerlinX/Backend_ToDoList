package com.core.to_do_list.services;

import com.core.to_do_list.persistence.Entities.Tarea;
import com.core.to_do_list.persistence.Entities.Usuario;
import com.core.to_do_list.persistence.Repositories.TereasInfoRepositorie;
import com.core.to_do_list.persistence.Repositories.UserInfoRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ComponentScan
public class TareasInfoSevices{

    @Autowired
    private TereasInfoRepositorie tareaRepositorie;

    @Autowired
    private UserInfoRepositorie userRepositorie;

    public Tarea obtenerTarea(Long tarea_id, Long user_id){
        Tarea tarea = tareaRepositorie.findById(tarea_id).orElseThrow(() -> new RuntimeException("Tarea no encontrado"));
        Usuario usuario = userRepositorie.findById(user_id).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return tarea;
    }

    public Tarea agregarTarea(Long usuarioId, Tarea tareas){

        Optional<Usuario> usuarioOpt = userRepositorie.findById(usuarioId);

        if (usuarioOpt.isPresent()){
            tareas.setUsuario(usuarioOpt.get());
            return tareaRepositorie.save(tareas);
        }
        else {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
        }
    }

    public Tarea modificarTarea(Long user_id ,Long tarea_id, Tarea tareaDetalle){

       Tarea tarea = tareaRepositorie.findById(tarea_id).orElseThrow(() -> new RuntimeException("Tarea no encontrado"));

       Usuario usuario = userRepositorie.findById(user_id).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

       tarea.setDescripcion(tareaDetalle.getDescripcion());
       tarea.setEstado(tareaDetalle.getEstado());

       tarea.setUsuario(usuario);

       return tareaRepositorie.save(tarea);
    }

    public void eliminarTarea(Long id){
        tareaRepositorie.deleteById(id);
    }


}
