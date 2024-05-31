package com.talentman.sgthtalentman.transversal;

public enum TipoDocumento {

    CC(0),
    TI(1),
    CE(2),
    PASAPORTE(3);
    
    private final int tipoDocumento;

    TipoDocumento(int tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public int getTipoDocumento() {
        return tipoDocumento;
    }
}
