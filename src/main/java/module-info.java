module com.uni.labor6_2021_2022_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.uni.labor6_2021_2022_javafx to javafx.fxml;
    exports com.uni.labor6_2021_2022_javafx;
    exports com.uni.labor6_2021_2022_javafx.application;
    opens com.uni.labor6_2021_2022_javafx.application to javafx.fxml;
}