package com.talentman.sgthtalentman.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talentman.sgthtalentman.models.NominaModel;

@Repository
public interface NominaRepository extends JpaRepository<NominaModel, Long>{

}
