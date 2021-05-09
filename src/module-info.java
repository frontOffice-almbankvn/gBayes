module gBayes {
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.web;
    requires jsmile;
    requires yfiles;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;
    opens sample.test;
    exports sample.test.GNC;
    opens sample.test.GNC to javafx.base;
}