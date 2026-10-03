package Vistacontrolador;

import java.time.LocalDate;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;

public class ControladorDisponibilidad {

    @FXML private Label lblSubtitulo;
    @FXML private DatePicker dpDia;
    @FXML private GridPane cuadricula;

    @FXML
    private void initialize() {
        lblSubtitulo.setText("Qué laboratorios de " + Catalogo.nombreEdificio() + " están libres en cada hora del día.");
        dpDia.setConverter(Util.convertidorFecha());
        dpDia.valueProperty().addListener((obs, anterior, nuevo) -> construir());
        dpDia.setValue(LocalDate.now());
    }

    private Label celda(String texto, String... clases) {
        Label l = new Label(texto);
        l.getStyleClass().addAll(clases);
        l.setAlignment(Pos.CENTER);
        return l;
    }

    private void construir() {
        cuadricula.getChildren().clear();
        LocalDate dia = dpDia.getValue();
        if (dia == null) dia = LocalDate.now();

        cuadricula.add(celda("Laboratorio", "celda-encabezado", "celda-lab"), 0, 0);
        for (int h = Catalogo.HORA_PRIMERA; h < Catalogo.HORA_ULTIMA; h++) {
            cuadricula.add(celda(Util.horaTexto(h), "celda-encabezado"), h - Catalogo.HORA_PRIMERA + 1, 0);
        }

        List<Laboratorio> labs = Catalogo.laboratorios();
        if (labs.isEmpty()) {
            Label vacio = celda("Este edificio todavía no tiene laboratorios registrados.", "celda-lab");
            vacio.setAlignment(Pos.CENTER_LEFT);
            vacio.setMinWidth(380);
            cuadricula.add(vacio, 0, 1, 6, 1);
            return;
        }
        for (int fila = 0; fila < labs.size(); fila++) {
            Laboratorio lab = labs.get(fila);
            Label nombre = celda(lab.nombre, "celda-lab");
            nombre.setAlignment(Pos.CENTER_LEFT);
            nombre.setWrapText(true);
            cuadricula.add(nombre, 0, fila + 1);

            boolean mantenimiento = lab.enMantenimientoEn(dia);
            List<Apartado> apartados = Catalogo.apartados(lab, dia);
            for (int h = Catalogo.HORA_PRIMERA; h < Catalogo.HORA_ULTIMA; h++) {
                Label c;
                Apartado ocupante = null;
                for (Apartado a : apartados) {
                    if (a.traslapa(dia, h, h + 1)) {
                        ocupante = a;
                        break;
                    }
                }
                if (mantenimiento) {
                    c = celda("Mant.", "celda", "celda-mant");
                    if (!lab.motivo.isEmpty()) Tooltip.install(c, new Tooltip(lab.motivo));
                } else if (ocupante != null) {
                    c = celda("Ocupado", "celda", "celda-ocupada");
                    Tooltip.install(c, new Tooltip(ocupante.materia + " (" + ocupante.grupo + ")\n"
                            + ocupante.docente.nombre + "\n" + ocupante.horario()));
                } else {
                    c = celda("Libre", "celda", "celda-libre");
                }
                cuadricula.add(c, h - Catalogo.HORA_PRIMERA + 1, fila + 1);
            }
        }
    }
}
