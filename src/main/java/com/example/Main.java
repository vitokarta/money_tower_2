package com.example;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {

    

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        // 创建一个新的场景并显示
        //Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        //double windowWidth = Math.min(screenBounds.getWidth(), 800);  // 限制最大寬度為800或更小
        //double windowHeight = Math.min(screenBounds.getHeight(), 600);  // 限制最大高度為600或更小
        //Stage stage = new Stage();
        //stage.show();

        //Group root = new Group();
        //Scene scene = new Scene(root);
        //primaryStage.setScene(scene);
        //primaryStage.show();
        SceneLoader scene = new SceneLoader ();
        SceneLoader.loadScene(primaryStage); // 设置窗口大小为 800x600
    }
}
