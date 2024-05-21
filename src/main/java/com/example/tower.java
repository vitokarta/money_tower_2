package com.example;

import javafx.application.Platform;
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

    public String towerType;
	public int attackPower;
	public int rangeRadius;
	public int width;
	public int height;
	public int costValue;
	public int sellValue;
	public int projectileSpeed;
	public int attackDelayTick;
	public int moneyRate;

	public double imageheight;
	public double imagewidth;

	private Image Image2;
	private Image Image3;

    public tower(AnchorPane root,String imagePath ,double imageWidth, double imageHeight) {
        if (imagePath.equals("resouce\\monkey.png")) {
			attackPower = 1;
			rangeRadius = 200; //medium
			costValue = 150;
			sellValue = costValue /2 ;
			attackDelayTick = 29;
			projectileSpeed = 5; //fast
		}
		else if (imagePath.equals("resouce\\snag.png")) {
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
			rangeRadius = 60;
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
		this.towerType=getName(imagePath);

        try {
            Image Image1 = new Image(new FileInputStream(imagePath));
            Image2 = new Image(new FileInputStream("resouce\\range.png"));
			Image3 = new Image(new FileInputStream("resouce\\red_range.png"));
            towerImageView = new ImageView(Image1);
            towerImageRange = new ImageView(Image2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		imageheight = imageHeight;
		imagewidth = imageWidth;

		towerPane = new StackPane();
        towerImageView.setFitWidth(imageWidth);
        towerImageView.setFitHeight(imageHeight);
        towerImageRange.setFitWidth(rangeRadius);
        towerImageRange.setFitHeight(rangeRadius);
        towerImageRange.setOpacity(0.8);
	

        
        
        towerPane.getChildren().addAll(towerImageRange, towerImageView); // Add both images to the StackPane

        button = new Button("sell");
        button.setLayoutX(890);
        button.setLayoutY(570);
		button.setUserData(this);
		root.getChildren().add(button);
		
        button.setOnAction(event1 -> {
            AnchorPane parent = (AnchorPane) towerPane.getParent();
            if (parent != null) {
				parent.getChildren().remove(towerPane);
				button.setVisible(false);
				Controller.removeTower(this); // 移除 tower 实例
				ManualMap.restoreMap(this);
				System.out.println(this.towerType);
				Controller.getInstance().increaseMoneyByAmount(this.sellValue); // 增加金錢數值
        	}
        });
    }
	public String getName(String imagePath) {
		switch (imagePath) {
			case "resouce\\monkey.png":
				return "Monkey";
			case "resouce\\snag.png":
				return "Snag";
			case "resouce\\bananatree.png":
				return "Banana Tree";
			case "resouce\\battleship.png":
				return "Battleship";
			case "resouce\\cannon.png":
				return "Cannon";
			case "resouce\\boomerange.png":
				return "Boomerange";
			case "resouce\\icemonkey.png":
				return "IceMonkey";
			case "resouce\\ninjamonkey.png":
				return "NinjaMonkey";
			case "resouce\\painter.png":
				return "Painter";
			case "resouce\\sniper.png":
				return "Sniper";
			case "resouce\\wizmonkey.png":
				return "Wizard";
			case "resouce\\supermonkey.png":
				return "SuperMonkey";
			default:
				return "Unknown";
		}
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
	public void switchToImage2() {
        towerImageRange.setImage(Image2);
    }
	public void switchToImage3() {
        towerImageRange.setImage(Image3);
    }

    public void placeTower(AnchorPane root, double x, double y) {
        towerPane.setLayoutX(x);  // Center the pane (assuming width and height are 100)
        towerPane.setLayoutY(y);
        root.getChildren().add(towerPane);
		Controller.getInstance().decreaseMoneyByAmount(this.costValue); // 花錢買猴
    }
	public void rotateTowards(double targetX, double targetY) {
		double x;
		double y;
		
		if(towerPane.getChildren().contains(towerImageRange)){
			x=towerPane.getLayoutX()+ rangeRadius / 2;
			y=towerPane.getLayoutY()+ rangeRadius / 2;
		}
		else{
			x=towerPane.getLayoutX()+ towerImageView.getFitWidth()/2;
			y=towerPane.getLayoutY()+ towerImageView.getFitHeight()/2;

		}
        double deltaX = targetX - x;
        double deltaY = targetY - y;
        double angle = Math.toDegrees(Math.atan2(deltaY, deltaX)) + 90;
		if (towerType.equals("Battleship")) {
			Platform.runLater(() -> towerImageView.setRotate(angle + 90));
		} 
		else Platform.runLater(() -> towerImageView.setRotate(angle));
		System.out.println(angle);
        
    }
}
