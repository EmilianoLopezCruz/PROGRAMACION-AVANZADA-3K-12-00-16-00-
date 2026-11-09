module PA_a2253332354_Practica3 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;

	exports application;
	exports controlador;
	exports modelo;
	exports utilerias;
	exports vista;

	opens application to javafx.graphics, javafx.fxml;
	opens controlador to javafx.fxml;
	opens modelo to javafx.base, javafx.fxml;
	opens vista to javafx.graphics, javafx.fxml;
}
