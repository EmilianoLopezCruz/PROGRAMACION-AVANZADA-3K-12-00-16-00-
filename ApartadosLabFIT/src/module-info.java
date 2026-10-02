module ApartadosLabFIT {
    requires javafx.controls;
    requires javafx.fxml;
    opens application to javafx.graphics;
    opens Vistacontrolador to javafx.fxml;
}
