module org.example.hangmanjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;


    opens org.example.hangmanjavafx to javafx.fxml;
    exports org.example.hangmanjavafx;
    exports org.example.hangmanjavafx.main;
    opens org.example.hangmanjavafx.main to javafx.fxml;
}