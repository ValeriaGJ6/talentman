package com.talentman.sgthtalentman.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talentman.sgthtalentman.dto.MunicipioDTO;
import com.talentman.sgthtalentman.models.MunicipioModel;
import com.talentman.sgthtalentman.repositories.MunicipioRepository;

@Service
public class MunicipioService {

    public MunicipioRepository municipioRepository;

    public MunicipioService(MunicipioRepository municipioRepository) {
        this.municipioRepository = municipioRepository;
    }

    public List<MunicipioDTO> findByDepartamentoId(Long departamentoId) {
        return municipioRepository.findByDepartamentoId(departamentoId);
    }

    public MunicipioModel findById(Long idMunicipio) {
        return municipioRepository.findById(idMunicipio).orElse(null);
    }

    public MunicipioModel findbyNombre(String nombre) {
        return municipioRepository.findByNombre(nombre);
    }
}
