package com.encuestas.domain.port;

import com.encuestas.domain.model.Encuesta;

public interface ProveedorResultadosEncuesta {

    /**
     * Obtiene una encuesta con sus respuestas y resultados
     * 
     * @param encuestaId
     * @return Encuesta con sus respuestas y resultados
     */
    Encuesta obtenerResultados(String encuestaId);
}