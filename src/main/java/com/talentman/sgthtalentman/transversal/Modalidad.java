package com.talentman.sgthtalentman.transversal;

import lombok.Getter;

@Getter
public enum Modalidad {
    Presencial(0),
    Remoto(1),
    Híbrido(2);

    private final int id;

    Modalidad(int id) {
        this.id = id;
    }
}
