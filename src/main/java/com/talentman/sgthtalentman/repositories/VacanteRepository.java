package com.talentman.sgthtalentman.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.talentman.sgthtalentman.models.VacanteModel;

@Repository
public interface VacanteRepository extends JpaRepository<VacanteModel, Long>{
    
}
