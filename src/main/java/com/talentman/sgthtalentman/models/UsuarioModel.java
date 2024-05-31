package com.talentman.sgthtalentman.models;

import java.util.Set;

import com.talentman.sgthtalentman.transversal.Rol;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "idUsuario")
    private Long id;

    @Column(unique = true, nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String contrasenaHash;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(nullable = false)
    private boolean activo;

    @ManyToMany
    @JoinTable(
        name = "aplicaciones_vacantes", 
        joinColumns = @JoinColumn(name = "idUsuario"), 
        inverseJoinColumns = @JoinColumn(name = "idVacante"))
    private Set<VacanteModel> vacantes;

}
