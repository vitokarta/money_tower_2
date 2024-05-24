package com.example;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.shape.Circle;
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
    private Label healthLabel;
    @FXML
    private Label moneyLabel;
    @FXML
    private Label roundLabel;
    @FXML
    private Label costLabel; // 新增的顯示猴子價格的按鈕

    @FXML
    private AnchorPane root;

    private int health = 500; // 血條初始值
    private int money = 2500; // 金錢初始值
    private int round = 1; // 回合初始值
    private int cost = 0;
    private final int totalRounds = 40; // 總回合數
    private ImageView target; // 目標物

    private static List<tower> towers = new ArrayList<>();
    public static List<Projectile> projectiles = new ArrayList<>();
    private ManualMap manualMap = new ManualMap("resouce\\map1.jpg");
    private static Controller instance;

    public Controller() {
        instance = this;
    }

    public static Controller getInstance() {
        return instance;
    }
    @FXML
    private void initialize() {
        // 初始化顯示猴子價格的按鈕

        // 鼠標移動事件，用於移動所有動態創建的ImageView
        root.setOnMouseMoved(this::handleMouseMoved);
        // 處理鼠標點擊事件
        root.setOnMouseClicked(this::handleMouseClicked);

        //start
        start.setOnAction(event -> bloonStart());

        // 為每個按鈕設置事件處理程序
        /*monkey.setOnAction(event -> handleButtonClick("resouce\\monkey.png", 50, 50, monkey));
        snag.setOnAction(event -> handleButtonClick("resouce\\snag.png", 50, 50, snag));
        bananatree.setOnAction(event -> handleButtonClick("resouce\\bananatree.png", 60, 60, bananatree));
        battleship.setOnAction(event -> handleButtonClick("resouce\\battleship.png", 60, 60, battleship));
        cannon.setOnAction(event -> handleButtonClick("resouce\\cannon.png", 50, 50, cannon));
        boomerange.setOnAction(event -> handleButtonClick("resouce\\boomerange.png", 50, 50, boomerange));
        icemonkey.setOnAction(event -> handleButtonClick("resouce\\icemonkey.png", 60, 60, icemonkey));
        ninjamonkey.setOnAction(event -> handleButtonClick("resouce\\ninjamonkey.png", 60, 60, ninjamonkey)); // 特殊尺寸
        painter.setOnAction(event -> handleButtonClick("resouce\\painter.png", 60, 60, painter));
        sniper.setOnAction(event -> handleButtonClick("resouce\\sniper.png", 60, 80, sniper)); // 特殊尺寸
        wizmonkey.setOnAction(event -> handleButtonClick("resouce\\wizmonkey.png", 50, 50, wizmonkey));
        supermonkey.setOnAction(event -> handleButtonClick("resouce\\supermonkey.png", 50, 50, supermonkey));*/
        setButtonHandlers(monkey, "resouce\\monkey.png", 50, 50, 150);
        setButtonHandlers(snag, "resouce\\snag.png", 50, 50, 250);
        setButtonHandlers(bananatree, "resouce\\bananatree.png", 60, 60, 750);
        setButtonHandlers(battleship, "resouce\\battleship.png", 60, 60, 400);
        setButtonHandlers(cannon, "resouce\\cannon.png", 50, 50, 650);
        setButtonHandlers(boomerange, "resouce\\boomerange.png", 50, 50, 350);
        setButtonHandlers(icemonkey, "resouce\\icemonkey.png", 60, 60, 300);
        setButtonHandlers(ninjamonkey, "resouce\\ninjamonkey.png", 60, 60, 300); // 特殊尺寸
        setButtonHandlers(painter, "resouce\\painter.png", 60, 60, 250);
        setButtonHandlers(sniper, "resouce\\sniper.png", 60, 80, 350); // 特殊尺寸
        setButtonHandlers(wizmonkey, "resouce\\wizmonkey.png", 50, 50, 750);
        setButtonHandlers(supermonkey, "resouce\\supermonkey.png", 50, 50, 3000);

        //monkey.setOnMousePressed(event -> handleButtonPress(monkey));
       // monkey.setOnMouseReleased(event -> handleButtonRelease(monkey));

        // 初始化血條和目標物
        updateHealthLabel();
        updateMoneyLabel();
        updateRoundLabel();
        updateCostLabel();
        initializeTarget();
        // 啟動計時器
        startTimer();
    }
    private void setButtonHandlers(Button button, String imagePath, double imageWidth, double imageHeight, int cost) {
        button.setOnAction(event -> handleButtonClick(imagePath, imageWidth, imageHeight, button));
        button.setOnMousePressed(event -> updateButtonStyle(button, money < cost, true));
        button.setOnMouseReleased(event -> updateButtonStyle(button, money < cost, false));
    }
    
    
    //bloon start
    private int total;
    private List<String> types = new ArrayList<>();
    private List<Integer> amounts = new ArrayList<>();
    private int currentIndex = 0;
    bloon bloons= new bloon();
    private void bloonStart(){
        //bloons.Bloon_Generate(root);
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
        int delays = 6000/total;
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



    private void updateHealthLabel() {
        healthLabel.setText(String.valueOf(health));
    }

    private void updateMoneyLabel() {
        moneyLabel.setText("$" + money);
        updateAllButtonStyles();
    }

    private void updateRoundLabel() {
        roundLabel.setText("Round:\n" + round + "/" + totalRounds);
    }
    private void updateCostLabel() {
        costLabel.setText("Cost:" + cost);
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
                rotateAllMonkeysTowardsTarget();
                updateAllProjectiles();
                rotateAllProjectilesTowardsTarget();
                //checkAndShootBullets();
            }
        };
        timer.start();
    }
    private void updateAllProjectiles() {
        double targetX = target.getLayoutX();
        double targetY = target.getLayoutY();
        
        updateProjectile(root, targetX+target.getFitWidth()/2, targetY +target.getFitHeight()/2);
        
    }
    public void updateProjectile(AnchorPane root, double targetX, double targetY) {
        Iterator<Projectile> iterator = projectiles.iterator();
        while (iterator.hasNext()) {
			//System.out.println("123");
            Projectile projectile = iterator.next();
            projectile.move();
            if (projectile.isRemoved()) {
                root.getChildren().remove(projectile.getProjectileImageView());
                iterator.remove();
            }
        }
        for(tower t : towers){
            t.attackDelayCounter++;
            if (t.isTargetInRange(targetX, targetY)) {
                t.shoot(root, targetX, targetY);
            }
        }
        /* */
    }


    

    /*private void checkAndShootBullets() {

        for (tower t : towers) {
            double towerCenterX = t.getTowerPane().getLayoutX() + t.getTowerImageView().getFitWidth() / 2;
            double towerCenterY = t.getTowerPane().getLayoutY() + t.getTowerImageView().getFitHeight() / 2;
            double targetCenterX = target.getLayoutX() + target.getFitWidth() / 2;
            double targetCenterY = target.getLayoutY() + target.getFitHeight() / 2;

            double distance = Math.sqrt(Math.pow(targetCenterX - towerCenterX, 2) + Math.pow(targetCenterY - towerCenterY, 2));
            
            if (distance <= t.rangeRadius) {
                t.shootBullet(targetCenterX, targetCenterY, root);
            }
        }
    }*/
    private void checkTargetPosition() {
        double x = target.getLayoutX();
        double y = target.getLayoutY();
        double speed = 1.0; // 目标物移动的速度
    
        // 更新目标物的位置
        Platform.runLater(() -> {
            // Z字形路径
            if (x < 200 && y == 0) {  // 向右移动
                target.setLayoutX(x + speed);
            } else if (x >= 200 && y < 150) {  // 向下移动
                target.setLayoutY(y + speed);
            } else if (x > 0 && y >= 150 && y < 300) {  // 向左移动
                target.setLayoutX(x - speed);
            } else if (x == 0 && y >= 150 && y < 300) {  // 向下移动
                target.setLayoutY(y + speed);
            } else if (x < 300 && y == 300) {  // 向右移动
                target.setLayoutX(x + speed);
            } else if (x >= 300 && y < 450) {  // 向下移动
                target.setLayoutY(y + speed);
            } else if (x > 100 && y >= 450) {  // 向左移动
                target.setLayoutX(x - speed);
            } else if (x <= 100 && y >= 450 && y < 600) {  // 向下移动
                target.setLayoutY(y + speed);
            }
        });
    }
    

    private void rotateAllMonkeysTowardsTarget() {
        for (tower t : towers) {
            if (!t.towerType.equals("Snag") && !t.towerType.equals("Banana Tree")) {
            t.rotateTowards(target.getLayoutX()+target.getFitWidth()/2, target.getLayoutY()+target.getFitHeight()/2);
            }
        }
    }
    
    private void rotateAllProjectilesTowardsTarget() {
        for (Projectile p : projectiles) {
            p.rotateTowards(target.getLayoutX()+target.getFitWidth()/2, target.getLayoutY()+target.getFitHeight()/2);
        }
    }

    private void reduceHealth() {
        if (health > 0) {
            health--; // 血條減少1
            updateHealthLabel(); // 更新顯示的血條數值
        }
    }

    private void increaseMoney() {
        money += 500; // 金錢增加1aa
        updateMoneyLabel(); // 更新顯示的金錢數值
        updateAllButtonStyles();
    }
    public void increaseMoneyByAmount(int amount) {
        money += amount;
        updateMoneyLabel();
        updateAllButtonStyles();
    }
    public void decreaseMoneyByAmount(int amount) {
        money -= amount;
        updateMoneyLabel();
        updateAllButtonStyles();
    }
    public void showMonkeyCost(String monkeyName, int cost) {
        String message = String.format("%s\nCost: %d", monkeyName, cost);
        costLabel.setText(message);
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

    private void updateButtonStyle(Button button, boolean showOverlay, boolean isPressed) {
        if (showOverlay) {
            button.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5);"); // 50%透明的红色
        } else {
            if (isPressed) {
                button.setStyle("-fx-background-color: #d18b6a; -fx-background-radius: 10px;"); // 深咖啡色
            } else {
                button.setStyle("-fx-background-color: #eac3a3; -fx-background-radius: 10px;"); // 咖啡色
            }
        }
    }
    private void updateAllButtonStyles() {
        updateButtonStyle(monkey, money < 150, false);
        updateButtonStyle(snag, money < 250, false);
        updateButtonStyle(bananatree, money < 750, false);
        updateButtonStyle(battleship, money < 400, false);
        updateButtonStyle(cannon, money < 650, false);
        updateButtonStyle(boomerange, money < 350, false);
        updateButtonStyle(icemonkey, money < 300, false);
        updateButtonStyle(ninjamonkey, money < 300, false);
        updateButtonStyle(painter, money < 250, false);
        updateButtonStyle(sniper, money < 350, false);
        updateButtonStyle(wizmonkey, money < 750, false);
        updateButtonStyle(supermonkey, money < 3000, false);
    }
    
    tower newTower;
    private void handleButtonClick(String imagePath, double imageWidth, double imageHeight, Button button) {
        tower tempTower = new tower(root, imagePath, imageWidth, imageHeight);
        if (money >= tempTower.costValue) {
            newTower = tempTower;
            towers.add(newTower);
            double x = monkey.getLayoutX() - 100000;
            double y = monkey.getLayoutY();
            newTower.placeTower(root, x, y);
            currentlyFollowing = newTower.getTowerPane();
            showMonkeyCost(newTower.towerType, newTower.costValue);
            System.out.println("Click on button, loading image: " + imagePath);
            updateButtonStyle(button, false,false);
        } else {
            System.out.println("Not enough money to place this tower.");
            updateButtonStyle(button, true,false);
        }
    }

    private void handleMouseMoved(MouseEvent event) {
        // 確保在UI線程上執行
        if (currentlyFollowing != null) { // 僅當存在正在跟隨的ImageView時更新位置
            Platform.runLater(() -> {
                currentlyFollowing.setLayoutX(event.getX() -newTower.rangeRadius/2);
                currentlyFollowing.setLayoutY(event.getY() -newTower.rangeRadius/2);
                if(manualMap.isPositionPlaceable(newTower.towerType,(int)event.getX(), (int) event.getY()))
                    newTower.switchToImage2();
                else
                    newTower.switchToImage3();
                System.out.println(event.getX() + " " + event.getY());
                if (event.getX() > 950 && event.getY() < 50) {
                    towers.remove(newTower);
                    newTower.button.setVisible(false);
                    root.getChildren().remove(newTower.getTowerPane());
                    increaseMoneyByAmount(newTower.costValue);
                    currentlyFollowing = null; // 停止跟隨
                }
                updateMoneyLabel();
            });
            
        }
    }

    private void handleMouseClicked(MouseEvent event) {
        if (target != null && event.getTarget() == target) {
            increaseMoney(); // 當目標物被點擊時增加金錢
        }
        if (currentlyFollowing != null) { // 检测鼠标左键
            System.out.println("click");
            if(manualMap.isPositionPlaceable(newTower.towerType,(int)event.getX(), (int) event.getY()))
            {
                newTower.button.setVisible(false);
                currentlyFollowing.getChildren().removeAll(newTower.getTowerImageRange());
                currentlyFollowing.setLayoutX(event.getX() -newTower.imagewidth/2);
                currentlyFollowing.setLayoutY(event.getY() -newTower.imageheight/2);
                newTower.setPlaced(true);
                currentlyFollowing = null; // 停止跟随

                for(int y=0;y<newTower.imageheight;y++)
                {
                    for(int x=0;x<newTower.imagewidth;x++)
                    {
                        int y1=(int)event.getY()-(int)newTower.imageheight/2+y;
                        int x1=(int)event.getX()-(int)newTower.imagewidth/2+x;
                        if(y1>0&&x1>0&&y1<manualMap.grid.length&&x1<manualMap.grid[0].length)
                            manualMap.grid[y1][x1] =0;
                    }
                }
            }
            
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
                        t.button.toFront();
                    }
                    System.out.println("click");
                    break;
                }
                else
                {
                    if(t.getTowerPane().getChildren().contains(t.getTowerImageRange()))
                    {
                        //newTower.setPlaced(true);
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

    }
    public static void removeTower(tower t) {
        towers.remove(t);
    }
}
