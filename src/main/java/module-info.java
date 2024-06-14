module code.javacode {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    opens code.javacode to javafx.fxml;
    exports code.javacode;
    
}
