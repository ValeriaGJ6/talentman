package com.talentman.sgthtalentman.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "municipios")
@Getter
@Setter
public class MunicipioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "idMunicipio")
    private Long id;

    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDepartamento", referencedColumnName = "idDepartamento")
    private DepartamentoModel departamento;

    @OneToMany(mappedBy = "municipio", fetch = FetchType.LAZY)
    private List<VacanteModel> vacantes;

    @OneToMany(mappedBy = "municipioNacimiento", fetch = FetchType.LAZY)
    private List<InformacionPersonalModel> informacionPersonalNacimiento;

    @OneToMany(mappedBy = "municipioResidencia", fetch = FetchType.LAZY)
    private List<InformacionPersonalModel> informacionPersonalResidencia;
}
