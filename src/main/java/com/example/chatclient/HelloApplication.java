package com.example.chatclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Client Application");
        stage.setScene(scene);
        HelloController controller = fxmlLoader.getController();
        stage.setOnCloseRequest((e)->{
            try {
                controller.shutDown();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}