package com.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class bloon {
    private AnchorPane root;

    public String towerType;
    public int health;
    public int width;
    public int height;
    public int Speed;

    
    /*public Bloon(){
        
    }*/
    public void Bloon_Generate(AnchorPane anchorPane){
        //set image
        ImageView bloonImageView;
        Image img;
        try {
            img = new Image(new FileInputStream("resouce//bloon.png"));
            bloonImageView = new ImageView(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        //set svg
        SVGPath svg = new SVGPath();
        Path filePath = Paths.get("resouce//svg.txt");
        String content;
        try {
            content = Files.readString(filePath);
            svg.setContent(content);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        bloonImageView.setVisible(true);
        bloonImageView.toFront();
        bloonImageView.setFitWidth(40);
        bloonImageView.setFitHeight(40);
        anchorPane.getChildren().addAll(bloonImageView);
       // root.getChildren().add(bloonImageView);
        

        PathTransition pathTransition = new PathTransition(Duration.millis(5000), svg,bloonImageView);
        pathTransition.setOnFinished((ActionEvent event) -> {
            anchorPane.getChildren().remove(bloonImageView); // 从 AnchorPane 中移除 ImageView
        });
        pathTransition.setInterpolator(Interpolator.LINEAR);
        //PathTransition pathTransition2 = new PathTransition(Duration.millis(5000), svg,bloonImageView);
        pathTransition.play();

        ImageView targetImageView = new ImageView(new Image("resouce//sidebar.jpg"));
        //targetImageView.setFitHeight(200);
        //targetImageView.setFitWidth(200);;
        
        anchorPane.getChildren().addAll(targetImageView);
        targetImageView.setLayoutX(800);// 设置目标图片的X位置
        targetImageView.setLayoutY(800); // 设置目标图片的Y位置

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                if (checkCollision(bloonImageView, targetImageView, anchorPane)) {
                    anchorPane.getChildren().remove(bloonImageView); // Remove the ImageView
                    this.stop(); // Stop the timer
                }
            }
        };
        timer.start();
    }

    private boolean checkCollision(ImageView bloonImageView,ImageView targetImageView, AnchorPane anchorPane) {
        for (javafx.scene.Node node : anchorPane.getChildren()) {
            if (node instanceof ImageView && node == targetImageView) {
                if (bloonImageView.getBoundsInParent().intersects(targetImageView.getBoundsInParent())) {
                    return true;
                }
            }
        }
        return false;
    }
}
