/*package com.example;

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

    public int basic_speed = 2;

    private Image redBloon = new Image("file:@..//..//resouce//R.jpg");
    private Image blueBloon = new Image("file:@..//..//resouce//b.jpg");
    private Image greenBloon = new Image("file:@..//..//resouce//g.jpg");
    private Image yellowBloon = new Image("file:@..//..//resouce//y.jpg");
    private Image pinkBloon = new Image("file:@..//..//resouce//p.jpg");

    private boolean occurred = false;
    private double bloonSpeed(String type){
        if (type.equals("R")) return 1;
        if (type.equals("B")) return 1.4;
        if (type.equals("G")) return 1.8;
        if (type.equals("Y")) return 3.2;
        if (type.equals("P")) return 3.5;
        else return 1;
    }

    public void showBloon(AnchorPane anchorPane, String type){
        //Image newimage = new Image("resouce//R.jpg");
        ImageView bloonImageView;
        bloonImageView = new ImageView(bloonImg(type));
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

        double bloon_speed = bloonSpeed(type) * basic_speed;

        anchorPane.getChildren().addAll(bloonImageView);        
        PathTransition pathTransition = new PathTransition(Duration.millis(15000/bloon_speed), svg,bloonImageView);
        pathTransition.setOnFinished((ActionEvent event) -> {
            anchorPane.getChildren().remove(bloonImageView); // 从 AnchorPane 中移除 ImageView
        });
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.play();
    

        //目標圖片測試
        ImageView targetImageView;
        try {
            Image target_img = new Image(new FileInputStream("resouce//sidebar.jpg"));
            targetImageView = new ImageView(target_img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        /*targetImageView.setFitHeight(200);
        targetImageView.setFitWidth(200);;
        targetImageView.setLayoutX(500);// 设置目标图片的X位置
        targetImageView.setLayoutY(500); // 设置目标图片的Y位置
        anchorPane.getChildren().addAll(targetImageView);  
        AnimationTimer timer = new AnimationTimer() {  //碰到目標圖片消失
            public void handle(long now) {
                if (checkCollision(bloonImageView, targetImageView, anchorPane)&& occurred == false) {
                    occurred = true;
                    if (bloonDestroy(type).equals("0")) {
                        anchorPane.getChildren().remove(bloonImageView); // Remove the ImageView
                        this.stop();
                    }else{
                        Image img = bloonImg(bloonDestroy(type));
                        ImageView new_image = new ImageView(img);
                        Double currentTime = pathTransition.getCurrentTime().toMillis();
                        pathTransition.stop();
                        Double new_bloon_speed = bloonSpeed(bloonDestroy(type)) * basic_speed;
                        // 創建新的 PathTransition，從當前位置開始
                        pathTransition = new PathTransition(Duration.millis(15000/new_bloon_speed), svg, new_image);
                        pathTransition.setInterpolator(Interpolator.LINEAR);
                        pathTransition.setOnFinished((ActionEvent event) -> {
                            anchorPane.getChildren().remove(new_image); // 從 AnchorPane 中移除 ImageView
                        });
                        pathTransition.setInterpolator(Interpolator.LINEAR);
                        // 設置新的動畫從當前位置開始
                        pathTransition.jumpTo(Duration.millis(currentTime));
                        pathTransition.play();

                    }
                }
            }
        };
        timer.start();
    }
    
    //判斷是否消失
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

    private String bloonDestroy(String type){
        switch (type) {
            case "R":
                return "0";
            case "B":
                return "R";
            case "G":
                return "B";
            case "Y":
                return "G";
            case "P":
                return "Y";
            default:
                return "0";
        }
    }

    private Image bloonImg(String type){
        switch (type) {
            case "R":
                return redBloon;
            case "B":
                return blueBloon;
            case "G":
                return greenBloon;
            case "Y":
                return yellowBloon;
            case "P":
                return pinkBloon;
            default:
                return null;
        }
    }
}
*/

package com.example;

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

public class bloon_copy {

    public int basic_speed = 2;

    private Image redBloon = new Image("file:@..//..//resouce//R.jpg");
    private Image blueBloon = new Image("file:@..//..//resouce//b.jpg");
    private Image greenBloon = new Image("file:@..//..//resouce//g.jpg");
    private Image yellowBloon = new Image("file:@..//..//resouce//y.jpg");
    private Image pinkBloon = new Image("file:@..//..//resouce//p.jpg");

    private boolean occurred = false; // 标志变量，用于记录是否已经发生碰撞

    private ImageView targetImageView;
    bloon_copy(AnchorPane anchorPane){
        // 目标图片测试
        try {
            Image target_img = new Image(new FileInputStream("resouce//bloon.png"));
            targetImageView = new ImageView(target_img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        targetImageView.setFitHeight(70);
        targetImageView.setFitWidth(70);
        targetImageView.setLayoutX(550); // 设置目标图片的X位置
        targetImageView.setLayoutY(550); // 设置目标图片的Y位置
        targetImageView.setOpacity(0.5);
        anchorPane.getChildren().add(targetImageView);
    }
    private double bloonSpeed(String type){
        switch (type) {
            case "R": return 1;
            case "B": return 1.4;
            case "G": return 1.8;
            case "Y": return 3.2;
            case "P": return 3.5;
            default: return 1;
        }
    }
    ImageView bloonImageView2;
    public void showBloon(AnchorPane anchorPane, String type){
        ImageView bloonImageView = new ImageView(bloonImg(type));
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

        double bloon_speed = bloonSpeed(type) * basic_speed;

        anchorPane.getChildren().addAll(bloonImageView);        
        //PathTransition pathTransition = new PathTransition(Duration.millis(15000 / bloon_speed), svg, bloonImageView);
        PathTransition pathTransition = new PathTransition(Duration.millis(10000/ bloon_speed), svg, bloonImageView);
        pathTransition.setOnFinished((ActionEvent event) -> {
            anchorPane.getChildren().remove(bloonImageView); // 从 AnchorPane 中移除 ImageView
        });
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.play();

        

        // 使用 AnimationTimer 来检测碰撞
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //System.out.println(checkCollision(bloonImageView, targetImageView)+" "+ occurred);
                if (checkCollision(bloonImageView, targetImageView) && !occurred) {
                    System.out.println("Collision Detected!");
                    occurred = true;
                    handleCollision(anchorPane, bloonImageView, pathTransition, type, svg, targetImageView, this);
                }
            }
        };
        timer.start();
    }

    private void handleCollision(AnchorPane anchorPane, ImageView bloonImageView, PathTransition pathTransition, String type, SVGPath svg, ImageView targetImageView, AnimationTimer timer) {
        switch (type) {
            case "R":
                anchorPane.getChildren().remove(bloonImageView); // Remove the ImageView
                occurred = false;
                timer.stop(); // 停止定时器
                break;
            case "B":
                changeBloon(anchorPane, bloonImageView, pathTransition, "R", svg, targetImageView, timer);
                break;
            case "G":
                changeBloon(anchorPane, bloonImageView, pathTransition, "B", svg, targetImageView, timer);
                break;
            case "Y":
                changeBloon(anchorPane, bloonImageView, pathTransition, "G", svg, targetImageView, timer);
                break;
            case "P":
                changeBloon(anchorPane, bloonImageView, pathTransition, "Y", svg, targetImageView, timer);
                break;
            default:
                break;
        }
    }

    public int cun=0;
    private void changeBloon(AnchorPane anchorPane, ImageView oldImageView, PathTransition oldPathTransition, String newType, SVGPath svg, ImageView targetImageView, AnimationTimer timer) {
        Image newImage = bloonImg(newType);
        ImageView newImageView = new ImageView(newImage);
        newImageView.setFitWidth(40);
        newImageView.setFitHeight(40);
        anchorPane.getChildren().add(newImageView);
        anchorPane.getChildren().remove(oldImageView);


        double currentTime = oldPathTransition.getCurrentTime().toMillis();
        double oldTotalDuration = oldPathTransition.getTotalDuration().toMillis();
        System.out.println(cun++);
        double progress = currentTime / oldTotalDuration;
    
        double newBloonSpeed = bloonSpeed(newType) * basic_speed;
        double newTotalDuration = 10000 / newBloonSpeed;
        double newCurrentTime = newTotalDuration * progress;

        //double currentTime = oldPathTransition.getCurrentTime().toMillis();
        //double newBloonSpeed = bloonSpeed(newType) * basic_speed;
        //PathTransition newPathTransition = new PathTransition(Duration.millis(15000 / newBloonSpeed), svg, newImageView);
        PathTransition newPathTransition = new PathTransition(Duration.millis(10000/newBloonSpeed), svg, newImageView);
        newPathTransition.setInterpolator(Interpolator.LINEAR);
        newPathTransition.setOnFinished(event -> anchorPane.getChildren().remove(newImageView));
        newPathTransition.jumpTo(Duration.millis(newCurrentTime));
        //newPathTransition.jumpTo(Duration.millis(currentTime));
        //newPathTransition.jumpTo(Duration.millis(8000));

        newPathTransition.play();

        // 重置定时器
        occurred = false;
        timer.start();
    }
    private boolean checkCollision(ImageView iv1, ImageView iv2) {
        return iv1.getBoundsInParent().intersects(iv2.getBoundsInParent());
        
    }

    private Image bloonImg(String type){
        switch (type) {
            case "R":
                return redBloon;
            case "B":
                return blueBloon;
            case "G":
                return greenBloon;
            case "Y":
                return yellowBloon;
            case "P":
                return pinkBloon;
            default:
                return null;
        }
    }
}
