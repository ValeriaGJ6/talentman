package com.talentman.sgthtalentman.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "paises")
@Getter
@Setter
public class PaisModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "idPais")
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "paisNacimiento")
    private List<InformacionPersonalModel> informacionPersonalNacimiento;

    @OneToMany(mappedBy = "paisResidencia")
    private List<InformacionPersonalModel> informacionPersonalResidencia;
}
