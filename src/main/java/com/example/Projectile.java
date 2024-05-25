package com.example;

import javax.swing.ImageIcon;


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
    public int durability=2;
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
        for (bloon b : bloonsList) {
            if (!collidedBloons.contains(b) && checkCollision(projectileImageView, b.imageView)) {
                
                if(--durability<=0)
                    isRemoved = true;
                collidedBloons.addAll((b.handleCollision()));
                if(b.isRemoved)
                    toRemove.add(b);
                break;
            }
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
}
