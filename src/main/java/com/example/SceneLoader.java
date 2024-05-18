package com.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneLoader   {
    
    public static void loadScene(Stage primaryStage) {
        // 加载JPG图像文件
        Image mapImage = null;
        try {
            mapImage = new Image(new FileInputStream("file.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // 创建ImageView来显示地图
        //ImageView mapImageView = new ImageView(mapImage);
        //mapImageView.setFitWidth(windowWidth);
        //mapImageView.setFitHeight(windowHeight);
        // 创建栈面板并将ImageView添加到其中


        ImageView mapImageView = new ImageView(mapImage);
        mapImageView.setPreserveRatio(true);

        AnchorPane root = new AnchorPane();
        root.getChildren().add(mapImageView);
        AnchorPane.setTopAnchor(mapImageView, 0.0);
        AnchorPane.setRightAnchor(mapImageView, 150.0);
        AnchorPane.setBottomAnchor(mapImageView, 0.0);
        AnchorPane.setLeftAnchor(mapImageView, 0.0);
        //Group root = new Group();

        // 创建场景并将栈面板添加到场景中
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    System.out.println("Click on escape");
                    primaryStage.close(); // 直接关闭舞台
                }
            }
        });
        
        // 设置舞台标题和场景
        primaryStage.setTitle("Game Map Example");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
