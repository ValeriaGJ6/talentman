package com.talentman.sgthtalentman.dto;

import com.talentman.sgthtalentman.models.NominaModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpleadoNominaDTO {

    private Long id;
    private String nombre;
    private String numeroDocumento;    
    private NominaModel nomina;

}
