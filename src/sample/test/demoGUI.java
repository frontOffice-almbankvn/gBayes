package sample.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

import static sample.test.gConnect.testConnection;

public class demoGUI extends Application {
    public static Connection con;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        new smile.License(
                "SMILE LICENSE b720b29f eb50d223 5e4ea873 " +
                        "THIS IS AN ACADEMIC LICENSE AND CAN BE USED " +
                        "SOLELY FOR ACADEMIC RESEARCH AND TEACHING, " +
                        "AS DEFINED IN THE BAYESFUSION ACADEMIC " +
                        "SOFTWARE LICENSING AGREEMENT. " +
                        "Serial #: 29p8tgjuwefzat87lrygsqqhh " +
                        "Issued for: Duy Phuong Truong Nguyen (dpzoro1994@gmail.com) " +
                        "Academic institution: Hanoi university of science and technology " +
                        "Valid until: 2021-10-23 " +
                        "Issued by BayesFusion activation server",
                new byte[] {
                        32,118,101,-114,-90,-10,-43,33,-15,-82,-37,33,26,30,-45,27,
                        16,-71,91,-113,-12,48,-38,-74,56,-44,8,-28,-113,-43,-10,-37,
                        -79,-14,79,50,36,3,-80,34,-10,-48,-62,111,-78,-121,60,-127,
                        76,-127,29,-62,27,-71,-118,-69,81,9,-108,-2,-83,91,-27,22
                }
        );
        con = gConnect.getConnection();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("appication.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            testConnection();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
