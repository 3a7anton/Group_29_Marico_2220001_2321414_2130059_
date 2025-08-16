module org.example.marico_bangladesh {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.base;
    requires java.desktop;
    requires jdk.jfr;

    opens org.example.marico_bangladesh to javafx.fxml;
    opens org.example.marico_bangladesh.HR.hr_officer to javafx.fxml;
    opens org.example.marico_bangladesh.HR.hr_assistant to javafx.fxml;
    opens org.example.marico_bangladesh.FIN.Sales to javafx.fxml;
    opens org.example.marico_bangladesh.FIN.Business_analytics to javafx.fxml;
    opens org.example.marico_bangladesh.INVENTORY to javafx.fxml;
    
    exports org.example.marico_bangladesh;
    exports org.example.marico_bangladesh.HR.hr_officer;
    exports org.example.marico_bangladesh.HR.hr_assistant;
    exports org.example.marico_bangladesh.FIN.Sales;
    exports org.example.marico_bangladesh.FIN.Business_analytics;
    exports org.example.marico_bangladesh.INVENTORY;
}