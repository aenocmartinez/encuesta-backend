package com.encuestas.infrastructure;

import com.encuestas.domain.model.Encuesta;
import com.encuestas.domain.model.Respuesta;
import com.encuestas.domain.port.ProveedorResultadosEncuesta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class CrowdsignalProveedorResultadosEncuesta implements ProveedorResultadosEncuesta {

    private static final String API_URL = "https://api.crowdsignal.com/v1";

    private final String partnerGuid;
    private final String userCode;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CrowdsignalProveedorResultadosEncuesta(String partnerGuid, String userCode) {
        this.partnerGuid = partnerGuid;
        this.userCode = userCode;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Encuesta obtenerResultados(String encuestaId) {
        String requestBody = construirBody(encuestaId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error al llamar a Crowdsignal. Código HTTP: " + response.statusCode());
            }

            return parsearEncuesta(encuestaId, response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al obtener resultados de la encuesta desde Crowdsignal", e);
        }
    }

    private String construirBody(String encuestaId) {
        return """
                {
                  "pdRequest": {
                    "partnerGUID": "%s",
                    "userCode": "%s",
                    "demands": {
                      "demand": {
                        "id": "GetPollResults",
                        "poll": {
                          "id": "%s"
                        }
                      }
                    }
                  }
                }
                """.formatted(partnerGuid, userCode, encuestaId);
    }

    private Encuesta parsearEncuesta(String encuestaId, String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);

            JsonNode answersNode = root
                    .path("pdResponse")
                    .path("demands")
                    .path("demand")
                    .get(0)
                    .path("result")
                    .path("answers")
                    .path("answer");

            List<Respuesta> respuestas = new ArrayList<>();

            if (answersNode.isArray()) {
                for (JsonNode answerNode : answersNode) {
                    long id = answerNode.path("id").asLong();
                    String texto = answerNode.path("text").asText();
                    int total = answerNode.path("total").asInt();
                    double porcentaje = answerNode.path("percent").asDouble();

                    Respuesta respuesta = new Respuesta(id, texto, total, porcentaje);
                    respuestas.add(respuesta);
                }
            }

            return new Encuesta(encuestaId, respuestas);
        } catch (IOException e) {
            throw new RuntimeException("Error al parsear la respuesta de Crowdsignal", e);
        }
    }
}
