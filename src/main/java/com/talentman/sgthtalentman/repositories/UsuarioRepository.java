package com.talentman.sgthtalentman.repositories;

import com.talentman.sgthtalentman.models.UsuarioModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long>{
    public UsuarioModel findByUsuario(String usuario);

    @Query("SELECT u.rol FROM UsuarioModel u WHERE u.id = :id")
    public String getRolById(@Param("id") Long idUsuario);

}
