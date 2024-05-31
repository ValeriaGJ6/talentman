package com.talentman.sgthtalentman.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.talentman.sgthtalentman.transversal.Genero;
import com.talentman.sgthtalentman.transversal.TipoDocumento;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistroDTO {

    @NotBlank(message = "El campo Primer Nombre es obligatorio")
    private String primerNombre;

    private String segundoNombre;

    @NotBlank(message = "El campo Primer Apellido es obligatorio")
    private String primerApellido;

    private String segundoApellido;

    @NotNull(message = "El campo Género es obligatorio")
    private Genero genero;

    @NotNull(message = "El campo Fecha de Nacimiento es obligatorio")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El campo País de Nacimiento es obligatorio")
    private Long idPaisNacimiento;

    private Long idDepartamentoNacimiento;

    private Long idMunicipioNacimiento;
    private String ciudadOtroPaisNacimiento;

    @NotNull(message = "El campo País de Residencia es obligatorio")
    private Long idPaisResidencia;

    private Long idDepartamentoResidencia;

    private Long idMunicipioResidencia;
    private String ciudadOtroPaisResidencia;

    @NotBlank(message = "El campo Dirección es obligatorio")
    private String direccion;

    @NotBlank(message = "El campo Teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[0-9 ]*$", message = "El campo Teléfono solo puede contener números y el signo '+' si es un número internacional.")
    private String telefono;

    @NotBlank(message = "El campo Correo es obligatorio")
    @Email(message = "El campo Correo no es válido. Debe contener un formato de correo electrónico válido: ejemplo@correo.com")
    private String correo;

    @NotNull(message = "El campo Tipo de Documento es obligatorio")
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El campo Número de Documento es obligatorio")
    @Pattern(regexp = "^[0-9]*$", message = "El campo Número de Documento solo puede contener números.")
    private String numeroDocumento;

    @NotBlank(message = "El campo Usuario es obligatorio")
    private String usuario;

    @NotBlank(message = "El campo Contraseña es obligatorio")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasena;

    @AssertTrue(message = "El campo Municipio es obligatorio")
    public boolean isMunicipioRequired() {
        boolean isMunicipioNacimientoRequired = !(idPaisNacimiento != null && idPaisNacimiento.equals(51L) && (idMunicipioNacimiento == null));
        boolean isMunicipioResidenciaRequired = !(idPaisResidencia != null && idPaisResidencia.equals(51L) && idMunicipioResidencia == null);
        return isMunicipioNacimientoRequired || isMunicipioResidenciaRequired;
    }

}
