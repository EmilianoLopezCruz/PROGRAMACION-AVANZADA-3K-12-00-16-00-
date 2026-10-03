package Vistacontrolador;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.StringConverter;

final class Util {

    private static final String TUTORIAL =
            "1. Elige el edificio que vas a administrar, o agrega uno nuevo. Cada edificio se puede renombrar (R) o eliminar (Supr) si sus laboratorios no tienen apartados pendientes.\n"
            + "2. En Apartar laboratorio registra el apartado de un docente: laboratorio, fecha y horario. "
            + "El sistema no deja apartar un laboratorio ocupado, en mantenimiento o con más alumnos que su capacidad.\n"
            + "3. En Disponibilidad consulta qué laboratorios están libres cada día; en Apartados busca, cancela, "
            + "anota observaciones o marca cuando el maestro no llegó.\n"
            + "4. En Laboratorios agrega, edita, elimina o pon en mantenimiento cada espacio.\n"
            + "5. En Maestros agrega docentes, edita sus datos o elimina los que ya no se necesiten.\n"
            + "6. En Reportes consulta el uso de los laboratorios por periodo y expórtalo a un archivo de texto.\n\n"
            + "Atajos: las teclas con el número abren el menú (o el edificio, si hay más de nueve se escriben los dos dígitos), ↑ ↓ se mueven, → entra a la vista y Esc regresa al menú.";

    private Util() { }

    static <T> void columna(TableColumn<T, String> col, Function<T, String> valor) {
        col.setCellValueFactory(c -> new ReadOnlyStringWrapper(valor.apply(c.getValue())));
    }

    static <T> void columnaChip(TableColumn<T, String> col, Function<T, String> valor) {
        columna(col, valor);
        col.setCellFactory(c -> new TableCell<T, String>() {
            @Override
            protected void updateItem(String texto, boolean vacio) {
                super.updateItem(texto, vacio);
                if (vacio || texto == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }
                Label chip = new Label(texto);
                chip.getStyleClass().addAll("chip", claseChip(texto));
                setGraphic(chip);
                setText(null);
            }
        });
    }

    private static String claseChip(String texto) {
        switch (texto) {
            case Apartado.CONFIRMADO:
            case Laboratorio.DISPONIBLE:
                return "chip-confirmado";
            case Apartado.REALIZADO:
                return "chip-realizado";
            case Laboratorio.MANTENIMIENTO:
                return "chip-mant";
            case Apartado.NO_ASISTIO:
                return "chip-noasistio";
            default:
                return "chip-cancelado";
        }
    }

    static StringConverter<LocalDate> convertidorFecha() {
        return new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate fecha) {
                return fecha == null ? "" : Catalogo.formato(fecha);
            }

            @Override
            public LocalDate fromString(String texto) {
                return texto == null || texto.trim().isEmpty() ? null : Catalogo.parsearFecha(texto);
            }
        };
    }

    static int hora(String texto) {
        return texto == null ? -1 : Integer.parseInt(texto.substring(0, 2));
    }

    static String horaTexto(int hora) {
        return String.format("%02d:00", hora);
    }

    static void estilizar(Dialog<?> dialogo) {
        dialogo.getDialogPane().getStylesheets().add(Util.class.getResource("/Vista/estilos.css").toExternalForm());
    }

    static void mensaje(Alert.AlertType tipo, String titulo, String texto) {
        Alert alerta = new Alert(tipo, texto, ButtonType.OK);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        estilizar(alerta);
        alerta.showAndWait();
    }

    static boolean confirmar(String titulo, String encabezado, String texto) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, texto, ButtonType.OK, ButtonType.CANCEL);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        estilizar(alerta);
        Optional<ButtonType> respuesta = alerta.showAndWait();
        return respuesta.isPresent() && respuesta.get() == ButtonType.OK;
    }

    static void tutorialBreve() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION, TUTORIAL, ButtonType.OK);
        alerta.setTitle("¿Cómo funciona?");
        alerta.setHeaderText("Guía rápida del sistema");
        estilizar(alerta);
        alerta.showAndWait();
    }

    static void mostrarMensaje(Label etiqueta, String texto, boolean exito) {
        etiqueta.getStyleClass().removeAll("mensaje-ok", "mensaje-error");
        etiqueta.getStyleClass().add(exito ? "mensaje-ok" : "mensaje-error");
        etiqueta.setText(texto);
        etiqueta.setVisible(true);
        etiqueta.setManaged(true);
    }

    static void ocultarMensaje(Label etiqueta) {
        etiqueta.setText("");
        etiqueta.setVisible(false);
        etiqueta.setManaged(false);
    }

    static Integer entero(String texto) {
        if (texto == null) return null;
        try {
            return Integer.valueOf(texto.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
