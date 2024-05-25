package com.example;

import javax.swing.ImageIcon;


import javafx.util.Duration;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
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
    public int durability=1;
    public double speed;
    public double directionX;
    public double directionY;
    public int attack;
    public int health;
    public double distanceTravelled;
    public double rangeRadius;
    public boolean isRemoved = false;
    public tower tower;


    public Projectile(AnchorPane root, tower tower, double targetX, double targetY, int speed,  int rangeRadius) {
        this.tower = tower;
        String imagePath = tower.getProjectileImagePath();
        try {
            Image image = new Image(new FileInputStream(imagePath));
            projectileImageView = new ImageView(image);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        projectileImageView.setFitWidth(40);
        projectileImageView.setFitHeight(40);
        Point2D startCoordinates = tower.getStartCoordinates();
        double startX = startCoordinates.getX();
        double startY = startCoordinates.getY();
        projectileImageView.setLayoutX(startX - 40 / 2);
        projectileImageView.setLayoutY(startY - 40 / 2);
        root.getChildren().add(projectileImageView);

        this.speed = speed;
        this.rangeRadius = rangeRadius;
        double deltaX = targetX - startX;
        double deltaY = targetY - startY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        directionX = deltaX / distance;
        directionY = deltaY / distance;
        distanceTravelled = 0;
    }

    private Set<bloon> collidedBloons= new HashSet<>();; // 記錄已碰撞的氣球
    public void checkForCollision(List<bloon> bloonsList, AnchorPane root) {
        List<bloon> toRemove = new ArrayList<>();
        List<bloon> overlap = new ArrayList<>();
        for (bloon b : bloonsList) {
            if (!collidedBloons.contains(b) && checkCollision(projectileImageView, b.imageView)) {
                if(b.type.equals("Camo"))
                {
                    if(tower.towerType.equals("Sniper")||tower.towerType.equals("NinjaMonkey"))
                    {
                        if(--durability<=0)
                            isRemoved = true;
                        collidedBloons.addAll((b.handleCollision()));
                        if(b.isRemoved)
                            toRemove.add(b);
                        break;
                    }
                }
                else if(tower.towerType.equals("Cannon"))
                {
                    isRemoved = true;
                    //toRemove.addAll(checkexplosion(root, b.imageView.getTranslateX(), b.imageView.getTranslateY(), bloonsList));
                    overlap=checkexplosion(root, bloonsList);
                }
                else if(tower.towerType.equals("Painter"))
                {
                    if(b.isglue==false)
                    {
                        b.isglue();
                        b.isglue=true;
                    }
                }
                else
                {
                    if(--durability<=0)
                        isRemoved = true;
                    collidedBloons.addAll((b.handleCollision()));
                    if(b.isRemoved)
                        toRemove.add(b);
                    break;
                }
                
            }
        }
        for(bloon b : overlap)
        {
            b.handleCollision();
            if(b.isRemoved)
                toRemove.add(b);
        }
        bloonsList.removeAll(toRemove);
    }
    public boolean checkCollision(ImageView iv1, ImageView iv2) {
        return iv1.getBoundsInParent().intersects(iv2.getBoundsInParent());
    }


    public void move() {
        projectileImageView.setLayoutX(projectileImageView.getLayoutX() + directionX * speed);
        projectileImageView.setLayoutY(projectileImageView.getLayoutY() + directionY * speed);
        distanceTravelled += speed;
        if (distanceTravelled > rangeRadius) {
            isRemoved = true;
        }
    }

    public ImageView getProjectileImageView() {
        return projectileImageView;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void rotateTowards(double targetX, double targetY) {
		Point2D startCoordinates = tower.getStartCoordinates();
        double startX = startCoordinates.getX();
        double startY = startCoordinates.getY();
        double deltaX = targetX - startX;
        double deltaY = targetY - startY;
        double angle = Math.toDegrees(Math.atan2(deltaY, deltaX)) + 90;
		Platform.runLater(() -> projectileImageView.setRotate(angle));
        
    }
    private List<bloon> checkexplosion(AnchorPane root ,List<bloon> bloonsList) {
        System.out.println("hihi");
        Image image = new Image("file:@..//..//resouce//explosion.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100); // 設置圖片寬度
        imageView.setFitHeight(100); // 設置圖片高度
        
        imageView.setTranslateX(projectileImageView.getLayoutX() + projectileImageView.getFitWidth() / 2 - imageView.getFitWidth() / 2);//
        imageView.setTranslateY(projectileImageView.getLayoutY() + projectileImageView.getFitHeight() / 2 - imageView.getFitHeight() / 2);//- imageView.getFitHeight() / 2
        imageView.setVisible(true);
        root.getChildren().add(imageView);

        List<bloon> overlap = new ArrayList<>();
        List<bloon> toRemove = new ArrayList<>();

        // 創建一個Timeline來控制圖片顯示時間
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), e -> {
            root.getChildren().remove(imageView); // 1秒後移除圖片
        }));
        timeline.setCycleCount(1);
        timeline.play();

        for (bloon b : bloonsList) {
            if (checkCollision(imageView, b.imageView)) {
                overlap.add(b);
            }
        }
        return overlap;
    }
}
