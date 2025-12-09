package com.encuestas;

import static spark.Spark.*;

import com.encuestas.application.ConsultarResultadosEncuestaService;
import com.encuestas.infrastructure.CrowdsignalProveedorResultadosEncuesta;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppRest {

    private static final String PARTNER_GUID = Config.get("crowdsignal.partnerGuid");
    private static final String USER_CODE = Config.get("crowdsignal.userCode");

    public static void main(String[] args) {

        var proveedor = new CrowdsignalProveedorResultadosEncuesta(PARTNER_GUID, USER_CODE);

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
