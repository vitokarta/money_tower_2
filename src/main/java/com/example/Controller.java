package com.example;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imageView = new ImageView();
    private StackPane currentlyFollowing;
    private AnimationTimer timer;

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
    private Label healthLabel;
    @FXML
    private Label moneyLabel;
    @FXML
    private Label roundLabel;

    @FXML
    private AnchorPane root;

    private int health = 500; // 血條初始值
    private int money = 0; // 金錢初始值
    private int round = 1; // 回合初始值
    private final int totalRounds = 40; // 總回合數
    private ImageView target; // 目標物
    private ImageView placedMonkey; // 被放置的猴子

    @FXML
    private void initialize() {
        // 鼠標移動事件，用於移動所有動態創建的ImageView
        root.setOnMouseMoved(this::handleMouseMoved);
        // 處理鼠標點擊事件
        root.setOnMouseClicked(this::handleMouseClicked);

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

        // 初始化血條和目標物
        updateHealthLabel();
        updateMoneyLabel();
        updateRoundLabel();
        initializeTarget();
        // 啟動計時器
        startTimer();
    }

    private void updateHealthLabel() {
        healthLabel.setText(String.valueOf(health));
    }

    private void updateMoneyLabel() {
        moneyLabel.setText("$" + money);
    }

    private void updateRoundLabel() {
        roundLabel.setText("Round:\n" + round + "/" + totalRounds);
    }

    private void initializeTarget() {
        // 初始化目標物(之後要考慮多顆氣球，或是那個點有氣球經過再做氣球類別判斷來扣血)
        target = new ImageView("file:/C:/Users/Edward%20Liao/Desktop/tower/money_tower_2/resouce/bloon.png");
        target.setFitWidth(50);
        target.setFitHeight(50);
        target.setLayoutX(0);
        target.setLayoutY(0);
        root.getChildren().add(target);
    }

    private void startTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                checkTargetPosition();
                rotatePlacedMonkeyTowardsTarget();
            }
        };
        timer.start();
    }

    private void checkTargetPosition() {
        double x = target.getLayoutX();
        double y = target.getLayoutY();

        // 假設特定座標為(500, 300)
        if (x > 500 && y > 300) {
            reduceHealth();
            // 停止定時器以防止多次減少
            timer.stop();
        }

        // 更新目標物的位置（待更新）
        Platform.runLater(() -> {
            target.setLayoutX(x + 1);
            target.setLayoutY(y + 1);
        });
    }

    private void rotatePlacedMonkeyTowardsTarget() {
        if (placedMonkey != null) {
            double deltaX = target.getLayoutX() - placedMonkey.getLayoutX();
            double deltaY = target.getLayoutY() - placedMonkey.getLayoutY();
            double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));

            Platform.runLater(() -> {
                placedMonkey.setRotate(angle);
            });
        }
    }

    private void reduceHealth() {
        if (health > 0) {
            health--; // 血條減少1
            updateHealthLabel(); // 更新顯示的血條數值
        }
    }

    private void increaseMoney() {
        money++; // 金錢增加1
        updateMoneyLabel(); // 更新顯示的金錢數值
    }

    private void advanceRound() {
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            round++; // 回合數增加
            updateRoundLabel(); // 更新顯示的回合數
            resetTarget(); // 重置目標物的位置和狀態
            startTimer(); // 重啟計時器
        });
        pause.play();
    }

    private void resetTarget() {
        // 重置目標物的位置
        target.setLayoutX(0);
        target.setLayoutY(0);
        // 其他重置邏輯（如更新目標物的圖像等）可以在這裡添加
    }

    tower newTower;
    private void handleButtonClick(String imagePath, double imageWidth, double imageHeight) {
        newTower = new tower(root, imagePath, imageWidth, imageHeight);
        double x = monkey.getLayoutX() - 1000;
        double y = monkey.getLayoutY() + 1000;
        newTower.placeTower(root, x, y);
        currentlyFollowing = newTower.getTowerPane();
        placedMonkey = newTower.getTowerImageView(); // 更新 placedMonkey 以旋轉正確的圖像
        System.out.println("Click on button, loading image: " + imagePath);
    }

    private void handleMouseMoved(MouseEvent event) {
        if (currentlyFollowing != null) {
            Platform.runLater(() -> {
                currentlyFollowing.setLayoutX(event.getX() - newTower.rangeRadius / 2);
                currentlyFollowing.setLayoutY(event.getY() - newTower.rangeRadius / 2);
                System.out.println(event.getX() + " " + event.getY());
                if (event.getX() > 950 && event.getY() < 50) {
                    currentlyFollowing.setVisible(false);
                    currentlyFollowing = null;
                }
            });
        }
    }

    private void handleMouseClicked(MouseEvent event) {
        if (currentlyFollowing != null) {
            System.out.println("click");
            newTower.getTowerImageRange().setVisible(false);
            currentlyFollowing.getChildren().removeAll(newTower.getTowerImageRange());
            currentlyFollowing = null;
        }

        if (newTower != null && newTower.button != null) {
            newTower.button.setVisible(false);
        }

        if (target != null && event.getTarget() == target) {
            increaseMoney(); // 當目標物被點擊時增加金錢
        }
    }
}