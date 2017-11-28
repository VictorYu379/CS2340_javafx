package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import sample.model.LoadingView;
import sample.model.Report;
import sample.model.ReportManager;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GraphActivity implements Initializable, LoadingView {

    ReportManager reportManager = ReportManager.getInstance();
    AtomicBoolean infoReceived = new AtomicBoolean();
    LoadingView callback = this;
    ArrayList<Report> reports = new ArrayList<>();
    Map<String, Integer> map = new HashMap<>();


    @FXML
    protected LineChart lineChart;
    @FXML
    protected ProgressBar progressBar;
    @FXML
    protected StackPane stackPane;
    @FXML
    protected DatePicker startTime;
    @FXML
    protected DatePicker endTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        infoReceived.set(false);
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Years");

        lineChart.setTitle("Rat Sightings");
    }

    @Override
    public void setUpLoadingView() {
        stackPane.setVisible(true);

    }

    @Override
    public void setDownLoadingView() {
        for (Report report: reports) {
            String key = report.getDate().substring(0, 4);
            if (map.get(key) == null) {map.put(key, 1);}
            else {map.put(key, map.get(key) + 1);}
        }
        infoReceived.set(true);
    }

    public void backToMap(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../res/layout/MapActivity.fxml"));
        Main.stage.setScene(new Scene(root, Main.windowWidth, Main.windowHeight));
        Main.stage.show();
    }

    public void searchDataByDate(ActionEvent actionEvent) {
        setUpLoadingView();
        String start = startTime.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String end = endTime.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        reportManager.getReportsByDate(reports, start, end, callback);
        while (!infoReceived.get()) {}

        progressBar.setProgress(0.8);
        XYChart.Series series = new XYChart.Series();
        series.setName("Years");

        Set<String> keySet = map.keySet();
        List<String> list = new ArrayList<>(keySet);
        Collections.sort(list);
        for (String key : list) {
            series.getData().add(new XYChart.Data(key, map.get(key)));
        }

        lineChart.getData().add(series);
        progressBar.setProgress(1.0);
        stackPane.toBack();
    }
}
