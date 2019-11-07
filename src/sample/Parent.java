package sample;

import app.util.GlobalLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Parent {
    public static class Child {

        @FXML
        private Label label;

        @FXML
        private Button okButton;


        @FXML
        private void initialize() {
            System.out.println("initialize " + getClass().getName());
            System.out.println("label =  " + label);
            okButton.setText("OKOK");
        }

        @FXML
        private void handleOkButton(){
            GlobalLogger.logger.debug("handleOkButton");
        }
    }
}