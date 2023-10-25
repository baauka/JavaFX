module com.example.project2fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project2fx to javafx.fxml;
    exports com.example.project2fx;
}