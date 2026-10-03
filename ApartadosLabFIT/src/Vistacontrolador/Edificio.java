package Vistacontrolador;

import java.util.ArrayList;
import java.util.List;

/** Edificio del campus con sus laboratorios. */
final class Edificio {

    final String nombre;
    final String prefijo;
    final List<Laboratorio> labs = new ArrayList<>();

    Edificio(String nombre, String prefijo) {
        this.nombre = nombre;
        this.prefijo = prefijo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
