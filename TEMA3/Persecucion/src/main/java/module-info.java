module org.virosms.persecucion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.virosms.persecucion to javafx.fxml;
    exports org.virosms.persecucion;
}