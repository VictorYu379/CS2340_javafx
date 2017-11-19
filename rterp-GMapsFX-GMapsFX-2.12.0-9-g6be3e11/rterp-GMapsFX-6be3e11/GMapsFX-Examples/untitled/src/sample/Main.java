package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application {

    Thread curr;

    @Override
    public void start(Stage primaryStage) {
        curr = Thread.currentThread();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                Random rdm = new Random();
                int duration = rdm.nextInt(4000);
                System.out.println("Starting....");

                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(curr.getName());
                System.out.println("Finished.");
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}