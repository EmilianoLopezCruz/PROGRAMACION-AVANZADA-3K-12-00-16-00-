package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ControladorPractica01_01_b {

	@FXML
	private TabPane contentPane;
	@FXML
	private Tab tab1;
	@FXML
	private AnchorPane panelPestana1;
	@FXML
	private Tab tab2;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private Tab tab3;
	@FXML
	private Pane desktopPane;
	@FXML
	private Tab tab4;
	@FXML
	private Pane panelContenedorInterno;
	@FXML
	private TitledPane internalFrame;
	@FXML
	private Button btnCerrarInterna;

	@FXML
	private void onToggleInternalFrame(ActionEvent event) {
		internalFrame.setExpanded(!internalFrame.isExpanded());
	}
}
