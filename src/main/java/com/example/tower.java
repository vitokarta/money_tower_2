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
	private boolean bulletIsPlaced = false;
	
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
			rangeRadius = 200;
			costValue = 250;
			sellValue = costValue /2;
			attackDelayTick = 100;
			projectileSpeed = 8;
		}
		else if (imagePath.equals("resouce\\supermonkey.png")) {
			attackPower = 2;
			//rangeRadius = 500;
			rangeRadius = 1000;
			costValue = 3000;
			sellValue = costValue /2;
			attackDelayTick = 10;
			//attackDelayTick = 10;
			//projectileSpeed = 5;
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
			attackDelayTick = 5;
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
        towerImageRange.setFitWidth(rangeRadius);  //直徑
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
	public void checkIfCanAddProjectile(AnchorPane root,List<bloon> bloonsList) {
        boolean bloonInRadius = false; //used to check if a Bloon is within the rangeRadius of the Tower
        int shortestDistance = Integer.MAX_VALUE; //shortest distance b/w the closest Bloon and the Tower
        int checkDistance; //used to check for the smallest distance

        bloon closestBloon = null; // 保存最近的 bloon 对象

        //check all Bloon objects on screen 
        for (bloon b : bloonsList) {
            double targetX=b.imageView.getTranslateX()+b.imageView.getFitWidth()/2;
			double targetY=b.imageView.getTranslateY()+b.imageView.getFitHeight()/2;
			//System.out.println(targetX+" "+targetY);
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
			if (!towerType.equals("Snag") && !towerType.equals("Banana Tree")) {
				rotateTowards(targetX,targetY);
			}
			shoot(root,targetX,targetY);
		}
        return ;
    }
	
	
	
    public void shoot(AnchorPane root, double targetX, double targetY) {
		if(!bulletIsPlaced) return;
        if (attackDelayCounter >= attackDelayTick) {
			Point2D startCoordinates = getStartCoordinates();
        	double startX = startCoordinates.getX();
        	double startY = startCoordinates.getY();
            Projectile projectile = new Projectile(root, this, targetX, targetY, projectileSpeed,this.rangeRadius);
            Controller.projectiles.add(projectile);
            attackDelayCounter = 0;
        }
    }

    public Point2D getStartCoordinates() {
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
		return new Point2D(startX, startY);
	}
	
    public boolean isTargetInRange(double targetX, double targetY) {
		Point2D startCoordinates = getStartCoordinates();
        double startX = startCoordinates.getX();
        double startY = startCoordinates.getY();
		double deltaX = targetX - startX;
		double deltaY = targetY - startY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return distance <= rangeRadius/2;
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
	public String getProjectileImagePath() {
		switch (towerType) {
			case "Monkey":
				return "resouce\\bullet\\dart.png";
			case "Snag":
				return "resouce\\bullet\\dart.png";
			case "Banana Tree":
				return "resouce\\bullet\\banana.png";
			case "Battleship":
				return "resouce\\bullet\\bomb.png";
			case "Cannon":
				return "resouce\\bullet\\bomb.png";
			case "Boomerange":
				return "resouce\\bullet\\boomeranges.png";
			case "IceMonkey":
				return "resouce\\bullet\\dart.png";
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
		Point2D startCoordinates = getStartCoordinates();
        double startX = startCoordinates.getX();
        double startY = startCoordinates.getY();
        double deltaX = targetX - startX;
        double deltaY = targetY - startY;
        double angle = Math.toDegrees(Math.atan2(deltaY, deltaX)) + 90;
		if (towerType.equals("Battleship")) {
			Platform.runLater(() -> towerImageView.setRotate(angle + 90));
		} 
		else Platform.runLater(() -> towerImageView.setRotate(angle));
        
    }
}
