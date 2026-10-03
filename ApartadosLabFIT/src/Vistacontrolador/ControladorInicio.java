package Vistacontrolador;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControladorInicio {

    @FXML private Label lblBienvenida;
    @FXML private Label lblApartadosHoy;
    @FXML private Label lblMaestros;
    @FXML private Label lblLabsDisponibles;
    @FXML private Label lblAlumnosHoy;
    @FXML private TableView<Apartado> tablaProximos;
    @FXML private TableColumn<Apartado, String> colFecha;
    @FXML private TableColumn<Apartado, String> colHorario;
    @FXML private TableColumn<Apartado, String> colLab;
    @FXML private TableColumn<Apartado, String> colMateria;
    @FXML private TableColumn<Apartado, String> colEstado;

    @FXML
    private void initialize() {
        lblBienvenida.setText("Esto es lo que ocurre hoy en " + Catalogo.nombreEdificio() + ".");
        lblApartadosHoy.setText(String.valueOf(Catalogo.apartadosHoy()));
        lblMaestros.setText(String.valueOf(Catalogo.maestros().size()));
        lblLabsDisponibles.setText(Catalogo.disponibles().size() + " / " + Catalogo.laboratorios().size());
        lblAlumnosHoy.setText(String.valueOf(Catalogo.alumnosHoy()));

        Util.columna(colFecha, a -> Catalogo.formato(a.fecha));
        Util.columna(colHorario, a -> a.horario());
        Util.columna(colLab, a -> a.lab.nombre);
        Util.columna(colMateria, a -> a.materia);
        Util.columnaChip(colEstado, a -> a.estadoVisible());

        tablaProximos.setPlaceholder(new Label("No hay apartados próximos en este edificio."));
        tablaProximos.setItems(FXCollections.observableArrayList(Catalogo.proximos()));
    }

    @FXML private void nuevoApartado() { ControladorPrincipal.navegarA("apartar"); }
    @FXML private void verApartados() { ControladorPrincipal.navegarA("apartados"); }
}
