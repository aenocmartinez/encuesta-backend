package com.encuestas;

import com.encuestas.application.ConsultarResultadosEncuestaService;
import com.encuestas.domain.model.Encuesta;
import com.encuestas.domain.model.Respuesta;
import com.encuestas.infrastructure.CrowdsignalProveedorResultadosEncuesta;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class App {

    private static final String ENCUESTA_ID = "10503173";
    private static final String PARTNER_GUID = "5719aa21-c585-5aa9-42dd-00005547ef78";
    private static final String USER_CODE = "$P$BzM55aacFGir8JbSGtmXdZ7WGnKXiV1";

    public static void main(String[] args) {

        CrowdsignalProveedorResultadosEncuesta proveedor = new CrowdsignalProveedorResultadosEncuesta(PARTNER_GUID,
                USER_CODE);

        ConsultarResultadosEncuestaService service = new ConsultarResultadosEncuestaService(proveedor);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("====== Menú Encuesta ======");
            System.out.println("1. Registrar un voto (abrir encuesta en el navegador)");
            System.out.println("2. Consultar resultados");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    abrirEncuestaEnNavegador();
                    break;
                case "2":
                    Encuesta encuesta = service.ejecutar(ENCUESTA_ID);
                    imprimirResultados(encuesta);
                    break;
                case "3":
                    System.out.println("Hasta luego.");
                    return;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        }
    }

    private static void abrirEncuestaEnNavegador() {
        String url = "https://poll.fm/" + ENCUESTA_ID;

        System.out.println();
        System.out.println("Para registrar un voto se abrirá la encuesta en el navegador:");
        System.out.println(url);

        if (!Desktop.isDesktopSupported()) {
            System.out.println("Su sistema no permite abrir el navegador desde Java.");
            System.out.println("Abra manualmente esta URL: " + url);
        }

        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            System.out.println("No se pudo abrir el navegador.");
            System.out.println("Abra manualmente esta URL: " + url);
        }
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
