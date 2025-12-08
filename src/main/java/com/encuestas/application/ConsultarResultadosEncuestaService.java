package com.encuestas.application;

import com.encuestas.domain.model.Encuesta;
import com.encuestas.domain.port.ProveedorResultadosEncuesta;

public class ConsultarResultadosEncuestaService {

    private final ProveedorResultadosEncuesta proveedorResultadosEncuesta;

    public ConsultarResultadosEncuestaService(ProveedorResultadosEncuesta proveedorResultadosEncuesta) {
        this.proveedorResultadosEncuesta = proveedorResultadosEncuesta;
    }

    public Encuesta ejecutar(String encuestaId) {
        return this.proveedorResultadosEncuesta.obtenerResultados(encuestaId);
    }

}
