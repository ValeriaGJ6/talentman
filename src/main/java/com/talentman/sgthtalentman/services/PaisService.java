package com.talentman.sgthtalentman.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talentman.sgthtalentman.models.PaisModel;
import com.talentman.sgthtalentman.repositories.PaisRepository;

@Service
public class PaisService {

    private final PaisRepository paisRepository;

    public PaisService(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    public List<PaisModel> findAll() {
        return paisRepository.findAll();
    }

    public PaisModel findById(Long idPais) {
        return paisRepository.findById(idPais).orElse(null);
    }

    public PaisModel findByNombre(String nombre) {
        return paisRepository.findByNombre(nombre);
    }
}
