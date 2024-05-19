package com.example;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class tower {
    private ImageView towerImageView;
    private ImageView towerImageRange;
    private StackPane towerPane;
    public Button button;

    public String imagePath;
	public int attackPower;
	public int rangeRadius;
	public int width;
	public int height;
	public int costValue;
	public int sellValue;
	public int projectileSpeed;
	public int attackDelayTick;
	public int moneyRate;

    public tower(AnchorPane root,String imagePath) {
        if (imagePath.equals("resouce\\monkey.png")) {
			attackPower = 1;
			rangeRadius = 200; //medium
			costValue = 150;
			sellValue = costValue /2 ;
			attackDelayTick = 29;
			projectileSpeed = 5; //fast
		}
		else if (imagePath.equals("resouce\\snag")) {
			attackPower = 1;
			rangeRadius = 150; //short
			costValue = 250;
			sellValue = costValue /2 ;
			attackDelayTick = 50;
			projectileSpeed = 4; //slow
		}
		else if (imagePath.equals("resouce\\sniper.png")) {
			attackPower = 5;
			rangeRadius = 1000; //infinite
			costValue = 350;
			sellValue = costValue /2 ;
			attackDelayTick = 66;
			projectileSpeed = 10; //very fast
		}

        else if (imagePath.equals("resouce\\boomerange.png")) {
			attackPower = 2;
			rangeRadius = 320; //medium
			costValue = 350;
			sellValue = costValue /2 ;
			attackDelayTick = 40;
			projectileSpeed = 4;
		}
		else if (imagePath.equals("resouce\\ninjamonkey.png")) {
			attackPower = 2;
			rangeRadius = 300;
			costValue = 300;
			sellValue = costValue /2;
			attackDelayTick = 19;
			projectileSpeed = 6;
		}
		else if (imagePath.equals("resouce\\cannon.png")) {
			attackPower = 3;
			rangeRadius = 350; 
			costValue = 650;
			sellValue = costValue /2;
			attackDelayTick = 47;
			projectileSpeed = 4;
		}
		else if (imagePath.equals("resouce\\icemonkey.png")) {
			attackPower = 0;
			rangeRadius = 100; //small
			costValue = 300;
			sellValue = costValue /2;
			attackDelayTick = 73;
			projectileSpeed = 0;
		}
		else if (imagePath.equals("resouce\\painter.png")) {
			attackPower = 0;
			rangeRadius = 380;
			costValue = 250;
			sellValue = costValue /2;
			attackDelayTick = 31;
			projectileSpeed = 4;
		}
		else if (imagePath.equals("resouce\\supermonkey.png")) {
			attackPower = 2;
			rangeRadius = 500;
			costValue = 3000;
			sellValue = costValue /2;
			attackDelayTick = 10;
			projectileSpeed = 5;
		}
		else if (imagePath.equals("resouce\\battleship.png")) {
			attackPower = 3;
			rangeRadius = 500;
			costValue = 400;
			sellValue = costValue /2;
			attackDelayTick = 33;
			projectileSpeed = 4;
		}
		else if (imagePath.equals("resouce\\bananatree.png")) {
			attackPower = 0;
			rangeRadius = 30;
			costValue = 750;
			sellValue = costValue /2;
			attackDelayTick = 500;
			projectileSpeed = 0;
			moneyRate = 80;
		}
		else if (imagePath.equals("resouce\\wizmonkey.png")) {
			attackPower = 3;
			rangeRadius = 200;
			costValue = 750;
			sellValue = costValue /2;
			attackDelayTick = 15;
			projectileSpeed = 3;
		}


        try {
            Image Image1 = new Image(new FileInputStream(imagePath));
            Image Image2 = new Image(new FileInputStream("resouce\\range.png"));
            towerImageView = new ImageView(Image1);
            towerImageRange = new ImageView(Image2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

		towerPane = new StackPane();
        towerImageView.setFitWidth(50);
        towerImageView.setFitHeight(50);
        towerImageRange.setFitWidth(rangeRadius);
        towerImageRange.setFitHeight(rangeRadius);
        towerImageRange.setOpacity(0.8);
		towerPane.setMinWidth(rangeRadius);
        towerPane.setMinHeight(rangeRadius);
		//towerPane.setMouseTransparent(false);
		//towerImageRange.setMouseTransparent(true);
        /*newTower.getTowerPane().setPrefWidth(newTower.rangeRadius);
        newTower.getTowerPane().setPrefHeight(newTower.rangeRadius);*/

        
        
        towerPane.getChildren().addAll(towerImageRange, towerImageView); // Add both images to the StackPane

        button = new Button("sure");
        button.setLayoutX(0);
        button.setLayoutY(0);
        towerPane.setOnMouseClicked(event -> {

            double clickX = event.getX();
            double clickY = event.getY();
            double imageViewX = towerImageView.getLayoutX();
            double imageViewY = towerImageView.getLayoutY();
            double imageViewWidth = towerImageView.getFitWidth();
            double imageViewHeight = towerImageView.getFitHeight();

            if(clickX >= imageViewX && clickX <= imageViewX + imageViewWidth &&
            clickY >= imageViewY && clickY <= imageViewY + imageViewHeight){
                
                button.setVisible(true);
                root.getChildren().add(button);
            }
        });
        button.setOnAction(event1 -> {
            AnchorPane parent = (AnchorPane) towerPane.getParent();
            if (parent != null) {
            parent.getChildren().remove(towerPane);
            root.getChildren().remove(button);
        }
        });
    }

    public ImageView getTowerImageView() {
        return towerImageView;
    }
    public ImageView getTowerImageRange() {
        return towerImageRange;
    }

    public StackPane getTowerPane() {
        return towerPane;
    }

    public void placeTower(AnchorPane root, double x, double y) {
        /*imageWidth=100;
        imageHeight=100;
        towerImageView.setOpacity(0.8);
        towerImageView.setFitWidth(imageWidth);
        towerImageView.setFitHeight(imageHeight);
        towerImageView.setVisible(true);
        towerImageView.toFront();
        //towerImageView.setLayoutX(x - imageWidth / 2);  // Center the image
        //towerImageView.setLayoutY(y - imageHeight / 2);
        /*root.getChildren().add(towerImageView);*/

        towerPane.setLayoutX(x);  // Center the pane (assuming width and height are 100)
        towerPane.setLayoutY(y);
        root.getChildren().add(towerPane);
    }
}
