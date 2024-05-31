package com.talentman.sgthtalentman.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiDepartamentoDTO {
    private Long id;

    @JsonProperty("name")
    private String nombre;
}
