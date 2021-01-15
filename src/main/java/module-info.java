module com.github.kervincandido {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.github.kervincandido.controller to javafx.fxml;
    exports com.github.kervincandido;
}