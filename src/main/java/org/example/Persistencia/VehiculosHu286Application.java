package org.example.Persistencia;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class VehiculosHu286Application {

    private static VehiculoRepository repository = new VehiculoRepository();

    public static void main(String[] args) throws Exception {
        // Crear el servidor HTTP en el puerto 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Endpoint HU_286_crearveh: POST /hu_286/crearveh
        server.createContext("/hu_286/crearveh", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                    String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                            .lines().reduce("", (acc, line) -> acc + line);
                    Vehiculo vehiculo = parseVehiculo(body);
                    if (vehiculo != null) {
                        vehiculo = repository.crearVehiculo(vehiculo);
                        String response = vehiculoToJson(vehiculo);
                        exchange.sendResponseHeaders(200, response.getBytes().length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } else {
                        String response = "Error al parsear el vehículo";
                        exchange.sendResponseHeaders(400, response.getBytes().length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    }
                } else {
                    exchange.sendResponseHeaders(405, -1);
                }
            }
        });

        // Endpoint HU_286_listavehiculo: GET /hu_286/listavehiculo
        server.createContext("/hu_286/listavehiculo", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                    List<Vehiculo> lista = repository.listarVehiculos();
                    String response = vehiculosListToJson(lista);
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } else {
                    exchange.sendResponseHeaders(405, -1);
                }
            }
        });

        server.start();
        System.out.println("Servidor iniciado en http://localhost:8080");
    }

    // Método muy básico para parsear JSON y crear un Vehiculo.
    // Se espera un JSON con formato:
    // {"marca":"Toyota","modelo":"Corolla","anio":2022,"color":"Blanco","fechaCompra":"2023-03-01"}
    private static Vehiculo parseVehiculo(String json) {
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                json = json.substring(1);
            }
            if (json.endsWith("}")) {
                json = json.substring(0, json.length() - 1);
            }
            String[] parts = json.split(",");
            String marca = null, modelo = null, color = null, fechaCompraStr = null;
            int anio = 0;
            for (String part : parts) {
                String[] kv = part.split(":", 2);
                if (kv.length != 2) continue;
                String key = kv[0].trim().replaceAll("\"", "");
                String value = kv[1].trim().replaceAll("\"", "");
                switch (key) {
                    case "marca":
                        marca = value;
                        break;
                    case "modelo":
                        modelo = value;
                        break;
                    case "anio":
                        anio = Integer.parseInt(value);
                        break;
                    case "color":
                        color = value;
                        break;
                    case "fechaCompra":
                        fechaCompraStr = value;
                        break;
                }
            }
            if (marca != null && modelo != null && color != null && fechaCompraStr != null) {
                LocalDate fechaCompra = LocalDate.parse(fechaCompraStr);
                return new Vehiculo(null, marca, modelo, anio, color, fechaCompra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Convierte un Vehiculo a JSON de forma manual.
    private static String vehiculoToJson(Vehiculo v) {
        return "{"
                + "\"id\":" + v.getId() + ","
                + "\"marca\":\"" + v.getMarca() + "\","
                + "\"modelo\":\"" + v.getModelo() + "\","
                + "\"anio\":" + v.getAnio() + ","
                + "\"color\":\"" + v.getColor() + "\","
                + "\"fechaCompra\":\"" + v.getFechaCompra().toString() + "\""
                + "}";
    }

    // Convierte una lista de vehículos a un arreglo JSON de forma manual.
    private static String vehiculosListToJson(List<Vehiculo> lista) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < lista.size(); i++) {
            sb.append(vehiculoToJson(lista.get(i)));
            if (i < lista.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
