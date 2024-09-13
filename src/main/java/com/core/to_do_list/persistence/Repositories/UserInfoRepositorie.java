package com.core.to_do_list.persistence.Repositories;

import com.core.to_do_list.persistence.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepositorie extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByname(String name);
    public Optional<Usuario> findByemail(String email);
    public Optional<Usuario> findById(Long id);

}
