package com.encuestas;

import static spark.Spark.*;

import com.encuestas.application.ConsultarResultadosEncuestaService;
import com.encuestas.infrastructure.CrowdsignalProveedorResultadosEncuesta;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppRest {

    public static void main(String[] args) {

        var proveedor = new CrowdsignalProveedorResultadosEncuesta(
                "5719aa21-c585-5aa9-42dd-00005547ef78",
                "$P$BzM55aacFGir8JbSGtmXdZ7WGnKXiV1");

        var servicio = new ConsultarResultadosEncuestaService(proveedor);

        var mapper = new ObjectMapper();

        get("/encuestas/:id", (req, res) -> {
            try {
                String id = req.params(":id");

                var encuesta = servicio.ejecutar(id);

                res.type("application/json");
                return mapper.writeValueAsString(encuesta);
            } catch (Exception e) {
                e.printStackTrace();
                res.status(500);
                res.type("text/plain");
                return "Error interno: " + e.getMessage();
            }
        });

        System.out.println("API REST iniciada en http://localhost:4567");
    }
}
