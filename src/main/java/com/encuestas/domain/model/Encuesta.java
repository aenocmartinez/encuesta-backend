package com.encuestas.domain.model;

import java.util.List;

public class Encuesta {
    private final String id;
    private final List<Respuesta> respuestas;

    public Encuesta(String id, List<Respuesta> respuestas) {
        this.id = id;
        this.respuestas = respuestas;
    }

    public String getId() {
        return id;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }
}
