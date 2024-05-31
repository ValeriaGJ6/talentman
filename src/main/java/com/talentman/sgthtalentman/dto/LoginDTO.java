package com.talentman.sgthtalentman.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "El campo usuario es obligatorio")
    private String usuario;
    @NotBlank(message = "El campo contraseña es obligatorio")
    private String contrasena;
}
