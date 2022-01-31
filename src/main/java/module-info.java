module com.example.javaday6handin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.javaday6handin to javafx.fxml;
    exports com.example.javaday6handin;
}