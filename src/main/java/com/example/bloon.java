package com.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class bloon {

    public int health;
    public int width;
    public int height;
    public int Speed;

    
    /*public void readStage(AnchorPane anchorPane ,String stage_path) throws FileNotFoundException{
        Scanner scanner = new Scanner(new File(stage_path));
        int total = scanner.nextInt();
        System.out.println(total);
        String type;
        int amount;
        //scanner.next();
        while (scanner.hasNext()) {
            type = scanner.next();
            amount = scanner.nextInt();
            //System.out.println(type + " " + amount);
            Bloon_Generate(anchorPane, type, amount);
            while (Bloon_Generate(anchorPane, type, amount) == false) {
                
            }
        }

    }*/

    public void showBloon(AnchorPane anchorPane, String type){
        ImageView bloonImageView;
        Image img;
        String bloonfile = "resouce//" + type + ".jpg";
        try {
            img = new Image(new FileInputStream(bloonfile));
            bloonImageView = new ImageView(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ;
        }
        bloonImageView.toFront();
        bloonImageView.setFitWidth(40);
        bloonImageView.setFitHeight(40);
        SVGPath svg = new SVGPath();
        Path filePath = Paths.get("resouce//svg.txt");
        String content;
        try {
            content = Files.readString(filePath);
            svg.setContent(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        anchorPane.getChildren().addAll(bloonImageView);        
        //到終點消失
        PathTransition pathTransition = new PathTransition(Duration.millis(5000), svg,bloonImageView);
        pathTransition.setOnFinished((ActionEvent event) -> {
            anchorPane.getChildren().remove(bloonImageView); // 从 AnchorPane 中移除 ImageView
        });
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.play();
    }

    public void Bloon_Generate(AnchorPane anchorPane, String b_type, int amount){
        //set image
        /*ImageView bloonImageView;
        Image img;
        String bloonfile = "resouce//" + b_type + ".jpg";
        int delay = 6000 / amount;
        try {
            img = new Image(new FileInputStream(bloonfile));
            bloonImageView = new ImageView(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ;
        }
        bloonImageView.toFront();
        bloonImageView.setFitWidth(40);
        bloonImageView.setFitHeight(40);
        for(int i = 0; i < amount; i++){
            showBloon(anchorPane, bloonImageView);
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            showBloon(anchorPane, bloonImageView);
        }));
        timeline.setCycleCount(3); // Set the number of iterations
        timeline.play();*/

        //目標圖片測試
        /*ImageView targetImageView;
        try {
            Image target_img = new Image(new FileInputStream("resouce//sidebar.jpg"));
            targetImageView = new ImageView(target_img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        targetImageView.setFitHeight(200);
        targetImageView.setFitWidth(200);;
        targetImageView.setLayoutX(500);// 设置目标图片的X位置
        targetImageView.setLayoutY(500); // 设置目标图片的Y位置
        anchorPane.getChildren().addAll(bloonImageView, targetImageView);*/    

        

        /*AnimationTimer timer = new AnimationTimer() {  //碰到目標圖片消失
            public void handle(long now) {
                if (checkCollision(bloonImageView, targetImageView, anchorPane)) {
                    anchorPane.getChildren().remove(bloonImageView); // Remove the ImageView
                    this.stop(); // Stop the timer
                }
            }
        };
        timer.start();*/
    }
    //判斷是否消失
    /*private boolean checkCollision(ImageView bloonImageView,ImageView targetImageView, AnchorPane anchorPane) {
        for (javafx.scene.Node node : anchorPane.getChildren()) {
            if (node instanceof ImageView && node == targetImageView) {
                if (bloonImageView.getBoundsInParent().intersects(targetImageView.getBoundsInParent())) {
                    return true;
                }
            }
        }
        return false;
    }*/
}
