module ApartadosLabFIT {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
    opens application to javafx.graphics;
    opens Vistacontrolador to javafx.fxml;
}
