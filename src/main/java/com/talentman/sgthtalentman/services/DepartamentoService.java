package com.talentman.sgthtalentman.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talentman.sgthtalentman.dto.DepartamentoDTO;
import com.talentman.sgthtalentman.repositories.DepartamentoRepository;

@Service
public class DepartamentoService {
    private final DepartamentoRepository departamentoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    public List<DepartamentoDTO> findAll() {
        return departamentoRepository.findAllBy();
    }
}
