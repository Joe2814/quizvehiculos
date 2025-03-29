package org.example.dtoo.controlador.entidad;


import org.example.Entidad.Entidad;

import java.time.LocalDate;

public class VehiculoDTO extends Entidad {
    private String marca;
    private String modelo;
    private int anio;
    private String color;
    private LocalDate fechaCompra;

    public VehiculoDTO(String marca, String modelo, int anio, String color, LocalDate fechaCompra) {
        super(); // Llama al constructor de Entidad para asignar un ID único
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.fechaCompra = fechaCompra;
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public LocalDate getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDate fechaCompra) { this.fechaCompra = fechaCompra; }
}
