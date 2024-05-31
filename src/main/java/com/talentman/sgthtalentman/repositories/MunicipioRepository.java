package com.talentman.sgthtalentman.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talentman.sgthtalentman.dto.MunicipioDTO;
import com.talentman.sgthtalentman.models.MunicipioModel;

@Repository
public interface MunicipioRepository extends JpaRepository<MunicipioModel, Long>{

    List<MunicipioDTO> findByDepartamentoId(Long departamentoId);
    MunicipioModel findByNombre(String nombre);

}
