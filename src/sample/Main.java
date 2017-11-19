package sample;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.User;

import java.io.File;
import java.io.FileInputStream;

public class Main extends Application {
    static Stage stage;
    static File UserInformation;
    static int windowHeight = 600, windowWidth = 600;
    static User user = User.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception{
        UserInformation = new File("src/res/User Information.json");
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../res/layout/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, windowWidth, windowHeight));
        primaryStage.show();

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream("src/res/Tom and Jerry-241642ae92bd.json")))
                .setDatabaseUrl("https://tom-and-jerry-a4bd1.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
