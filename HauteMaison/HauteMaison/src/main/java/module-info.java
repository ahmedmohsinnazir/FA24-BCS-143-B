module example.com.creepsproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens example.com.hautemaison to javafx.fxml;
    exports example.com.hautemaison;
}