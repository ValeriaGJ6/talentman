package com.talentman.sgthtalentman.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talentman.sgthtalentman.models.PaisModel;

@Repository
public interface PaisRepository extends JpaRepository<PaisModel, Long>{
    PaisModel findByNombre(String nombre);
}
