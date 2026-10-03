package Vistacontrolador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Datos en memoria del prototipo: edificios, laboratorios, maestros y apartados, junto con las reglas
 * de negocio (validaciones, traslapes, mantenimiento). Los controladores solo hablan con esta clase,
 * así que al conectar una base de datos basta con sustituir su interior por DAO o repositorios.
 * Lo que se agrega o elimina dura mientras la aplicación está abierta.
 */
final class Catalogo {

    /** Primera hora en la que se puede iniciar un apartado y última hora en la que puede terminar. */
    static final int HORA_PRIMERA = 7;
    static final int HORA_ULTIMA = 21;

    static final String[] PLANTAS = { "Planta baja", "Planta alta" };

    private static final String CARACTERISTICAS_DEFECTO = "Aire acondicionado, proyector y pizarrón.";
    private static final String MOTIVO_DEFECTO = "Revisión del cableado y cambio de equipos";
    private static final String[] PALABRAS_VACIAS = { "de", "del", "la", "las", "los", "el", "y", "en" };
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FECHA_ENTRADA = DateTimeFormatter.ofPattern("d/M/yyyy");

    private static final List<Edificio> EDIFICIOS = new ArrayList<>();
    private static final List<Maestro> MAESTROS = new ArrayList<>();
    private static final List<Apartado> APARTADOS = new ArrayList<>();
    private static int secuenciaFolio = 0;

    /** Índice del edificio que se está administrando. */
    static int edificio = 0;

    static {
        Edificio industrial = new Edificio("Nave de Industrial", "NI");
        sembrar(industrial, "Manufactura", 20, 12, false, "Ing. Ramón Salinas", "CAD/CAM, SolidWorks");
        sembrar(industrial, "Física", 24, 8, false, "Ing. Ramón Salinas", "Sensores y simuladores");
        sembrar(industrial, "Geomática", 20, 20, false, "Ing. Ramón Salinas", "ArcGIS, QGIS");
        sembrar(industrial, "Química y ambiental", 18, 6, true, "Ing. Ramón Salinas", "Equipo de análisis de laboratorio");
        sembrar(industrial, "Aula interactiva", 35, 35, false, "Ing. Ramón Salinas", "Pizarrón interactivo, Office");
        sembrar(industrial, "Ergonomía", 15, 10, false, "Ing. Ramón Salinas", "Software de análisis postural");
        sembrar(industrial, "Mecánica de fluidos e hidráulica", 20, 8, false, "Ing. Ramón Salinas", "Banco hidráulico, sensores de flujo");
        sembrar(industrial, "Microcontroladores y robótica", 20, 15, false, "Lic. Sofía Vega", "Arduino IDE, MPLAB");
        sembrar(industrial, "Redes", 20, 20, false, "Lic. Sofía Vega", "Packet Tracer, Wireshark");
        EDIFICIOS.add(industrial);

        Edificio civil = new Edificio("Nave de Civil", "NC");
        sembrar(civil, "Topografía", 25, 10, false, "Ing. Patricia Lozano", "AutoCAD, Civil 3D");
        sembrar(civil, "Materiales de construcción y pavimentos", 20, 8, false, "Ing. Patricia Lozano", "Equipo de ensayo de materiales");
        EDIFICIOS.add(civil);

        MAESTROS.add(new Maestro("Dr. Navarro", "navarro@ejemplo.edu.mx", "Sistemas computacionales"));
        MAESTROS.add(new Maestro("Mtra. Gómez", "gomez@ejemplo.edu.mx", "Ciencias básicas"));
        MAESTROS.add(new Maestro("Ing. Herrera", "herrera@ejemplo.edu.mx", "Ingeniería industrial"));
        MAESTROS.add(new Maestro("Dra. Ruiz", "ruiz@ejemplo.edu.mx", "Ingeniería civil"));
        MAESTROS.add(new Maestro("M.C. Torres", "torres@ejemplo.edu.mx", "Sistemas computacionales"));

        List<Laboratorio> l = industrial.labs;
        sembrarApartado(l.get(0), MAESTROS.get(0), 0, 8, 10, "Procesos de manufactura", "3A", 12, Apartado.CONFIRMADO, "");
        sembrarApartado(l.get(1), MAESTROS.get(1), 0, 10, 12, "Física general", "5B", 8, Apartado.NO_ASISTIO,
                "El docente no se presentó.");
        sembrarApartado(l.get(2), MAESTROS.get(2), 1, 12, 14, "Sistemas de información geográfica", "3C", 18, Apartado.CONFIRMADO, "");
        sembrarApartado(l.get(4), MAESTROS.get(3), 2, 15, 17, "Taller de innovación", "2B", 30, Apartado.CONFIRMADO, "");
        sembrarApartado(l.get(8), MAESTROS.get(4), 3, 16, 18, "Redes de computadoras", "3K", 20, Apartado.CONFIRMADO, "");
        sembrarApartado(l.get(5), MAESTROS.get(0), 4, 9, 11, "Ergonomía y seguridad", "4A", 10, Apartado.CANCELADO,
                "El docente avisó que no podría asistir; se liberó el laboratorio.");
    }

    private Catalogo() { }

    private static void sembrar(Edificio e, String nombre, int capacidad, int equipos, boolean mantenimiento,
            String responsable, String software) {
        int n = e.labs.size();
        String clave = e.prefijo + "-" + String.format("%02d", n + 1);
        String planta = n % 2 == 0 ? PLANTAS[0] : PLANTAS[1];
        Laboratorio lab = new Laboratorio(e, clave, nombre, planta, capacidad, equipos, responsable, software,
                CARACTERISTICAS_DEFECTO);
        if (mantenimiento) {
            lab.estado = Laboratorio.MANTENIMIENTO;
            lab.motivo = MOTIVO_DEFECTO;
            lab.reapertura = LocalDate.now().plusDays(10);
            lab.agregarNota("Mantenimiento preventivo programado.");
            lab.agregarNota("Se reportaron fallas en dos equipos.");
        }
        e.labs.add(lab);
    }

    private static void sembrarApartado(Laboratorio lab, Maestro docente, int dias, int inicio, int fin,
            String materia, String grupo, int alumnos, String estado, String anotacion) {
        Apartado a = new Apartado(nuevoFolio(), lab, docente, LocalDate.now().plusDays(dias), inicio, fin, materia,
                grupo, alumnos, "");
        a.estado = estado;
        a.anotacion = anotacion;
        APARTADOS.add(a);
    }

    private static String nuevoFolio() {
        secuenciaFolio++;
        return String.format("AP-%04d", secuenciaFolio);
    }

    // ------------------------------------------------------------------ fechas

    static String fecha(int diasDesdeHoy) {
        return LocalDate.now().plusDays(diasDesdeHoy).format(FECHA);
    }

    static String formato(LocalDate fecha) {
        return fecha.format(FECHA);
    }

    /** Acepta 5/3/2026 y 05/03/2026. Devuelve null si el texto no es una fecha válida. */
    static LocalDate parsearFecha(String texto) {
        if (texto == null || texto.isEmpty()) return null;
        try {
            return LocalDate.parse(texto.trim(), FECHA_ENTRADA);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // --------------------------------------------------------------- edificios

    static int totalEdificios() {
        return EDIFICIOS.size();
    }

    static String nombreEdificio() {
        return EDIFICIOS.get(edificio).nombre;
    }

    static String nombreEdificio(int indice) {
        return EDIFICIOS.get(indice).nombre;
    }

    static List<String> nombresEdificios() {
        List<String> nombres = new ArrayList<>();
        for (Edificio e : EDIFICIOS) nombres.add(e.nombre);
        return nombres;
    }

    static String resumenEdificio(int indice) {
        List<Laboratorio> labs = EDIFICIOS.get(indice).labs;
        int n = labs.size();
        if (n == 0) return "Sin laboratorios registrados todavía";
        StringBuilder s = new StringBuilder(n == 1 ? "1 laboratorio:  " : n + " laboratorios:  ");
        for (int k = 0; k < Math.min(3, n); k++) {
            if (k > 0) s.append("  ·  ");
            s.append(labs.get(k).nombre);
        }
        if (n > 3) s.append("  ·  …");
        return s.toString();
    }

    /** Agrega un edificio. Devuelve un mensaje de error, o null si se agregó. */
    static String agregarEdificio(String nombre) {
        String limpio = nombre == null ? "" : nombre.trim().replaceAll("\\s+", " ");
        if (limpio.isEmpty()) return "Escribe el nombre del edificio.";
        if (limpio.length() > 50) return "El nombre del edificio es demasiado largo (máximo 50 caracteres).";
        for (Edificio e : EDIFICIOS) {
            if (e.nombre.equalsIgnoreCase(limpio)) return "Ya existe un edificio con ese nombre.";
        }
        EDIFICIOS.add(new Edificio(limpio, prefijoUnico(limpio)));
        return null;
    }

    private static String prefijoUnico(String nombre) {
        StringBuilder base = new StringBuilder();
        for (String palabra : nombre.split(" ")) {
            if (palabra.isEmpty() || esPalabraVacia(palabra)) continue;
            char inicial = palabra.charAt(0);
            if (Character.isLetter(inicial)) base.append(Character.toUpperCase(inicial));
            if (base.length() == 3) break;
        }
        if (base.length() == 0) base.append("ED");
        String prefijo = base.toString();
        int contador = 2;
        while (prefijoEnUso(prefijo)) {
            prefijo = base.toString() + contador;
            contador++;
        }
        return prefijo;
    }

    private static boolean esPalabraVacia(String palabra) {
        for (String vacia : PALABRAS_VACIAS) {
            if (vacia.equalsIgnoreCase(palabra)) return true;
        }
        return false;
    }

    private static boolean prefijoEnUso(String prefijo) {
        for (Edificio e : EDIFICIOS) {
            if (e.prefijo.equals(prefijo)) return true;
        }
        return false;
    }

    // ----------------------------------------------------------- laboratorios

    static List<Laboratorio> laboratorios() {
        return laboratorios(edificio);
    }

    static List<Laboratorio> laboratorios(int indiceEdificio) {
        return new ArrayList<>(EDIFICIOS.get(indiceEdificio).labs);
    }

    /** Laboratorios del edificio actual que se pueden usar hoy. */
    static List<Laboratorio> disponibles() {
        return disponiblesEn(LocalDate.now());
    }

    /** Laboratorios del edificio actual que se pueden usar en ese día. */
    static List<Laboratorio> disponiblesEn(LocalDate dia) {
        List<Laboratorio> lista = new ArrayList<>();
        for (Laboratorio lab : laboratorios()) {
            if (!lab.enMantenimientoEn(dia)) lista.add(lab);
        }
        return lista;
    }

    /** Posición del primer laboratorio en mantenimiento del edificio actual, o 0 si no hay. */
    static int indiceDeMantenimiento() {
        List<Laboratorio> labs = laboratorios();
        for (int i = 0; i < labs.size(); i++) {
            if (labs.get(i).enMantenimiento()) return i;
        }
        return 0;
    }

    /** Los laboratorios cuya fecha de reapertura ya llegó vuelven a quedar disponibles. */
    static void reabrirVencidos() {
        LocalDate hoy = LocalDate.now();
        for (Edificio e : EDIFICIOS) {
            for (Laboratorio lab : e.labs) {
                if (lab.enMantenimiento() && lab.reapertura != null && !lab.reapertura.isAfter(hoy)) {
                    lab.estado = Laboratorio.DISPONIBLE;
                    lab.motivo = "";
                    lab.reapertura = null;
                    lab.agregarNota("Volvió a estar disponible (reapertura automática).");
                }
            }
        }
    }

    /** Siguiente clave libre del edificio, por ejemplo NI-10. */
    static String claveSiguiente(int indiceEdificio) {
        Edificio e = EDIFICIOS.get(indiceEdificio);
        int mayor = 0;
        for (Laboratorio lab : e.labs) {
            int guion = lab.clave.lastIndexOf('-');
            String sufijo = guion >= 0 ? lab.clave.substring(guion + 1) : lab.clave;
            try {
                mayor = Math.max(mayor, Integer.parseInt(sufijo));
            } catch (NumberFormatException ex) {
                // clave escrita a mano sin número: no cuenta
            }
        }
        String clave;
        int n = Math.max(mayor, e.labs.size()) + 1;
        do {
            clave = e.prefijo + "-" + String.format("%02d", n);
            n++;
        } while (existeClave(indiceEdificio, clave));
        return clave;
    }

    static boolean existeClave(int indiceEdificio, String clave) {
        for (Laboratorio lab : EDIFICIOS.get(indiceEdificio).labs) {
            if (lab.clave.equalsIgnoreCase(clave)) return true;
        }
        return false;
    }

    static boolean existeNombreLab(int indiceEdificio, String nombre, Laboratorio excepto) {
        for (Laboratorio lab : EDIFICIOS.get(indiceEdificio).labs) {
            if (lab != excepto && lab.nombre.equalsIgnoreCase(nombre)) return true;
        }
        return false;
    }

    /** Crea un laboratorio disponible en el edificio indicado. */
    static Laboratorio agregarLaboratorio(int indiceEdificio, String clave, String nombre, String planta,
            int capacidad, int equipos, String responsable, String software, String caracteristicas) {
        Edificio e = EDIFICIOS.get(indiceEdificio);
        Laboratorio lab = new Laboratorio(e, clave, nombre, planta, capacidad, equipos, responsable, software,
                caracteristicas);
        e.labs.add(lab);
        return lab;
    }

    /**
     * Elimina un laboratorio junto con su historial de apartados.
     * Devuelve un mensaje de error, o null si se eliminó.
     */
    static String eliminarLaboratorio(Laboratorio lab) {
        if (lab == null) return "Selecciona un laboratorio de la tabla.";
        int pendientes = pendientes(lab).size();
        if (pendientes > 0) {
            return "No se puede eliminar: tiene " + textoPendientes(pendientes)
                    + ". Cancélalos primero en Apartados.";
        }
        APARTADOS.removeIf(a -> a.lab == lab);
        lab.edificio.labs.remove(lab);
        return null;
    }

    // ---------------------------------------------------------------- maestros

    static List<Maestro> maestros() {
        return new ArrayList<>(MAESTROS);
    }

    /** Agrega un maestro. Devuelve un mensaje de error, o null si se agregó. */
    static String agregarMaestro(String nombre, String correo, String departamento) {
        String n = nombre == null ? "" : nombre.trim().replaceAll("\\s+", " ");
        String c = correo == null ? "" : correo.trim();
        String d = departamento == null ? "" : departamento.trim();
        if (n.isEmpty()) return "Escribe el nombre del maestro.";
        if (c.isEmpty()) return "Escribe el correo institucional.";
        if (!c.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) return "El correo no es válido. Ejemplo: nombre@dominio.edu.mx";
        for (Maestro m : MAESTROS) {
            if (m.nombre.equalsIgnoreCase(n)) return "Ya existe un maestro con ese nombre.";
            if (m.correo.equalsIgnoreCase(c)) return "Ya existe un maestro con ese correo.";
        }
        MAESTROS.add(new Maestro(n, c, d.isEmpty() ? "Sin asignar" : d));
        return null;
    }

    /** Elimina un maestro. Devuelve un mensaje de error, o null si se eliminó. */
    static String eliminarMaestro(Maestro maestro) {
        if (maestro == null) return "Selecciona un maestro de la tabla.";
        int pendientes = 0;
        for (Apartado a : pendientes()) {
            if (a.docente == maestro) pendientes++;
        }
        if (pendientes > 0) {
            return "No se puede eliminar: tiene " + textoPendientes(pendientes)
                    + ". Cancélalos primero en Apartados.";
        }
        MAESTROS.remove(maestro);
        return null;
    }

    private static String textoPendientes(int n) {
        return n == 1 ? "1 apartado confirmado pendiente" : n + " apartados confirmados pendientes";
    }

    // ---------------------------------------------------------------- apartados

    /** Apartados confirmados de hoy en adelante, de todos los edificios. */
    private static List<Apartado> pendientes() {
        LocalDate hoy = LocalDate.now();
        List<Apartado> lista = new ArrayList<>();
        for (Apartado a : APARTADOS) {
            if (a.confirmado() && !a.fecha.isBefore(hoy)) lista.add(a);
        }
        return lista;
    }

    /** Apartados confirmados de hoy en adelante de un laboratorio. */
    static List<Apartado> pendientes(Laboratorio lab) {
        List<Apartado> lista = new ArrayList<>();
        for (Apartado a : pendientes()) {
            if (a.lab == lab) lista.add(a);
        }
        return lista;
    }

    private static final Comparator<Apartado> POR_FECHA = Comparator
            .comparing((Apartado a) -> a.fecha)
            .thenComparingInt(a -> a.horaInicio)
            .thenComparing(a -> a.folio);

    /** Todos los apartados del edificio actual, por fecha y hora. */
    static List<Apartado> apartados() {
        List<Apartado> lista = new ArrayList<>();
        for (Apartado a : APARTADOS) {
            if (a.lab.edificio == EDIFICIOS.get(edificio)) lista.add(a);
        }
        lista.sort(POR_FECHA);
        return lista;
    }

    /** Apartados confirmados de un laboratorio en un día, por hora. */
    static List<Apartado> apartados(Laboratorio lab, LocalDate dia) {
        List<Apartado> lista = new ArrayList<>();
        for (Apartado a : APARTADOS) {
            if (a.lab == lab && a.confirmado() && a.fecha.equals(dia)) lista.add(a);
        }
        lista.sort(POR_FECHA);
        return lista;
    }

    /** Apartados del edificio actual que siguen vigentes de hoy en adelante. */
    static List<Apartado> proximos() {
        LocalDate hoy = LocalDate.now();
        List<Apartado> lista = new ArrayList<>();
        for (Apartado a : apartados()) {
            if (!Apartado.CANCELADO.equals(a.estado) && !a.fecha.isBefore(hoy)) lista.add(a);
        }
        return lista;
    }

    static int apartadosHoy() {
        LocalDate hoy = LocalDate.now();
        int total = 0;
        for (Apartado a : apartados()) {
            if (a.fecha.equals(hoy) && !Apartado.CANCELADO.equals(a.estado)) total++;
        }
        return total;
    }

    static int alumnosHoy() {
        LocalDate hoy = LocalDate.now();
        int total = 0;
        for (Apartado a : apartados()) {
            if (a.fecha.equals(hoy) && a.confirmado()) total += a.alumnos;
        }
        return total;
    }

    private static Apartado traslapeLab(Laboratorio lab, LocalDate dia, int inicio, int fin) {
        for (Apartado a : APARTADOS) {
            if (a.lab == lab && a.confirmado() && a.traslapa(dia, inicio, fin)) return a;
        }
        return null;
    }

    private static Apartado traslapeDocente(Maestro docente, LocalDate dia, int inicio, int fin) {
        for (Apartado a : APARTADOS) {
            if (a.docente == docente && a.confirmado() && a.traslapa(dia, inicio, fin)) return a;
        }
        return null;
    }

    /** Revisa todas las reglas de un apartado nuevo. Devuelve un mensaje de error, o null si es válido. */
    static String validarApartado(Maestro docente, Laboratorio lab, LocalDate dia, int inicio, int fin,
            String materia, String grupo, Integer alumnos) {
        if (docente == null) return "Elige el docente que solicita.";
        if (lab == null) return "Elige el laboratorio.";
        if (dia == null) return "Elige una fecha válida (dd/mm/aaaa).";
        if (dia.isBefore(LocalDate.now())) return "La fecha no puede ser anterior a hoy.";
        if (inicio < HORA_PRIMERA || fin > HORA_ULTIMA) {
            return "El horario debe estar entre " + Util.horaTexto(HORA_PRIMERA) + " y " + Util.horaTexto(HORA_ULTIMA) + ".";
        }
        if (fin <= inicio) return "La hora de fin debe ser posterior a la de inicio.";
        if (dia.atTime(inicio, 0).isBefore(LocalDateTime.now())) return "La hora de inicio ya pasó.";
        if (materia == null || materia.trim().isEmpty()) return "Escribe la materia o actividad.";
        if (grupo == null || grupo.trim().isEmpty()) return "Escribe el grupo.";
        if (alumnos == null || alumnos < 1) return "Los alumnos deben ser un número entero mayor que 0.";
        if (alumnos > lab.capacidad) {
            return "«" + lab.nombre + "» solo tiene capacidad para " + lab.capacidad + " alumnos.";
        }
        if (lab.enMantenimientoEn(dia)) {
            return "«" + lab.nombre + "» está en mantenimiento en esa fecha"
                    + (lab.reapertura != null ? " (reabre el " + formato(lab.reapertura) + ")." : ".");
        }
        Apartado choque = traslapeLab(lab, dia, inicio, fin);
        if (choque != null) {
            return "El laboratorio ya está apartado de " + choque.horario() + " (" + choque.materia + ", "
                    + choque.docente.nombre + ").";
        }
        Apartado ocupado = traslapeDocente(docente, dia, inicio, fin);
        if (ocupado != null) {
            return docente.nombre + " ya tiene un apartado de " + ocupado.horario() + " en «" + ocupado.lab.nombre + "».";
        }
        return null;
    }

    /** Registra el apartado. Antes hay que haberlo validado con {@link #validarApartado}. */
    static Apartado crearApartado(Maestro docente, Laboratorio lab, LocalDate dia, int inicio, int fin,
            String materia, String grupo, int alumnos, String motivo) {
        Apartado a = new Apartado(nuevoFolio(), lab, docente, dia, inicio, fin, materia.trim(), grupo.trim(),
                alumnos, motivo == null ? "" : motivo.trim());
        if (!a.motivo.isEmpty()) a.anotacion = a.motivo;
        APARTADOS.add(a);
        return a;
    }

    /** Devuelve el motivo por el que no se puede cancelar el apartado, o null si sí se puede. */
    static String validarCancelacion(Apartado a) {
        if (a == null) return "Selecciona un apartado de la tabla.";
        if (!a.confirmado()) return "Solo se pueden cancelar apartados confirmados.";
        if (a.fecha.isBefore(LocalDate.now())) return "No se puede cancelar un apartado de una fecha pasada.";
        return null;
    }

    /** Cancela un apartado. Devuelve un mensaje de error, o null si se canceló. */
    static String cancelarApartado(Apartado a) {
        String error = validarCancelacion(a);
        if (error != null) return error;
        a.estado = Apartado.CANCELADO;
        return null;
    }

    /** Marca que el maestro no llegó. Devuelve un mensaje de error, o null si se marcó. */
    static String marcarNoAsistio(Apartado a) {
        if (a == null) return "Selecciona un apartado de la tabla.";
        if (!a.confirmado()) return "Solo se puede marcar un apartado confirmado.";
        if (a.inicio().isAfter(LocalDateTime.now())) return "El apartado todavía no comienza.";
        a.estado = Apartado.NO_ASISTIO;
        return null;
    }

    /** Apartados confirmados que quedarían dentro del periodo de mantenimiento de un laboratorio. */
    static List<Apartado> afectadosPorMantenimiento(Laboratorio lab, LocalDate reapertura) {
        List<Apartado> lista = new ArrayList<>();
        for (Apartado a : pendientes(lab)) {
            if (reapertura == null || a.fecha.isBefore(reapertura)) lista.add(a);
        }
        lista.sort(POR_FECHA);
        return lista;
    }

    static void cancelarPorMantenimiento(List<Apartado> afectados) {
        for (Apartado a : afectados) {
            a.estado = Apartado.CANCELADO;
            a.anotacion = "Cancelado por mantenimiento del laboratorio.";
        }
    }
}
