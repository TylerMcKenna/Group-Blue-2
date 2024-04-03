module com.example.login {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires jbcrypt;
    requires com.fasterxml.jackson.databind;

    opens com.example.login to javafx.fxml;
    exports com.example.login;
}