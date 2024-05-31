package com.talentman.sgthtalentman.models;


import java.time.LocalDate;

import com.talentman.sgthtalentman.transversal.Genero;
import com.talentman.sgthtalentman.transversal.TipoDocumento;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "informacion_personal")
@Getter
@Setter
public class InformacionPersonalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "idInformacionPersonal")
    private Long id;

    @Column(nullable = false)
    private String primerNombre;

    private String segundoNombre;

    @Column(nullable = false)
    private String primerApellido;
    
    private String segundoApellido;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genero genero;
    
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPaisNacimiento", referencedColumnName = "idPais", nullable = false)
    private PaisModel paisNacimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMunicipioNacimiento", referencedColumnName = "idMunicipio")
    private MunicipioModel municipioNacimiento;

    private String ciudadOtroPaisNacimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPaisResidencia", referencedColumnName = "idPais", nullable = false)
    private PaisModel paisResidencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMunicipioResidencia", referencedColumnName = "idMunicipio")
    private MunicipioModel municipioResidencia;

    private String ciudadOtroPaisResidencia;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String telefono;
    
    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Column(nullable = false)
    private String numeroDocumento;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    private LocalDate fechaIngreso;

    private LocalDate ultimoDiaLaborado;

    @OneToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioModel idUsuario;

    @OneToOne(mappedBy = "informacionPersonal")
    private NominaModel nomina;


}
