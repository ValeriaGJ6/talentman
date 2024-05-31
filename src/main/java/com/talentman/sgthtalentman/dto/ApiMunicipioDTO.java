package com.talentman.sgthtalentman.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiMunicipioDTO {
    private Long id;

    @JsonProperty("name")
    private String nombre;

    @JsonProperty("departmentId")
    private Long departamentoId;
}
