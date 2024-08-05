module com.itgroup.foetida {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires jdk.jshell;

    opens com.itgroup.foetida to javafx.fxml;
    exports com.itgroup.foetida;

    exports com.itgroup.foetida.application to javafx.graphics;
    opens com.itgroup.foetida.controller to javafx.fxml;
    opens com.itgroup.foetida.application to javafx.fxml;
    opens com.itgroup.foetida.bean to javafx.base;
}