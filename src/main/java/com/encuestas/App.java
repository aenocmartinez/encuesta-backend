package com.encuestas;

import com.encuestas.application.ConsultarResultadosEncuestaService;
import com.encuestas.domain.model.Encuesta;
import com.encuestas.domain.model.Respuesta;
import com.encuestas.infrastructure.CrowdsignalProveedorResultadosEncuesta;

public class App {

    private static final String ENCUESTA_ID = Config.get("encuesta.id");
    private static final String PARTNER_GUID = Config.get("crowdsignal.partnerGuid");
    private static final String USER_CODE = Config.get("crowdsignal.userCode");

    public static void main(String[] args) {

        CrowdsignalProveedorResultadosEncuesta proveedor = new CrowdsignalProveedorResultadosEncuesta(PARTNER_GUID,
                USER_CODE);

        ConsultarResultadosEncuestaService service = new ConsultarResultadosEncuestaService(proveedor);

        Encuesta encuesta = service.ejecutar(ENCUESTA_ID);

        imprimirResultados(encuesta);

    }

    private static void imprimirResultados(Encuesta encuesta) {
        int totalVotos = 0;
        for (Respuesta r : encuesta.getRespuestas()) {
            totalVotos += r.getTotal();
        }

        System.out.println("========================================");
        System.out.println("   Resultados de la encuesta " + encuesta.getId());
        System.out.println("========================================");
        System.out.printf("%-20s | %5s | %10s%n", "Respuesta", "Votos", "Porcentaje");
        System.out.println("----------------------------------------");

        for (Respuesta r : encuesta.getRespuestas()) {
            double porcentajeCalculado = totalVotos > 0
                    ? (r.getTotal() * 100.0) / totalVotos
                    : r.getPorcentaje();

            System.out.printf(
                    "%-20s | %5d | %9.2f%%%n",
                    capitalizar(r.getTexto()),
                    r.getTotal(),
                    porcentajeCalculado);
        }

        System.out.println("----------------------------------------");
        System.out.printf("Total votos: %d%n", totalVotos);
    }

    private static String capitalizar(String texto) {
        if (texto == null || texto.isBlank())
            return texto;
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }
}
