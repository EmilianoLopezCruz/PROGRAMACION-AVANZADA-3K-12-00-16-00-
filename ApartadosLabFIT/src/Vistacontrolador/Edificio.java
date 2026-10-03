package Vistacontrolador;

import java.util.ArrayList;
import java.util.List;

final class Edificio {

    String nombre;
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
