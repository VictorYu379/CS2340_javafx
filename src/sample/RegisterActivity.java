package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sample.model.UserItem;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import static sample.Main.windowHeight;
import static sample.Main.windowWidth;

public class RegisterActivity {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label usernameWrong;
    @FXML
    private Label passwordWrong;
    @FXML
    private Label userTypeWrong;
    @FXML
    private Label usernameExist;
    @FXML
    private RadioButton Admin;
    @FXML
    private RadioButton Cat;

    private JSONParser parser = new JSONParser();

    public void doRegister(ActionEvent actionEvent) {
        usernameWrong.setVisible(false);
        passwordWrong.setVisible(false);
        userTypeWrong.setVisible(false);
        usernameExist.setVisible(false);
        String userName = username.getText();
        String passWord = password.getText();

        if (!Pattern.matches(".+@.+\\.com", userName)) {
            usernameWrong.setVisible(true);
            return;
        }
        if (!Pattern.matches(".{6,}+", passWord)) {
            passwordWrong.setVisible(true);
            return;
        }
        if (!Admin.isSelected() && !Cat.isSelected()) {
            userTypeWrong.setVisible(true);
            return;
        }

        try {
            Object obj = parser.parse(new FileReader(Main.UserInformation));

            JSONObject jsonObject = (JSONObject) obj;
            if (jsonObject.get(userName) != null) {
                usernameExist.setVisible(true);
                return;
            }
            JSONObject newUserInformation = new JSONObject();
            newUserInformation.put("Password", passWord);
            if (Admin.isSelected()) {
                newUserInformation.put("User Type", "1");
            }
            jsonObject.put(userName, newUserInformation);

            FileWriter file = new FileWriter(Main.UserInformation);
            file.write(jsonObject.toJSONString());
            file.flush();

            Main.user.setUserItem(
                    new UserItem(userName, null,
                            (Admin.isSelected())));

            Parent root = FXMLLoader.load(getClass().getResource("../res/layout/MapActivity.fxml"));
            Main.stage.setScene(new Scene(root, Main.windowWidth, Main.windowHeight));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void backToMainPage(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../res/layout/sample.fxml"));
        Main.stage.setScene(new Scene(root, windowWidth, windowHeight));
    }
}
