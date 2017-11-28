package sample;

import com.google.firebase.database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NewReportActivity {

    int currentID;

    @FXML
    protected DatePicker dateFill;
    @FXML
    protected TextField zipFill;
    @FXML
    protected TextArea addressFill;
    @FXML
    protected TextField boroughFill;
    @FXML
    protected TextField typeFill;


    public void submitNewReport(ActionEvent actionEvent) throws IOException {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("IDcounter").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentID = (int)(long) dataSnapshot.child("counter").getValue();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        String zip = zipFill.getText();
        String address = addressFill.getText();
        String borough = boroughFill.getText();
        String type = typeFill.getText();
        try {
            if (!zip.equals("") && !address.equals("") && !borough.equals("") && !type.equals("")) {
                currentID++;
                if (dateFill.getValue().compareTo(LocalDate.now()) > 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid date.");
                    alert.showAndWait();
                } else {
                    String date = dateFill.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                    mDatabase.child("IDcounter").child("counter").setValueAsync(currentID);
                    mDatabase.child("Entries").child(""+currentID).child("Created Date").setValueAsync(date);
                    mDatabase.child("Entries").child(""+currentID).child("Borough").setValueAsync(borough);
                    mDatabase.child("Entries").child(""+currentID).child("City").setValueAsync("NEW YORK");
                    mDatabase.child("Entries").child(""+currentID).child("Incident Address").setValueAsync(address);
                    mDatabase.child("Entries").child(""+currentID).child("Incident Zip").setValueAsync(zip);
                    mDatabase.child("Entries").child(""+currentID).child("Location Type").setValueAsync(type);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your submission is successful.");
                    alert.showAndWait();
                    Parent root = FXMLLoader.load(getClass().getResource("../res/layout/MapActivity.fxml"));
                    Main.stage.setScene(new Scene(root, Main.windowWidth, Main.windowHeight));
                    Main.stage.show();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new NullPointerException("Please enter valid content.");
        } catch (IOException e) {
            throw e;
        }
    }

    public void backToMap(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../res/layout/MapActivity.fxml"));
        Main.stage.setScene(new Scene(root, Main.windowWidth, Main.windowHeight));
        Main.stage.show();
    }
}
