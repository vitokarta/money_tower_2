package com.example;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class bloon {
    private static int cun=0;
    private static final Image RED = new Image("file:@..//..//resouce//R.jpg");
    private static final Image BLUE = new Image("file:@..//..//resouce//B.jpg");
    private static final Image GREEN = new Image("file:@..//..//resouce//g.jpg");
    private static final Image YELLOW = new Image("file:@..//..//resouce//y.jpg");
    private static final Image PINK = new Image("file:@..//..//resouce//p.jpg");

    private String type;
    private ImageView imageView;
    private double speed;
    private SVGPath path;
    private PathTransition transition;
    private boolean occurred = false;
    public bloon(String type, AnchorPane root) {


        this.type = type;
        this.imageView = new ImageView(getBloonImage(type));
        this.imageView.toFront();
        this.imageView.setFitWidth(40);
        this.imageView.setFitHeight(40);
        root.getChildren().add(this.imageView);
        

        try {
            Path filePath = Paths.get("resouce//svg.txt");
            String content = Files.readString(filePath);
            this.path = new SVGPath();
            this.path.setContent(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.speed = calculateSpeed(type) * 2;
        startAnimation(root);
    }

    private double newCurrentTime=0;
    private void startAnimation(AnchorPane root) {
        this.transition = new PathTransition(Duration.millis(10000 / this.speed), this.path, this.imageView);
        this.transition.setInterpolator(Interpolator.LINEAR);
        
        //this.transition.jumpTo(Duration.millis(0));
        this.transition.setOnFinished((ActionEvent event) -> {
            root.getChildren().remove(this.imageView);
        });
        this.transition.play();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (checkCollision(imageView, Controller.targetImageView,Controller.targetImageView2) && !occurred) {
                    occurred = true;
                    System.out.println("Collision Detected!");
                    System.out.println(cun++);
                    handleCollision(root,this);
                }
            }
        };
        timer.start();
    }

    private boolean checkCollision(ImageView iv1, ImageView iv2,ImageView iv3) {
        return iv1.getBoundsInParent().intersects(iv2.getBoundsInParent())|| iv1.getBoundsInParent().intersects(iv3.getBoundsInParent());
    }

    private void handleCollision(AnchorPane root, AnimationTimer timer) {
        this.type = bloonDestroy(this.type);
        if (!this.type.equals("0")) {
            //new bloon(nextType, root); // Create a new Bloon with the next type
            changeBloon(this.type, root);
        }
        else{
            timer.stop(); // 停止定时器
            root.getChildren().remove(this.imageView);
        }
                
    }
    private void changeBloon(String newType, AnchorPane root) {
        Image newImage = getBloonImage(newType);
        System.out.println(getBloonImage(newType));
        ImageView newImageView = new ImageView(newImage);
        newImageView.setFitWidth(40);
        newImageView.setFitHeight(40);
        //newImageView.setLayoutX();
        //newImageView.setLayoutY(0);
        root.getChildren().add(newImageView);
        root.getChildren().remove(this.imageView);

        double currentTime = this.transition.getCurrentTime().toMillis();
        double oldTotalDuration = this.transition.getTotalDuration().toMillis();
        double progress = currentTime / oldTotalDuration;

        double newBloonSpeed = calculateSpeed(newType) * 2;
        double newTotalDuration = 10000 / newBloonSpeed;
        this.newCurrentTime = newTotalDuration * progress;

        PathTransition newTransition = new PathTransition(Duration.millis(newTotalDuration), this.path, newImageView);
        newTransition.setInterpolator(Interpolator.LINEAR);
        newTransition.setOnFinished(event -> root.getChildren().remove(newImageView));
        newTransition.jumpTo(Duration.millis(newCurrentTime+200)); // 设置正确的跳转时间
        newTransition.play();

        this.transition.stop();
        this.transition = newTransition;
        this.imageView = newImageView;
        occurred = false;
        
        //startAnimation(root); // Call startAnimation again to reset the AnimationTimer
    }

    private double calculateSpeed(String type) {
        switch (type) {
            case "R": return 1;
            case "B": return 1.4;
            case "G": return 1.8;
            case "Y": return 3.2;
            case "P": return 3.5;
            default: return 1;
        }
    }

    private Image getBloonImage(String type) {
        switch (type) {
            case "R": return RED;
            case "B": return BLUE;
            case "G": return GREEN;
            case "Y": return YELLOW;
            case "P": return PINK;
            default: return null;
        }
    }

    private String bloonDestroy(String type) {
        switch (type) {
            case "R": return "0";
            case "B": return "R";
            case "G": return "B";
            case "Y": return "G";
            case "P": return "Y";
            default: return "0";
        }
    }
}