package com.talentman.sgthtalentman.models;

import java.time.LocalDate;
import java.util.Set;

import com.talentman.sgthtalentman.transversal.Modalidad;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vacantes")
@Getter
@Setter
public class VacanteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "idVacante")
    private Long id;

    private String nombre;

    @Column(columnDefinition = "LONGTEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMunicipio", referencedColumnName = "idMunicipio")
    private MunicipioModel municipio; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPais", referencedColumnName = "idPais")
    private PaisModel pais;

    private String ciudadOtroPais;

    private LocalDate fechaPublicacion;
    
    @Enumerated(EnumType.ORDINAL)
    private Modalidad modalidad;
    
    private boolean activo;

    @ManyToMany(mappedBy = "vacantes")
    private Set<UsuarioModel> usuarios;
    
}
