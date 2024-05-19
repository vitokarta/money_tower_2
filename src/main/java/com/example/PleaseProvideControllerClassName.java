package com.example;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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
    //private ImageView currentlyFollowing;
    private StackPane currentlyFollowing;

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
    }

    tower newTower;
    private void handleButtonClick(String imagePath, double imageWidth, double imageHeight) {

        newTower = new tower(root,imagePath);
        
        // Set the image width and height as needed
        
        // Place the tower centered at an offset from the monkey button
        double x = monkey.getLayoutX();
        double y = monkey.getLayoutY();
        
        newTower.placeTower(root, x, y);
        //currentlyFollowing = newTower.getTowerImageView();
        currentlyFollowing = newTower.getTowerPane();

        /*Image mapImage = null;
        try {
            mapImage = new Image(new FileInputStream(imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        ImageView newImageView = new ImageView();
        newImageView.setImage(mapImage);
        newImageView.setFitWidth(imageWidth);
        newImageView.setFitHeight(imageHeight);
        newImageView.setVisible(true);
        newImageView.toFront(); // 確保圖像顯示在最前
        newImageView.setLayoutX(monkey.getLayoutX() + 50);  // 設置新圖像的初始位置（根據需要調整）
        newImageView.setLayoutY(monkey.getLayoutX() + 50);
        root.getChildren().add(newImageView);
        currentlyFollowing = newImageView;*/

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
            //currentlyFollowing.setVisible(false);
            newTower.getTowerImageRange().setVisible(false);
            //newTower.getTowerImageRange().toBack();
            currentlyFollowing.getChildren().removeAll(newTower.getTowerImageRange());
            currentlyFollowing = null; // 停止跟随
        }

        if (newTower.button != null) { // 检测鼠标左键
            newTower.button.setVisible(false);
        }


    }
}
