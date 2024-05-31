package com.talentman.sgthtalentman.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talentman.sgthtalentman.models.EstadoCandidatoModel;
import com.talentman.sgthtalentman.repositories.EstadoCandidatoRepository;

@Service
public class EstadoCandidatoService {
    
    @Autowired
    private EstadoCandidatoRepository estadoCandidatoRepository;

    public EstadoCandidatoModel save(EstadoCandidatoModel estadoCandidato) {
        return estadoCandidatoRepository.save(estadoCandidato);   
    }
    
}
