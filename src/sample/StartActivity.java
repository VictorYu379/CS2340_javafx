package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StartActivity {
    Stage stage;
    Button btn;

    public void gotoLogin(ActionEvent event) throws IOException {
        stage = Main.stage;
        Parent root = FXMLLoader.load(getClass().getResource("../res/layout/Login.fxml"));
        stage.setScene(new Scene(root, Main.windowWidth, Main.windowHeight));
    }


    public void gotoRegister(ActionEvent event) throws IOException {
        stage = Main.stage;
        Parent root = FXMLLoader.load(getClass().getResource("../res/layout/Register.fxml"));
        stage.setScene(new Scene(root, Main.windowWidth, Main.windowHeight));
    }
}
