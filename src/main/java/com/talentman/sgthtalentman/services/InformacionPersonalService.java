package com.talentman.sgthtalentman.services;

import java.time.LocalDate;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.talentman.sgthtalentman.dto.RegistroDTO;
import com.talentman.sgthtalentman.models.InformacionPersonalModel;
import com.talentman.sgthtalentman.models.MunicipioModel;
import com.talentman.sgthtalentman.models.PaisModel;
import com.talentman.sgthtalentman.repositories.InformacionPersonalRepository;
import com.talentman.sgthtalentman.transversal.Rol;

@Service
public class InformacionPersonalService {

    @Autowired
    private InformacionPersonalRepository informacionPersonalRepository;

    @Autowired
    private PaisService paisService;

    @Autowired
    private MunicipioService municipioService;

    // private static final Logger logger = LoggerFactory.getLogger(VacanteController.class);

    public InformacionPersonalModel getInformacionPersonalByIdUsuario(Long idUsuario) {
        return informacionPersonalRepository.findByIdUsuario_Id(idUsuario);
    }

    public InformacionPersonalModel convertToModel(RegistroDTO registroDTO) {
        InformacionPersonalModel informacionPersonal = new InformacionPersonalModel();
        informacionPersonal.setPrimerNombre(registroDTO.getPrimerNombre());
        informacionPersonal.setSegundoNombre(registroDTO.getSegundoNombre());
        informacionPersonal.setPrimerApellido(registroDTO.getPrimerApellido());
        informacionPersonal.setSegundoApellido(registroDTO.getSegundoApellido());
        informacionPersonal.setGenero(registroDTO.getGenero());
        informacionPersonal.setFechaNacimiento(registroDTO.getFechaNacimiento());
        informacionPersonal.setTipoDocumento(registroDTO.getTipoDocumento());
        informacionPersonal.setNumeroDocumento(registroDTO.getNumeroDocumento());

        PaisModel paisNacimiento = paisService.findById(registroDTO.getIdPaisNacimiento());
        informacionPersonal.setPaisNacimiento(paisNacimiento);

        if (registroDTO.getIdMunicipioNacimiento() != null) {
            MunicipioModel municipioNacimiento = municipioService.findById(registroDTO.getIdMunicipioNacimiento());
            informacionPersonal.setMunicipioNacimiento(municipioNacimiento);
        } else {
            informacionPersonal.setMunicipioNacimiento(null);
        }

        informacionPersonal.setCiudadOtroPaisNacimiento(registroDTO.getCiudadOtroPaisNacimiento());

        PaisModel paisResidencia = paisService.findById(registroDTO.getIdPaisResidencia());
        informacionPersonal.setPaisResidencia(paisResidencia);

        if (registroDTO.getIdMunicipioResidencia() != null) {
            MunicipioModel municipioResidencia = municipioService.findById(registroDTO.getIdMunicipioResidencia());
            informacionPersonal.setMunicipioResidencia(municipioResidencia);
        } else {
            informacionPersonal.setMunicipioResidencia(null);
        }

        informacionPersonal.setCiudadOtroPaisResidencia(registroDTO.getCiudadOtroPaisResidencia());
        informacionPersonal.setDireccion(registroDTO.getDireccion());
        informacionPersonal.setTelefono(registroDTO.getTelefono());
        informacionPersonal.setCorreo(registroDTO.getCorreo());
        informacionPersonal.setFechaRegistro(LocalDate.now());

        return informacionPersonal;
    }

    public InformacionPersonalModel save(InformacionPersonalModel informacionPersonal) {
        informacionPersonalRepository.save(informacionPersonal);
        return informacionPersonal;
    }

    public Page<InformacionPersonalModel> findPaginatedEmpleados(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Rol rol = Rol.valueOf("Estandar");
        return informacionPersonalRepository.findAllByUsuarioRolNot(rol, pageable);
    }

    public Page<InformacionPersonalModel> findPaginatedCandidatos(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Rol rol = Rol.valueOf("Estandar");        
        return informacionPersonalRepository.findAllByUsuarioRol(rol, pageable);
    }

    public InformacionPersonalModel findById(Long id) {
        return informacionPersonalRepository.findById(id).orElse(null);
    }
}
