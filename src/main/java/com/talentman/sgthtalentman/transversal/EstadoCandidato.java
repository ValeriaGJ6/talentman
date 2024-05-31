package com.talentman.sgthtalentman.transversal;

public enum EstadoCandidato {
    Aplicado,         // El candidato ha aplicado a la vacante
    Revision,      // La solicitud del candidato está siendo revisada
    Entrevista,       // El candidato ha sido invitado a una entrevista
    Evaluacion,       // El candidato está siendo evaluado (pruebas técnicas)
    Aceptado,         // El candidato ha aceptado la oferta
    Rechazado,        // El candidato ha sido rechazado en alguna etapa del proceso
    Retirado          // El candidato ha retirado su solicitud
}
