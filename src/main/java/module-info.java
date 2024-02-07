module sio.tp4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    //opens sio.tp4.Model;
    opens sio.tp4.Tools;
    opens sio.tp4 to javafx.fxml;
    exports sio.tp4;


}