package com.example;


import javafx.animation.Animation;
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
import java.io.IOException;
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
    @FXML
    private ImageView HomeButton; 
    @FXML
    private ImageView HomeButton2; 
    private StackPane currentlyFollowing;
    private AnimationTimer timer;

    @FXML
    private ImageView start;
    
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
    private Button snagtower; 
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
    private Label EndLabel; // 新增的顯示猴子價格的按鈕
    @FXML
    private Label EndLabel2; // 新增的顯示猴子價格的按鈕

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane game_over;
    @FXML
    private AnchorPane game_over2;

    public static int health = 100; // 血條初始值
    public static int money = 5000; // 金錢初始值
    private int round = 0; // 回合初始值
    private int cost = 0;
    private int totalRounds = 40; // 總回合數
    SoundPlayer game;

    private static List<tower> towers = new ArrayList<>();
    public static List<Projectile> projectiles = new ArrayList<>();
    public static List<bloon> bloons = new ArrayList<>();
    private List<String> levels = new ArrayList<>();
    private ManualMap manualMap = new ManualMap(stage_menu.manualmap);
    private static Controller instance;

    public Controller() {
        instance = this;
    }

    public static Controller getInstance() {
        return instance;
    }
    @FXML
    private void initialize() {
        // play music
        game = new SoundPlayer("resouce\\sound\\Title Music.wav");
        game.play(true);
        //讀關卡
        try {
            //Scanner scanner = new Scanner(new File("resouce//stage1.txt"));
            Scanner scanner = new Scanner(new File(stage_menu.stageBloon));
            totalRounds = scanner.nextInt();
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                levels.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 鼠標移動事件，用於移動所有動態創建的ImageView
        root.setOnMouseMoved(this::handleMouseMoved);
        // 處理鼠標點擊事件
        root.setOnMouseClicked(this::handleMouseClicked);

        //start
        start.setOnMouseClicked(event -> bloonStart());

        // 為每個按鈕設置事件處理程序
        
        setButtonHandlers(monkey, "resouce\\monkey.png", 50, 50, 150);
        setButtonHandlers(snag, "resouce\\snag.png", 50, 50, 250);
        setButtonHandlers(bananatree, "resouce\\bananatree.png", 60, 60, 750);
        setButtonHandlers(battleship, "resouce\\battleship.png", 60, 60, 400);
        setButtonHandlers(cannon, "resouce\\cannon.png", 50, 50, 650);
        setButtonHandlers(boomerange, "resouce\\boomerange.png", 50, 50, 350);
        setButtonHandlers(snagtower, "resouce\\snagtower.png", 60, 60, 300);
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
        //initializeTarget();
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
    private int currentStage = 0;
    private void bloonStart(){
        //types.clear();
        //amounts.clear();
        Scanner stage_scanner = new Scanner(levels.get(currentStage));
        if (stage_scanner.hasNextInt()) {
            total = stage_scanner.nextInt();
        }

        while (stage_scanner.hasNext()) {
            String type = stage_scanner.next();
            if (stage_scanner.hasNextInt()) {
                int amount = stage_scanner.nextInt();
                types.add(type);
                amounts.add(amount);
            }
        }
        if (currentStage <= levels.size()){
            currentStage++;
            round++;
            updateRoundLabel();
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
                bloons.add(new bloon(type, root,0));
            }));
            timeline.setCycleCount(amount);

            timeline.setOnFinished(event -> {
                PauseTransition pause = new PauseTransition(Duration.millis(delay));
                    pause.setOnFinished(e -> {
                        playNextAnimation(root, delay);
                    });
                    pause.play();
            });
            currentIndex++;
            timeline.playFrom(Duration.millis(delay -1)); 
        }
        else {
            if(round==totalRounds)
                checkWin();
        }
    }



    private void updateHealthLabel() {
        if(health<=0)   
        {
            game_over.setVisible(true);
            game_over.toFront();
            EndLabel.setText("Round:" + round);
            HomeButton.setOnMouseClicked(event -> {
            try {
                resetGame();
                Main.setRoot("menu");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        }
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

    private void startTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //checkTargetPosition();
                //rotateAllMonkeysTowardsTarget();
                updateAllProjectiles();
                updateHealthLabel();
                for (tower t : towers) {
                    if(t.bulletIsPlaced == true){ 
                        if (t.towerType.equals("Banana Tree")) {
                            t.shootBananas(root);
                        } 
                        else if(t.towerType.equals("SnagTower")){
                            t.shootBananas(root);
                        }
                    }
                }
            }
        };
        timer.start();
    }
    private void updateAllProjectiles() {
        List<Projectile> toRemove = new ArrayList<>();
        for(Projectile p : projectiles){
            p.move();
            p.checkForCollision(bloons,root);
            if(p.isRemoved()) {
                root.getChildren().remove(p.getProjectileImageView());
                toRemove.add(p);
            }
        }
        projectiles.removeAll(toRemove);
        for(tower t : towers){
            t.attackDelayCounter++;
            t.checkIfCanAddProjectile(root,bloons);
            }
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
        updateButtonStyle(snagtower, money < 300, false);
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
            //System.out.println("Click on button, loading image: " + imagePath);
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
                //System.out.println(event.getX() + " " + event.getY());
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
        if (currentlyFollowing != null) { // 检测鼠标左键
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

    }
    public static void removeTower(tower t) {
        towers.remove(t);
    }
    public static void removeBloon(bloon b) {
        bloons.remove(b);
    }

    
    public void checkWin() {
        final Timeline[] checkTimeline = new Timeline[1];
        checkTimeline[0] = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            if (bloons.isEmpty()) {
                if (health > 0) {
                    game_over2.setVisible(true);
                    game_over2.toFront();
                    EndLabel2.setText("Round:" + round);
                    HomeButton2.setOnMouseClicked(mouseEvent -> {
                        try {
                            resetGame();
                            Main.setRoot("menu");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    checkTimeline[0].stop();
                }
            }
        }));
        checkTimeline[0].setCycleCount(Animation.INDEFINITE);
        checkTimeline[0].play();
    }

    private void resetGame() {
        // 停止动画计时器
        if (timer != null) {
            timer.stop();
        }
    
        // 停止音乐
        if (game != null) {
            game.stop(); // 假设 SoundPlayer 类有一个 stop 方法
        }
        
        // 清除所有塔和气球
        for(bloon t:bloons)
        {
            t.transition.stop();
        }
        towers.clear();
        bloons.clear();
        projectiles.clear();
    
        // 移除所有UI组件
        root.getChildren().clear();
    
        // 重置金钱、健康、回合等
        health = 100;
        money = 5000;
        round = 0;
    
        // 更新标签显示
        updateHealthLabel();
        updateMoneyLabel();
        updateRoundLabel();
        updateCostLabel();
    }
}

