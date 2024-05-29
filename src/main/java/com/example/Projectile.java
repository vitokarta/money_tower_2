package com.example;
import javafx.util.Duration;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javax.swing.ImageIcon;
import javax.swing.plaf.TreeUI;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.util.*;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.event.*;
import java.awt.geom.*;


public class Projectile {
    public ImageView projectileImageView;
    public double speed;
    public double directionX;
    public double directionY;
    public int attack = 2;
    public int health;
    public double distanceTravelled;
    public double rangeRadius;
    public boolean isRemoved = false;
    public tower tower;
    private String towerType;
    private boolean isBanana;
    private long creationTime;
    private long lifespan = 10000; // 香蕉存在时间为2000毫秒（2秒）
    public int durability=2;
    private SoundPlayer ceramicBloonHit = new SoundPlayer("resouce\\sound\\Ceramic_damage.wav");
	private SoundPlayer pop = new SoundPlayer("resouce\\sound\\Bloon_Pop.wav");
    // constructor contains angle
    public Projectile(AnchorPane root, tower tower, double angle, int speed, int rangeRadius, boolean isBanana) {
        this.tower = tower;
        this.towerType = tower.towerType;
        this.isBanana = isBanana;
        String imagePath = isBanana && (tower.towerType == "Banana Tree") ? "resouce\\bullet\\banana.png" : getProjectileImagePath();
        try {
            Image image = new Image(new FileInputStream(imagePath));
            projectileImageView = new ImageView(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Point2D projectileSize = getProjectileSize();
        double projectileWidth = projectileSize.getX();
        double projectileHeight = projectileSize.getY();
        projectileImageView.setFitWidth(projectileWidth);
        projectileImageView.setFitHeight(projectileHeight);

        tower.getStartCoordinates();
        double startX = tower.x;
        double startY = tower.y;
        projectileImageView.setLayoutX(startX - projectileWidth / 2);
        projectileImageView.setLayoutY(startY - projectileHeight / 2);
        
        root.getChildren().add(projectileImageView);

        this.speed = speed;
        this.rangeRadius = rangeRadius;
        this.directionX = Math.cos(Math.toRadians(angle - 90));
        this.directionY = Math.sin(Math.toRadians(angle- 90));
        distanceTravelled = 0;
        this.creationTime = System.currentTimeMillis();
        if(!isBanana) {
            rotateTowards(startX + directionX * rangeRadius, startY + directionY * rangeRadius);
        }
        if (isBanana && (tower.towerType == "Banana Tree")) {
            projectileImageView.setOnMouseClicked(event -> {
                isRemoved = true;
                Controller.getInstance().increaseMoneyByAmount(tower.moneyRate);
                root.getChildren().remove(projectileImageView);
                System.out.println("Banana clicked. Increasing money by: " + tower.moneyRate);
            });
        }
        
        
    }
    
    private Set<bloon> collidedBloons= new HashSet<>();; // 記錄已碰撞的氣球
    public void checkForCollision(List<bloon> bloonsList, AnchorPane root) {
        if(isBanana && (tower.towerType == "Banana Tree")) return;
        List<bloon> overlap = new ArrayList<>();
        List<bloon> toRemove = new ArrayList<>();
        for (bloon b : bloonsList) {
            if (!collidedBloons.contains(b) && checkCollision(projectileImageView, b.imageView)) {
                if(tower.towerType.equals("Painter") && b.glueable && b.Visible)
                {
                    if(b.isglue==false)
                    {
                        b.isglue();
                        b.isglue=true;
                    }
                }
                else if(tower.towerType.equals("Cannon") && b.Visible)
                {
                    isRemoved = true;
                    //toRemove.addAll(checkexplosion(root, b.imageView.getTranslateX(), b.imageView.getTranslateY(), bloonsList));
                    overlap=checkexplosion(root, bloonsList);
                }
                else if(tower.towerType.equals("Sniper")||tower.towerType.equals("NinjaMonkey"))
                {
                    if(!b.sharpImmunity)
                    {
                        if(--durability<=0)
                            isRemoved = true;
                        collidedBloons.addAll((b.handleCollision(attack)));
                        if(b.isRemoved)
                            toRemove.add(b);
                        break;
                    }
                    else
                        isRemoved = true;
                }
                else if(!b.sharpImmunity && b.Visible)
                {
                    if(--durability<=0)
                        isRemoved = true;
                    collidedBloons.addAll((b.handleCollision(attack)));
                    if(b.isRemoved)
                        toRemove.add(b);
                    break;
                }
                else if(b.sharpImmunity)
                {
                    ceramicBloonHit.play(false);
                    isRemoved = true;
                }
            }
        }
        for(bloon b : overlap)
        {
            b.handleCollision(attack);
            if(b.isRemoved)
                toRemove.add(b);
        }
        
        if(toRemove.size()>0)
            pop.play();
        bloonsList.removeAll(toRemove);
    }
    public boolean checkCollision(ImageView iv1, ImageView iv2) {
        return iv1.getBoundsInParent().intersects(iv2.getBoundsInParent());
    }

    private String getProjectileImagePath() {
        switch (towerType) {
            case "Monkey":
                return "resouce\\bullet\\dart.png";
            case "Snag":
                return "resouce\\bullet\\snag.png"; // 更改为实际的图片路径
            case "Battleship":
                return "resouce\\bullet\\dart.png";
            case "Cannon":
                return "resouce\\bullet\\bomb.png";
            case "Boomerange":
                return "resouce\\bullet\\boomeranges.png";
            case "SnagTower":
                return "resouce\\bullet\\snags.png";
            case "NinjaMonkey":
                return "resouce\\bullet\\Shuriken.png";
            case "Painter":
                return "resouce\\bullet\\glue.png";
            case "Sniper":
                return "resouce\\bullet\\dart.png";
            case "Wizard":
                return "resouce\\bullet\\fireball.png";
            case "SuperMonkey":
                return "resouce\\bullet\\lightball5.png";
            default:
                return "resouce\\bullet.png"; // 默认子弹图像路径
        }
    }

    private Point2D getProjectileSize() {
        switch (towerType) {
            case "Monkey":
            case "Snag":
            case "Banana Tree":
            case "Battleship":
            case "Cannon":
            case "Boomerange":
            case "NinjaMonkey":
            case "Painter":
            case "Sniper":
            case "SuperMonkey":
                return new Point2D(40, 40); // 子弹宽度和高度
            case "SnagTower":
                return new Point2D(40, 40); // 子弹宽度和高度
            case "Wizard":
                return new Point2D(80, 80); // 子弹宽度和高度
            default:
                return new Point2D(40, 40); // 默认子弹宽度和高度
        }
    }


    private boolean isrotate=false;
    private int cun=0;
    public void move() {
        if (!isBanana || (isBanana && distanceTravelled <= rangeRadius/2)) {
            projectileImageView.setLayoutX(projectileImageView.getLayoutX() + directionX * speed);
            projectileImageView.setLayoutY(projectileImageView.getLayoutY() + directionY * speed);
            distanceTravelled += speed;
            if(tower.towerType.equals("Boomerange") || tower.towerType.equals("NinjaMonkey"))
                projectileImageView.setRotate(cun+=10);
        }
        if (!isrotate && tower.towerType.equals("Boomerange") && distanceTravelled > rangeRadius /2) {
            this.directionX *= -1;
            this.directionY *= -1;
            isrotate=true;
        }
        if (!isBanana && distanceTravelled > rangeRadius ) {
            isRemoved = true;
        }

        if (isBanana && distanceTravelled >= rangeRadius/2) {
            // 香蕉达到射程范围后停住
            directionX = 0;
            directionY = 0;
            if (System.currentTimeMillis() - creationTime > lifespan) {
                isRemoved = true;
            }
        }
    }
    
    public ImageView getProjectileImageView() {
        return projectileImageView;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void rotateTowards(double targetX, double targetY) {
		tower.getStartCoordinates();
        double startX = tower.x;
        double startY = tower.y;
        double deltaX = targetX - startX;
        double deltaY = targetY - startY;
        double angle = Math.toDegrees(Math.atan2(deltaY, deltaX)) + 90;
		Platform.runLater(() -> projectileImageView.setRotate(angle));
        
    }
    private List<bloon> checkexplosion(AnchorPane root ,List<bloon> bloonsList) {
        Image image = new Image("file:@..//..//resouce//explosion.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100); // 設置圖片寬度
        imageView.setFitHeight(100); // 設置圖片高度
        
        imageView.setTranslateX(projectileImageView.getLayoutX() + projectileImageView.getFitWidth() / 2 - imageView.getFitWidth() / 2);//
        imageView.setTranslateY(projectileImageView.getLayoutY() + projectileImageView.getFitHeight() / 2 - imageView.getFitHeight() / 2);//- imageView.getFitHeight() / 2
        imageView.setVisible(true);
        root.getChildren().add(imageView);

        List<bloon> overlap = new ArrayList<>();
        //List<bloon> toRemove = new ArrayList<>();

        // 創建一個Timeline來控制圖片顯示時間
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), e -> {
            root.getChildren().remove(imageView); // 1秒後移除圖片
        }));
        timeline.setCycleCount(1);
        timeline.play();

        for (bloon b : bloonsList) {
            if (checkCollision(imageView, b.imageView) && b.Visible) {
                overlap.add(b);
            }
        }
        return overlap;
    }
}
