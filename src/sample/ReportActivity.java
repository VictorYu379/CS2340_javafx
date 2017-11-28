package sample;

import com.sun.org.apache.regexp.internal.RE;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.model.LoadingView;
import sample.model.Report;
import sample.model.ReportManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReportActivity implements Initializable, LoadingView {

    Report current;
    ReportManager reportManager = ReportManager.getInstance();
    LoadingView callback = this;
    AtomicBoolean loaded = new AtomicBoolean();

    @FXML
    protected Label keyLabel;
    @FXML
    protected Label dateLabel;
    @FXML
    protected Label zipLabel;
    @FXML
    protected Label addressLabel;
    @FXML
    protected Label cityLabel;
    @FXML
    protected Label typeLabel;
    @FXML
    protected StackPane stackPane;
    @FXML
    protected ProgressBar progressBar;
    @FXML
    protected AnchorPane anchorPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loaded.set(false);
        setUpLoadingView();
        ArrayList<Report> report = new ArrayList<>();
        reportManager.getReportsByKey(report, Main.currentReport, callback);
        while (!loaded.get()) {}
        progressBar.setProgress(1.0);
        progressBar.setVisible(false);
        anchorPane.toFront();
        current = report.remove(0);
        keyLabel.setText(String.valueOf(Main.currentReport));
        dateLabel.setText(current.getDate());
        zipLabel.setText((String) current.getZip());
        addressLabel.setText((String) current.getAddress());
        cityLabel.setText((String) current.getCity());
        typeLabel.setText((String) current.getLocationType());
    }

    @Override
    public void setUpLoadingView() {
        stackPane.toFront();
    }

    @Override
    public void setDownLoadingView() {
        loaded.set(true);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        Stage stage = Main.stage;
        Parent root = FXMLLoader.load(getClass().getResource("../res/layout/History.fxml"));
        stage.setScene(new Scene(root, Main.windowWidth, Main.windowHeight));
    }
}
