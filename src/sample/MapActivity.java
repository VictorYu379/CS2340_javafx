package sample;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import com.sun.org.apache.regexp.internal.RE;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.model.LoadingView;
import sample.model.Report;
import sample.model.ReportManager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MapActivity implements LoadingView, Initializable, MapComponentInitializedListener {
    ReportManager reportManager;
    ArrayList<Report> reports = new ArrayList<>();
    LatLong latLong1;
    LatLong latLong2;
    AtomicBoolean infoReceived = new AtomicBoolean();

    protected GoogleMap map;

    protected DirectionsService directionsService;
    protected DirectionsPane directionsPane;

    protected StringProperty from = new SimpleStringProperty();
    protected StringProperty to = new SimpleStringProperty();
    protected DirectionsRenderer directionsRenderer = null;

    protected Thread fxThread;

    protected LoadingView callback = this;

    @FXML
    protected GoogleMapView mapView;
    @FXML
    protected VBox vBox;
    @FXML
    protected DatePicker startDate;
    @FXML
    protected DatePicker endDate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reportManager = ReportManager.getInstance();
        mapView.addMapInializedListener(this);
        vBox.setVisible(true);
    }

    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();
        latLong1 = new LatLong(50.1234, -90.2345);
        latLong2 = new LatLong(51.1234, -91.2345);

        options.center(new LatLong(40.7128, -74.0060))
                .zoomControl(true)
                .zoom(12)
                .overviewMapControl(false)
                .mapType(MapTypeIdEnum.ROADMAP);
        map = mapView.createMap(options);
    }

    @Override
    public void setUpLoadingView() {
        map.clearMarkers();
    }

    @Override
    public void setDownLoadingView() {
        infoReceived.set(true);
    }

    public void doSearch(ActionEvent actionEvent) {
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();
        if (start.compareTo(end) > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The end date should be after the start date.");
            alert.showAndWait();
            return;
        }
        setUpLoadingView();
        String start_date = startDate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String end_date = endDate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        reportManager.getReportsByDate(reports, start_date, end_date, callback);
        while (!infoReceived.get()) {}
        Iterator it = reports.iterator();
        while(it.hasNext()) {
            Report report = (Report)it.next();
            try {
                LatLong latLng = new LatLong(Double.parseDouble(report.getLatitude()), Double.parseDouble(report.getLongitude()));
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                options.title(String.valueOf(report.getKey()) + "\n" + report.getDescription());
                Marker marker = new Marker(options);
                map.addMarker(marker);
            } catch (NumberFormatException e) {
                return;
            }
        }
    }

    public void goToGraph(ActionEvent actionEvent) throws IOException {
        Stage stage = Main.stage;
        Parent root = FXMLLoader.load(getClass().getResource("../res/layout/Graph.fxml"));
        stage.setScene(new Scene(root, Main.windowWidth, Main.windowHeight));
    }

    public void goToHistory(ActionEvent actionEvent) throws IOException {
        Stage stage = Main.stage;
        Parent root = FXMLLoader.load(getClass().getResource("../res/layout/History.fxml"));
        stage.setScene(new Scene(root, Main.windowWidth, Main.windowHeight));
    }

    public void goToReport(ActionEvent actionEvent) throws IOException {
        Stage stage = Main.stage;
        Parent root = FXMLLoader.load(getClass().getResource("../res/layout/NewReportActivity.fxml"));
        stage.setScene(new Scene(root, Main.windowWidth, Main.windowHeight));
    }
}
