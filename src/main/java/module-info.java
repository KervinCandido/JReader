module com.github.kervincandido {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.github.kervincandido.view to javafx.fxml;
    exports com.github.kervincandido;
}