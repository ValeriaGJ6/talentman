package com.talentman.sgthtalentman.models;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estados_candidatos")
@Getter
@Setter
public class EstadoCandidatoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "idEstadoCandidato")
    private Long id;

    @OneToOne
    @JoinColumn(name = "idInformacionPersonal", referencedColumnName = "idInformacionPersonal")
    private InformacionPersonalModel informacionPersonal;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private LocalDate fechaActualizacion;
}
