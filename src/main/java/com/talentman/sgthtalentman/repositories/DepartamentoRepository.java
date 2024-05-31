package com.talentman.sgthtalentman.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talentman.sgthtalentman.dto.DepartamentoDTO;
import com.talentman.sgthtalentman.models.DepartamentoModel;

@Repository
public interface DepartamentoRepository extends JpaRepository<DepartamentoModel, Long>{
    List<DepartamentoDTO> findAllBy();
}
