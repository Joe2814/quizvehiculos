package org.example.Entidad;


import java.util.UUID;

public abstract class Entidad {
    private String id;

    public Entidad() {
        this.id = UUID.randomUUID().toString(); // Genera un ID único
    }

    public String getId() {
        return id;
    }
}
