module org.virosms.pratica05 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.virosms.pratica05 to javafx.fxml;
    exports org.virosms.pratica05;
}