package com.talentman.sgthtalentman.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talentman.sgthtalentman.models.InformacionPersonalModel;
import com.talentman.sgthtalentman.transversal.Rol;

@Repository
public interface InformacionPersonalRepository extends JpaRepository<InformacionPersonalModel, Long> {
    InformacionPersonalModel findByIdUsuario_Id(Long idUsuario);

    @Query("SELECT i FROM InformacionPersonalModel i WHERE i.idUsuario.rol != :rol")
    public Page<InformacionPersonalModel> findAllByUsuarioRolNot(@Param("rol") Rol rol, Pageable pageable);

    @Query("SELECT i FROM InformacionPersonalModel i WHERE i.idUsuario.rol = :rol")
    public Page<InformacionPersonalModel> findAllByUsuarioRol(@Param("rol") Rol rol, Pageable pageable);
}
