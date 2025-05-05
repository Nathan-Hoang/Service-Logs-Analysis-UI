package com.test_gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    public static Stage primaryStage;
    private static Scene mainScene;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(App.class.getResource("explorer.fxml"));
        mainScene = new Scene(root);
        stage.setScene(mainScene);
        stage.setTitle("Service Log Analyzer");
        stage.setMaximized(true);  // Start maximized
        stage.show();
    }

    public static void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlFile));
            Parent newRoot = loader.load();
            mainScene.setRoot(newRoot);  // Just swap the root
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void switchToDashboard() {
        switchScene("primary.fxml");
    }

    public static void switchToStream() {
        switchScene("stream.fxml");
    }

    public static void switchToExplorer() {
        switchScene("explorer.fxml");
    }
    public static void main(String[] args) {
        launch(args);
    }
}
