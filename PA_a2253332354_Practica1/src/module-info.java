module PA_a2253332354_Practica1 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;

	exports application;
	exports modelo;
	exports vista;
	exports controlador;
	exports utilerias;

	opens application to javafx.graphics, javafx.fxml;
	opens modelo to javafx.base, javafx.fxml;
	opens vista to javafx.graphics, javafx.fxml;
	opens controlador to javafx.graphics, javafx.fxml;
	opens utilerias to javafx.base, javafx.fxml;
}
