package Vistacontrolador;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** Reservación de un laboratorio por un docente en una fecha y un horario. */
final class Apartado {

    static final String CONFIRMADO = "Confirmado";
    static final String NO_ASISTIO = "No asistió";
    static final String CANCELADO = "Cancelado";
    static final String REALIZADO = "Realizado";

    final String folio;
    final Laboratorio lab;
    final Maestro docente;
    final LocalDate fecha;
    final int horaInicio;
    final int horaFin;
    final String materia;
    final String grupo;
    final int alumnos;
    final String motivo;
    String estado = CONFIRMADO;
    String anotacion = "";

    Apartado(String folio, Laboratorio lab, Maestro docente, LocalDate fecha, int horaInicio, int horaFin,
            String materia, String grupo, int alumnos, String motivo) {
        this.folio = folio;
        this.lab = lab;
        this.docente = docente;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.materia = materia;
        this.grupo = grupo;
        this.alumnos = alumnos;
        this.motivo = motivo;
    }

    String horario() {
        return Util.horaTexto(horaInicio) + " - " + Util.horaTexto(horaFin);
    }

    int horas() {
        return horaFin - horaInicio;
    }

    boolean confirmado() {
        return CONFIRMADO.equals(estado);
    }

    LocalDateTime inicio() {
        return fecha.atTime(horaInicio, 0);
    }

    LocalDateTime fin() {
        return fecha.atTime(horaFin, 0);
    }

    boolean terminado() {
        return !fin().isAfter(LocalDateTime.now());
    }

    String estadoVisible() {
        return confirmado() && terminado() ? REALIZADO : estado;
    }

    /** Indica si este apartado ocupa alguna parte del horario dado en ese día. */
    boolean traslapa(LocalDate dia, int inicio, int fin) {
        return fecha.equals(dia) && horaInicio < fin && inicio < horaFin;
    }
}
