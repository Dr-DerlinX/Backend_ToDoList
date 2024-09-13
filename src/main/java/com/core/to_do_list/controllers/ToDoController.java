package com.core.to_do_list.controllers;

import com.core.to_do_list.persistence.Entities.Tarea;
import com.core.to_do_list.persistence.Entities.Usuario;
import com.core.to_do_list.services.TareasInfoSevices;
import com.core.to_do_list.services.UserInfoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ToDoController {

    @Autowired
    private TareasInfoSevices tareasServices;
    @Autowired
    private UserInfoServices usuarioServices;

    @GetMapping("/tareas/{tarea_id}/usuario/{user_id}")
    public ResponseEntity<?> obtenerTareaPorId(@PathVariable Long tarea_id, @PathVariable Long user_id){
        Tarea tarea =tareasServices.obtenerTarea(tarea_id, user_id);
        return ResponseEntity.ok(tarea);
    }

    @GetMapping("/tareas/{usuario_id}")
    public ResponseEntity<?> obtenerTareaDeUsurio(@PathVariable Long usuario_id){
        Optional<Usuario> userTareas = usuarioServices.obtenerUsuario(usuario_id);

        if (userTareas.isPresent()){
            List<Tarea> tareas = userTareas.get().getTareas();
            return ResponseEntity.ok(tareas);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PostMapping("/tareas/{usuario_id}")
    public ResponseEntity<Tarea> postTareas(@PathVariable Long usuario_id, @RequestBody Tarea tareas){

        try{
            Tarea tareasEntity = tareasServices.agregarTarea(usuario_id, tareas);

            return ResponseEntity.status(HttpStatus.CREATED).body(tareasEntity);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @PutMapping("/tareas/{tarea_id}/usuario/{user_id}")
    public ResponseEntity<?> editarTarea(@PathVariable Long tarea_id, @PathVariable Long user_id,@RequestBody Tarea tarea){
        tareasServices.modificarTarea(user_id, tarea_id, tarea);
        return ResponseEntity.ok("Tarea edictada con exito..!");
    }

    @DeleteMapping("/tareas/{id}")
    public ResponseEntity<?> eliminarTarea(@PathVariable Long id){
        tareasServices.eliminarTarea(id);
        return ResponseEntity.ok("Tarea eliminada Exitosamente...!");
    }

}
