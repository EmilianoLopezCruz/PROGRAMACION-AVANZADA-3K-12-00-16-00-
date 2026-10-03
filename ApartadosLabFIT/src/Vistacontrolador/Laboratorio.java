package Vistacontrolador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

final class Laboratorio {

    static final String DISPONIBLE = "Disponible";
    static final String MANTENIMIENTO = "En mantenimiento";

    final Edificio edificio;
    final String clave;
    String nombre;
    String planta;
    int capacidad;
    int equipos;
    String responsable;
    String software;
    String caracteristicas;
    String estado = DISPONIBLE;
    String motivo = "";

    LocalDate reapertura;

    private final List<String> notas = new ArrayList<>();

    Laboratorio(Edificio edificio, String clave, String nombre, String planta, int capacidad, int equipos,
            String responsable, String software, String caracteristicas) {
        this.edificio = edificio;
        this.clave = clave;
        this.nombre = nombre;
        this.planta = planta;
        this.capacidad = capacidad;
        this.equipos = equipos;
        this.responsable = responsable;
        this.software = software;
        this.caracteristicas = caracteristicas;
    }

    String ubicacion() {
        return edificio.nombre + ", " + planta.toLowerCase(Locale.ROOT);
    }

    boolean enMantenimiento() {
        return MANTENIMIENTO.equals(estado);
    }

    boolean enMantenimientoEn(LocalDate dia) {
        return enMantenimiento() && (reapertura == null || dia.isBefore(reapertura));
    }

    List<String> notas() {
        return new ArrayList<>(notas);
    }

    void agregarNota(String texto) {
        notas.add(0, Catalogo.fecha(0) + "  ·  " + texto);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
