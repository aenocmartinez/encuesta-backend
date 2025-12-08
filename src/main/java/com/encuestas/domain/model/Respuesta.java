package com.encuestas.domain.model;

public class Respuesta {

    private final long id;
    private final String texto;
    private final int total;
    private final double porcentaje;

    public Respuesta(long id, String texto, int total, double porcentaje) {
        this.id = id;
        this.texto = texto;
        this.total = total;
        this.porcentaje = porcentaje;
    }

    public long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public int getTotal() {
        return total;
    }

    public double getPorcentaje() {
        return porcentaje;
    }
}