module org.example.marico_bangladesh {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.marico_bangladesh to javafx.fxml;
    exports org.example.marico_bangladesh;
}