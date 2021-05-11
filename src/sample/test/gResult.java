package sample.test;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.test.GNC.gNetwork;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TransferQueue;

public class gResult implements Initializable {
    @FXML
    private TextArea txtBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtBox.setWrapText(true);
        txtBox.setText(gNetwork.getTextAllPosteriors(Controller.net));
    }
}
