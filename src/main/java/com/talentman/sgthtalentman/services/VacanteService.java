package com.talentman.sgthtalentman.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.talentman.sgthtalentman.dto.VacanteDTO;
import com.talentman.sgthtalentman.models.MunicipioModel;
import com.talentman.sgthtalentman.models.PaisModel;
import com.talentman.sgthtalentman.models.VacanteModel;
import com.talentman.sgthtalentman.repositories.VacanteRepository;

@Service
public class VacanteService {

    @Autowired
    private VacanteRepository vacanteRepository;
    @Autowired
    private MunicipioService municipioService;
    @Autowired
    private PaisService paisService;

    public VacanteModel save(VacanteModel vacante) {
        vacante.setFechaPublicacion(LocalDate.now());
        return vacanteRepository.save(vacante);
    }

    public VacanteModel update(VacanteModel vacante) {
        return vacanteRepository.save(vacante);
    }

    public Page<VacanteModel> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return vacanteRepository.findAll(pageable);
    }

    public VacanteModel findById(Long id) {
        return vacanteRepository.findById(id).orElse(null);
    }

    public VacanteDTO convertToDTO(VacanteModel model) {
        VacanteDTO dto = new VacanteDTO();
        dto.setNombre(model.getNombre());
        dto.setDescripcion(model.getDescripcion());

        if (model.getMunicipio() != null) {
            dto.setIdMunicipio(model.getMunicipio().getId());
            if (model.getMunicipio().getDepartamento() != null) {
                dto.setIdDepartamento(model.getMunicipio().getDepartamento().getId());
            }
        }

        dto.setIdPais(model.getPais().getId());
        dto.setCiudadOtroPais(model.getCiudadOtroPais());
        dto.setModalidad(model.getModalidad());
        dto.setFechaPublicacion(model.getFechaPublicacion());
        dto.setActivo(model.isActivo());

        return dto;
    }

    public VacanteModel convertToModel(VacanteDTO dto) {
        VacanteModel model = new VacanteModel();
        model.setNombre(dto.getNombre());
        model.setDescripcion(dto.getDescripcion());

        PaisModel paisElegido = paisService.findById(dto.getIdPais());
        model.setPais(paisElegido);

        if (dto.getIdMunicipio() != null) {
            MunicipioModel municipio = municipioService.findById(dto.getIdMunicipio());
            model.setMunicipio(municipio);
        } else {
            model.setMunicipio(null);            
        }

        model.setCiudadOtroPais(dto.getCiudadOtroPais());
        model.setFechaPublicacion(LocalDate.now());
        model.setModalidad(dto.getModalidad());
        model.setActivo(dto.isActivo());

        return model;
    }

    public void delete(Long id) {
        vacanteRepository.deleteById(id);
    }

}
