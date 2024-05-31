package com.talentman.sgthtalentman.transversal;

public enum Genero {

    Masculino(0),
    Femenino(1),
    Otro(2);
    
    private final int genero;

    Genero(int genero) {
        this.genero = genero;
    }

    public int getGenero() {
        return genero;
    }

}
