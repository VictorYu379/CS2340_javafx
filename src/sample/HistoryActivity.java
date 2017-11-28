package sample;

import com.lynden.gmapsfx.GoogleMapView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HistoryActivity implements Initializable {

    @FXML
    protected static ListView<Integer> listView;
    @FXML
    protected VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.getItems().addAll(11464394, 15641584, 31614374, 35927676, 28765083, 36908696, 36910927, 36910928,
                36910929, 36911066, 36911067, 36911108, 36911109, 36911110, 36911128, 36912108, 36976605, 36977168,
                36988835, 37017278, 37015308, 36983191, 36752597);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Main.currentReport = listView.getSelectionModel().getSelectedItem();
                    Stage stage = Main.stage;
                    Parent root = FXMLLoader.load(getClass().getResource("../res/layout/ReportActivity.fxml"));
                    stage.setScene(new Scene(root, Main.windowWidth, Main.windowHeight));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../res/layout/MapActivity.fxml"));
        Main.stage.setScene(new Scene(root, Main.windowWidth, Main.windowHeight));
        Main.stage.show();
    }
}
