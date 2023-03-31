module com.example.snakecomponent {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.snakecomponent to javafx.fxml;
    exports com.example.snakecomponent;
}