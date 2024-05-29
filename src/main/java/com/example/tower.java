package com.example;
import javafx.geometry.Point2D;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
public class tower {
    private ImageView towerImageView;
    private ImageView towerImageRange;
    private StackPane towerPane;
    public Button button;
	public double x;
	public double y;
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
	public boolean bulletIsPlaced = false;
	
	public int attackDelayCounter = 0;
    //private List<Projectile> projectiles = new ArrayList<>();
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
			rangeRadius = 10000; //infinite
			costValue = 350;
			sellValue = costValue /2 ;
			attackDelayTick = 66;
			projectileSpeed = 100; //very fast
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
		else if (imagePath.equals("resouce\\snagtower.png")) {
			attackPower = 1;
			rangeRadius = 100; //small
			costValue = 300;
			sellValue = costValue /2;
			attackDelayTick = 100;
			projectileSpeed = 2;
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
			projectileSpeed = 10;
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
			rangeRadius = 200;
			costValue = 750;
			sellValue = costValue /2;
			attackDelayTick = 500;
			projectileSpeed = 2;
			moneyRate = 80;
		}
		else if (imagePath.equals("resouce\\wizmonkey.png")) {
			attackPower = 3;
			rangeRadius = 200;
			costValue = 750;
			sellValue = costValue /2;
			attackDelayTick = 5;
			projectileSpeed = 10;
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
        towerImageRange.setFitWidth(rangeRadius);  //直徑
        towerImageRange.setFitHeight(rangeRadius);
        towerImageRange.setOpacity(0.8);
	

        
        
        towerPane.getChildren().addAll(towerImageRange, towerImageView); // Add both images to the StackPane

		
        // 创建ImageView
		
		Image image = new Image("file:@..//..//resouce//sell.png");
        ImageView imageView2 = new ImageView(image);
        button = new Button();
		imageView2.setFitWidth(116);
		imageView2.setFitHeight(60);
		button.setGraphic(imageView2);
        button.setLayoutX(864);
        button.setLayoutY(533);
		button.setPrefWidth(96); // 设置按钮宽度
		button.setPrefHeight(60); // 设置按钮高度
		button.setUserData(this);
		button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		root.getChildren().add(button);
		
        button.setOnAction(event1 -> {
            AnchorPane parent = (AnchorPane) towerPane.getParent();
            if (parent != null) {
				parent.getChildren().remove(towerPane);
				button.setVisible(false);
				Controller.removeTower(this); // 移除 tower 实例
				ManualMap.restoreMap(this);
				//System.out.println(this.towerType);
				Controller.getInstance().increaseMoneyByAmount(this.sellValue); // 增加金錢數值
        	}
        });
		
    }
	public void checkIfCanAddProjectile(AnchorPane root,List<bloon> bloonsList) {
        boolean bloonInRadius = false; //used to check if a Bloon is within the rangeRadius of the Tower
        int shortestDistance = Integer.MAX_VALUE; //shortest distance b/w the closest Bloon and the Tower
        int checkDistance; //used to check for the smallest distance

        bloon closestBloon = null; // 保存最近的 bloon 对象

        //check all Bloon objects on screen 
        for (bloon b : bloonsList) {
		
            double targetX=b.imageView.getTranslateX()+b.imageView.getFitWidth()/2;
			double targetY=b.imageView.getTranslateY()+b.imageView.getFitHeight()/2;
            if (isTargetInRange(targetX, targetY)) {
				if(!b.type.equals("Camo")) {
					bloonInRadius = true;
					checkDistance = (int) Math.sqrt(Math.pow(targetX - this.x, 2) + Math.pow(targetY - this.y, 2));
					if (checkDistance < shortestDistance) {
						shortestDistance = checkDistance;
						closestBloon = b; // 保存最近的 bloon 对象
					}
				}
				else
				{
					if(towerType.equals("NinjaMonkey")||towerType.equals("Sniper")) {
						bloonInRadius = true;
						checkDistance = (int) Math.sqrt(Math.pow(targetX - this.x, 2) + Math.pow(targetY - this.y, 2));
						if (checkDistance < shortestDistance) {
							shortestDistance = checkDistance;
							closestBloon = b; // 保存最近的 bloon 对象
						}
					}
				}
                
            }
        }
		if(closestBloon!=null) {
			double targetX=closestBloon.imageView.getTranslateX()+closestBloon.imageView.getFitWidth()/2;
			double targetY=closestBloon.imageView.getTranslateY()+closestBloon.imageView.getFitHeight()/2;
			//rotateTowards(targetX,targetY);
			double angle = Math.toDegrees(Math.atan2(targetY - y, targetX - x)) + 90;
            // rotateTowards(targetX,targetY);
			
            if (towerType.equals("Snag")  ) {
                shootInAllDirections(root);
            }
			else if(!towerType.equals("Banana Tree") && (!towerType.equals("SnagTower"))){
                rotateTowards(targetX, targetY);
                shoot(root, angle);
            }
		}
        return ;
    }
	
    public void shoot(AnchorPane root, double angle) {
		if(!bulletIsPlaced) return;
        if (attackDelayCounter >= attackDelayTick) {
			if(towerType == "Battleship") {
				Projectile projectile = new Projectile(root, this, angle, projectileSpeed,this.rangeRadius, false);
				Controller.projectiles.add(projectile);
				Projectile projectile2 = new Projectile(root, this, angle + 180, projectileSpeed,this.rangeRadius, false);
				Controller.projectiles.add(projectile2);
			}
			else{
				Projectile projectile = new Projectile(root, this, angle, projectileSpeed,this.rangeRadius, false);
				Controller.projectiles.add(projectile);
			}
            attackDelayCounter = 0;
        }
    }
	public void shootInAllDirections(AnchorPane root) {
        if (!bulletIsPlaced) return; // 仅在猴子放置后才射击
        if (attackDelayCounter >= attackDelayTick) {
            getStartCoordinates();
            double startX = x;
            double startY = y;

            double[] angles = {0, 45, 90, 135, 180, 225, 270, 315};

            for (double angle : angles) {
                Projectile projectile = new Projectile(root, this, angle, projectileSpeed, this.rangeRadius, false);
                Controller.projectiles.add(projectile);
            }
            attackDelayCounter = 0;
        }
    }
	public void shootBananas(AnchorPane root) {
        //if (!towerType.equals("Banana Tree")) return;
        if (attackDelayCounter >= attackDelayTick) {
			double angle = Math.random() * 360;
			if (towerType.equals("Banana Tree")){
				Projectile banana = new Projectile(root, this, angle, projectileSpeed, rangeRadius, true);
				Controller.projectiles.add(banana);
			}
			else{ // for snagtower
				Projectile snags = new Projectile(root, this, angle, projectileSpeed, rangeRadius, true);
				Controller.projectiles.add(snags);
			}
            attackDelayCounter = 0;
        }
    }
    public void getStartCoordinates() {
		double startX;
		double startY;
	
		if (towerPane.getChildren().contains(towerImageRange)) {
			startX = towerPane.getLayoutX() + rangeRadius / 2;
			startY = towerPane.getLayoutY() + rangeRadius / 2;
		} else {
			startX = towerPane.getLayoutX() + towerImageView.getFitWidth() / 2;
			startY = towerPane.getLayoutY() + towerImageView.getFitHeight() / 2;
		}
		x = startX;
		y = startY;
		
	}
	
    public boolean isTargetInRange(double targetX, double targetY) {
		getStartCoordinates();
        double startX = x;
        double startY = y;
		double deltaX = targetX - startX;
		double deltaY = targetY - startY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return distance <= rangeRadius/2;
    }

    /*public List<Projectile> getProjectiles() {
        return projectiles;
    }*/
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
			case "resouce\\snagtower.png":
				return "SnagTower";
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
	
	
	public void setPlaced(boolean placed) {
		bulletIsPlaced = placed;
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
		//this.bulletIsPlaced = true;
    }

    public void placeTower(AnchorPane root, double x, double y) {
        towerPane.setLayoutX(x);  // Center the pane (assuming width and height are 100)
        towerPane.setLayoutY(y);
        root.getChildren().add(towerPane);
		Controller.getInstance().decreaseMoneyByAmount(this.costValue); // 花錢買猴
		
    }
	public void rotateTowards(double targetX, double targetY) {
		getStartCoordinates();
        double startX = x;
        double startY = y;
        double deltaX = targetX - startX;
        double deltaY = targetY - startY;
        double angle = Math.toDegrees(Math.atan2(deltaY, deltaX)) + 90;
		if (towerType.equals("Battleship")) {
			Platform.runLater(() -> towerImageView.setRotate(angle + 90));
		} 
		else Platform.runLater(() -> towerImageView.setRotate(angle));
        
    }
}
