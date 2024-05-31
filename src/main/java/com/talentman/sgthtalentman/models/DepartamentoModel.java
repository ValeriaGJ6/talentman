package com.talentman.sgthtalentman.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "departamentos")
@Getter
@Setter
public class DepartamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "idDepartamento")
    private Long id;
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "idPais", referencedColumnName = "idPais")
    private PaisModel pais;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MunicipioModel> municipios;

}
