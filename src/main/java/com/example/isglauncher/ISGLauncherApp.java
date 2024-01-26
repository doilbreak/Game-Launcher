package com.example.isglauncher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class ISGLauncherApp extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainActivity.fxml"));
        Parent root = loader.load();
        ISGLauncherController controller = loader.getController();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Image icon = new Image(getClass().getResourceAsStream("Logo.png"));
        primaryStage.getIcons().add(icon);
        Scene mainScene = new Scene(root, 1024, 600);
        mainScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm()); // Stil dosyasını ekleyin
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("ISG Launcher");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
