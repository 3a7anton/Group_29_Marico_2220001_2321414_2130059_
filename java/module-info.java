module org.example.marico_bangladesh {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;

    opens org.example.marico_bangladesh to javafx.fxml;
    opens org.example.marico_bangladesh.inventory_manager to javafx.fxml;
    exports org.example.marico_bangladesh;
    exports org.example.marico_bangladesh.inventory_manager;
}