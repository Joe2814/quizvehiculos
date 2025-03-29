package org.example.Persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class VehiculoRepository {

    private static List<Vehiculo> vehiculos = new ArrayList<>();
    private static AtomicLong counter = new AtomicLong(1);

    public Vehiculo crearVehiculo(Vehiculo vehiculo) {
        vehiculo.setId(counter.getAndIncrement());
        vehiculos.add(vehiculo);
        return vehiculo;
    }

    public List<Vehiculo> listarVehiculos() {
        return vehiculos;
    }
}
