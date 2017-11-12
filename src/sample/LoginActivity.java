package sample;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.google.firebase.internal.Log;
import com.google.firebase.internal.NonNull;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sample.model.User;
import sample.model.UserItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static sample.Main.windowHeight;
import static sample.Main.windowWidth;

public class LoginActivity {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label usernameWrong;
    @FXML
    private Label passwordWrong;

    public void doLogin(ActionEvent actionEvent) {
        usernameWrong.setVisible(false);
        passwordWrong.setVisible(false);
        String userName = username.getText();
        String passWord = password.getText();

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(Main.UserInformation));

            JSONObject jsonObject = (JSONObject) obj;
            if (jsonObject.get(userName) == null) {
                usernameWrong.setVisible(true);
                return;
            }
            if (!((String)((JSONObject) jsonObject.get(userName)).get("Password")).trim().equals(passWord)) {
                passwordWrong.setVisible(true);
                return;
            }

            JSONArray msg = (JSONArray) ((JSONObject) jsonObject.get(userName)).get("Report History");
            ArrayList<String> reports = new ArrayList<>();
            if (msg != null) {
                reports.addAll(msg);
            }

            Main.user.setUserItem(
                    new UserItem(userName, reports,
                            ((JSONObject) jsonObject.get(userName)).get("User Type") != null));

            Parent root = FXMLLoader.load(getClass().getResource("../res/layout/MapActivity.fxml"));
            Main.stage.setScene(new Scene(root, Main.windowWidth, Main.windowHeight));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("haha");
        }

    }

    public void backToMainPage(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../res/layout/sample.fxml"));
        Main.stage.setScene(new Scene(root, windowWidth, windowHeight));
    }
}
