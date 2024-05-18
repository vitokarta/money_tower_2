package com.example;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

public class PleaseProvideControllerClassName {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imageView = new ImageView();
    private ImageView currentlyFollowing;

    @FXML
    private Button monkey;

    @FXML
    private AnchorPane root;

    @FXML
    private void initialize() {
        // 鼠标移动事件，用于移动所有动态创建的ImageView
        root.setOnMouseMoved(this::handleMouseMoved);
        // 处理鼠标点击事件
        root.setOnMouseClicked(this::handleMouseClicked);
    }


    double imageWidth = 50;  // Desired width
    double imageHeight = 50; // Desired height
    @FXML
    void logouts(ActionEvent event) {
        
        Image mapImage = null;
        try {
            mapImage = new Image(new FileInputStream("resouce\\bloon.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        ImageView newImageView = new ImageView();
        //WritableImage image = monkey.snapshot(new SnapshotParameters(), null);
        newImageView.setImage(mapImage);
        newImageView.setFitWidth(imageWidth);
        newImageView.setFitHeight(imageHeight);
        newImageView.setVisible(true);
        newImageView.toFront(); // 确保图像显示在最前
        newImageView.setLayoutX(monkey.getLayoutX() + 50 );  // 设置新图像的初始位置
        newImageView.setLayoutY(monkey.getLayoutY() + 50 );
        //newImageView.add
        root.getChildren().add(newImageView);
        currentlyFollowing = newImageView;
    
        System.out.println("Click on monkey");
    }

    private void handleMouseMoved(MouseEvent event) {
        // 确保在UI线程上执行
        if (currentlyFollowing != null) { // 仅当存在正在跟随的ImageView时更新位置
            Platform.runLater(() -> {
                currentlyFollowing.setLayoutX(event.getX() -imageWidth / 2);
                currentlyFollowing.setLayoutY(event.getY() -imageHeight / 2);
                //- currentlyFollowing.getImage().getWidth() / 2
                //- currentlyFollowing.getImage().getHeight() / 2
                System.out.println(event.getX() + " " + event.getY());
                if(event.getX()>950 && event.getY()<50) {
                    currentlyFollowing.setVisible(false);
                    currentlyFollowing = null; // 停止跟随
                }
            });
        }
    }

    private void handleMouseClicked(MouseEvent event) {

        if (currentlyFollowing != null) { // 检测鼠标左键
            System.out.println("click");
            //currentlyFollowing.setVisible(false);
            currentlyFollowing = null; // 停止跟随
        }
    }
}
