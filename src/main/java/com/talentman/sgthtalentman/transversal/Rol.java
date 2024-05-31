package com.talentman.sgthtalentman.transversal;

import lombok.Getter;

@Getter
public enum Rol {
    Estandar(0),
    Administrador(1),
    AnalistaRRHH(2),
    Psicologo(3),
    Contador(4),
    Empleado(5);

    private final int id;

    Rol(int id) {
        this.id = id;
    }

}
