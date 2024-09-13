package com.core.to_do_list.persistence.Repositories;

import com.core.to_do_list.persistence.Entities.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TereasInfoRepositorie extends JpaRepository<Tarea, Long> {

}
