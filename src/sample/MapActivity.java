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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import sample.model.LoadingView;
import sample.model.Report;
import sample.model.ReportManager;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class MapActivity implements LoadingView, Initializable, MapComponentInitializedListener {
    ReportManager reportManager;
    ArrayList<Report> reports = new ArrayList<>();
    LatLong latLong1;
    LatLong latLong2;
    boolean infoReceived;

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
    @FXML
    protected ProgressIndicator progressIndicator;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reportManager = ReportManager.getInstance();
        mapView.addMapInializedListener(this);
        vBox.setVisible(true);
        fxThread = Thread.currentThread();
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

    }

    @Override
    public void setDownLoadingView() {
//        Iterator it = reports.iterator();
//        while(it.hasNext()) {
//            System.out.println("haha");
//            Report report = (Report)it.next();
//            Double latitude = Math.round(Double.parseDouble(report.getLatitude()) * 10000.0) / 10000.0;
//            Double longitude = Math.round(Double.parseDouble(report.getLongitude()) * 10000.0) / 10000.0;
//            LatLong latLng;
//            try {
//                latLng = new LatLong(latitude, longitude);
//                MarkerOptions options = new MarkerOptions();
//                options.position(latLng);
//                Marker marker = new Marker(options);
//                map.addMarker(marker);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        infoReceived = true;
    }

    public void doSearch(ActionEvent actionEvent) {
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();
        if (start.compareTo(end) > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The end date should be after the start date.");
            alert.showAndWait();
            return;
        }
        String start_date = startDate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String end_date = endDate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//        reportManager.getReportsByDate(reports, start_date, end_date, callback);
//        while (!infoReceived) {}
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<ArrayList<Report>> future = executor.submit(new Callable<ArrayList<Report>>() {
            @Override
            public ArrayList<Report> call() throws Exception {
                reportManager.getReportsByDate(reports, start_date, end_date, callback);
                Thread.sleep(2000);
                return reports;
            }
        });
        executor.shutdown();
        try {
            reports = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(reports.size());
        System.out.println(Thread.currentThread().getName());
        setUpLoadingView();
        map.clearMarkers();
        Iterator it = reports.iterator();
        while(it.hasNext()) {
            System.out.println("haha");
            Report report = (Report)it.next();
            LatLong latLng = new LatLong(Double.parseDouble(report.getLatitude()), Double.parseDouble(report.getLongitude()));
            MarkerOptions options = new MarkerOptions();
            options.position(latLng);
            options.title(String.valueOf(report.getKey()));
//            options.label(report.getDescription());
            Marker marker = new Marker(options);
            map.addMarker(marker);

//            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
//            infoWindowOptions.content("<h2>Fred Wilkie</h2>"
//                    + "Current Location: Safeway<br>"
//                    + "ETA: 45 minutes" );
//            InfoWindow fredWilkeInfoWindow = new InfoWindow(infoWindowOptions);
//            fredWilkeInfoWindow.open(map, marker);
        }
    }
}
