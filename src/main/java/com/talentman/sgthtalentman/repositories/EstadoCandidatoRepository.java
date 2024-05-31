package com.talentman.sgthtalentman.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talentman.sgthtalentman.models.EstadoCandidatoModel;

@Repository
public interface EstadoCandidatoRepository extends JpaRepository<EstadoCandidatoModel, Long>{

}
