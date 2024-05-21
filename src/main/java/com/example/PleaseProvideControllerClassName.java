package com.example;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
    //private ImageView currentlyFollowing;
    private StackPane currentlyFollowing;
    @FXML
    private Button start;
    
    @FXML
    private Button monkey;
    @FXML
    private Button snag; 
    @FXML
    private Button bananatree; 
    @FXML
    private Button battleship; 
    @FXML
    private Button cannon; 
    @FXML
    private Button boomerange; 
    @FXML
    private Button icemonkey; 
    @FXML
    private Button ninjamonkey; 
    @FXML
    private Button painter; 
    @FXML
    private Button sniper; 
    @FXML
    private Button wizmonkey; 
    @FXML
    private Button supermonkey; 

    @FXML
    private AnchorPane root;

    private static List<tower> towers = new ArrayList<>();
    
    
    @FXML
    private void initialize() {
        // 鼠標移動事件，用於移動所有動態創建的ImageView
        root.setOnMouseMoved(this::handleMouseMoved);
        // 處理鼠標點擊事件
        root.setOnMouseClicked(this::handleMouseClicked);
        
        //start
        start.setOnAction(event -> bloonStart());

        // 為每個按鈕設置事件處理程序
        monkey.setOnAction(event -> handleButtonClick("resouce\\monkey.png", 50, 50));
        snag.setOnAction(event -> handleButtonClick("resouce\\snag.png", 50, 50));
        bananatree.setOnAction(event -> handleButtonClick("resouce\\bananatree.png", 60, 60));
        battleship.setOnAction(event -> handleButtonClick("resouce\\battleship.png", 60, 60));
        cannon.setOnAction(event -> handleButtonClick("resouce\\cannon.png", 50, 50));
        boomerange.setOnAction(event -> handleButtonClick("resouce\\boomerange.png", 50, 50));
        icemonkey.setOnAction(event -> handleButtonClick("resouce\\icemonkey.png", 60, 60));
        ninjamonkey.setOnAction(event -> handleButtonClick("resouce\\ninjamonkey.png", 60, 60)); // 特殊尺寸
        painter.setOnAction(event -> handleButtonClick("resouce\\painter.png", 60, 60));
        sniper.setOnAction(event -> handleButtonClick("resouce\\sniper.png", 60, 80)); // 特殊尺寸
        wizmonkey.setOnAction(event -> handleButtonClick("resouce\\wizmonkey.png", 50, 50));
        supermonkey.setOnAction(event -> handleButtonClick("resouce\\supermonkey.png", 50, 50));
    }

    private int total;
    private List<String> types = new ArrayList<>();
    private List<Integer> amounts = new ArrayList<>();
    private int currentIndex = 0;
    bloon bloons= new bloon();
    private void bloonStart(){
        Scanner scanner;
        try {
            scanner = new Scanner(new File("resouce//stage1.txt"));
            total = scanner.nextInt();
            while (scanner.hasNext()) {
                String type = scanner.next();
                int amount = scanner.nextInt();
                types.add(type);
                amounts.add(amount);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int delays = 8000/total;
        if (!types.isEmpty() && !amounts.isEmpty()) {
            playNextAnimation(root, delays);
        }
    }

    private void playNextAnimation(AnchorPane root, int delay) {
        if (currentIndex < types.size()) {
            String type = types.get(currentIndex);
            int amount = amounts.get(currentIndex);

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(delay), event -> {
                bloons.showBloon(root, type);
            }));
            timeline.setCycleCount(amount);

            timeline.setOnFinished(event -> {
                currentIndex++;
                playNextAnimation(root, delay); // 递归调用，播放下一个动画
            });

            timeline.play();
        }
    }

    tower newTower;
    private void handleButtonClick(String imagePath, double imageWidth, double imageHeight) {

        newTower = new tower(root,imagePath,imageWidth,imageHeight);
        towers.add(newTower);
        double x = monkey.getLayoutX()-100000;
        double y = monkey.getLayoutY();
        
        newTower.placeTower(root, x, y  );
        currentlyFollowing = newTower.getTowerPane();

        System.out.println("Click on button, loading image: " + imagePath);
    }

    private void handleMouseMoved(MouseEvent event) {
        // 確保在UI線程上執行
        if (currentlyFollowing != null) { // 僅當存在正在跟隨的ImageView時更新位置
            Platform.runLater(() -> {
                currentlyFollowing.setLayoutX(event.getX() -newTower.rangeRadius/2);
                currentlyFollowing.setLayoutY(event.getY() -newTower.rangeRadius/2);
                System.out.println(event.getX() + " " + event.getY());
                if (event.getX() > 950 && event.getY() < 50) {
                    currentlyFollowing.setVisible(false);
                    currentlyFollowing = null; // 停止跟隨
                }
            });
        }
    }

    private void handleMouseClicked(MouseEvent event) {

        if (currentlyFollowing != null) { // 检测鼠标左键
            System.out.println("click");
            currentlyFollowing.getChildren().removeAll(newTower.getTowerImageRange());
            currentlyFollowing.setLayoutX(event.getX() -newTower.imagewidth/2);
            currentlyFollowing.setLayoutY(event.getY() -newTower.imageheight/2);
            currentlyFollowing = null; // 停止跟随
        }
        else
        {
            double clickX = event.getX();
            double clickY = event.getY();

            boolean clickedOnTower = false;
            for (tower t : towers) {
                double imageViewX = t.getTowerPane().getLayoutX();
                double imageViewY = t.getTowerPane().getLayoutY();
                double imageViewWidth = t.getTowerImageView().getFitWidth();
                double imageViewHeight = t.getTowerImageView().getFitHeight();

                if(clickX >= imageViewX && clickX <= imageViewX + imageViewWidth &&
                clickY >= imageViewY && clickY <= imageViewY + imageViewHeight){
                    for (tower t2 : towers) {
                        if(t2.getTowerPane().getChildren().contains(t2.getTowerImageRange()))
                        {
                            t2.getTowerPane().getChildren().remove(t2.getTowerImageRange());
                            t2.getTowerPane().setLayoutX(t2.getTowerPane().getLayoutX() + t2.rangeRadius / 2 - t2.imagewidth/2);
                            t2.getTowerPane().setLayoutY(t2.getTowerPane().getLayoutY() + t2.rangeRadius / 2 - t2.imageheight/2);
                            t2.button.setVisible(false);
                        }
                    }
                    clickedOnTower = true;
                    t.button.setVisible(true);
                    if(!t.getTowerPane().getChildren().contains(t.getTowerImageRange()))
                    {
                        t.getTowerPane().getChildren().add(t.getTowerImageRange());
                        t.getTowerImageRange().toBack();
                        t.getTowerImageRange().setVisible(true);
                        t.getTowerPane().setLayoutX(t.getTowerPane().getLayoutX() - t.rangeRadius / 2 + t.imagewidth/2);
                        t.getTowerPane().setLayoutY(t.getTowerPane().getLayoutY() - t.rangeRadius / 2 + t.imageheight/2);
                    }
                    System.out.println("click");
                    break;
                }
                else
                {
                    if(t.getTowerPane().getChildren().contains(t.getTowerImageRange()))
                    {
                        t.getTowerPane().getChildren().remove(t.getTowerImageRange());
                        t.getTowerPane().setLayoutX(t.getTowerPane().getLayoutX() + t.rangeRadius / 2 - t.imagewidth/2);
                        t.getTowerPane().setLayoutY(t.getTowerPane().getLayoutY() + t.rangeRadius / 2 - t.imageheight/2);
                    }
                }
            }
            if (!clickedOnTower) {
                for (tower t : towers) {
                    t.button.setVisible(false);
                }
            }
        }
        

        /*if (newTower.button != null) { // 检测鼠标左键
            newTower.button.setVisible(false);
        }
        if(root.getChildren().contains(tower.button)) {
            tower.button.setVisible(false);
            //root.getChildren().remove(tower.button);
        }*/

    }
    public static void removeTower(tower t) {
        towers.remove(t);
    }
}
