package com.talentman.sgthtalentman.dto;

import java.time.LocalDate;

import com.talentman.sgthtalentman.transversal.Modalidad;

import groovy.transform.ToString;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class VacanteDTO {

    private Long id;
    @NotBlank(message = "El campo Nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El campo Descripción es obligatorio")
    private String descripcion;

    @NotNull(message = "El campo País es obligatorio")
    private Long idPais;
    private String nombrePais;
    
    private Long idDepartamento;
    private String nombreDepartamento;
    
    private Long idMunicipio;
    private String nombreMunicipio;

    private String ciudadOtroPais;
    
    private Modalidad modalidad;

    private LocalDate fechaPublicacion;

    private boolean activo;

    @AssertTrue(message = "El campo Municipio es obligatorio")
    public boolean isMunicipioRequired() {
        return !(idPais != null && idPais.equals(51L) && idMunicipio == null);
    }
}
