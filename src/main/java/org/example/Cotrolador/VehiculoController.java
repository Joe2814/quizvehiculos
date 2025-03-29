package org.example.Cotrolador;

import org.example.dtoo.controlador.entidad.VehiculoDTO;

import java.util.ArrayList;
import java.util.List;

public class VehiculoController {
    private static List<VehiculoDTO> vehiculos = new ArrayList<>();

    public void crearVehiculo(VehiculoDTO vehiculo) {
        vehiculos.add(vehiculo);
        System.out.println("Vehículo agregado: " + vehiculo.getMarca() + " " + vehiculo.getModelo());
    }

    public List<VehiculoDTO> listarVehiculos() {
        return vehiculos;
    }
}
