package com.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class bloon {
    private static int cun=0;
    private static final Image RED = new Image("file:@..//..//resouce//bloon//R.jpg");
    private static final Image BLUE = new Image("file:@..//..//resouce//bloon//B.jpg");
    private static final Image GREEN = new Image("file:@..//..//resouce//bloon//G.jpg");
    private static final Image YELLOW = new Image("file:@..//..//resouce//bloon//Y.jpg");
    private static final Image PINK = new Image("file:@..//..//resouce//bloon//P.jpg");
    private static final Image BLACK = new Image("file:@..//..//resouce//bloon//Black.png");
    private static final Image WHITE = new Image("file:@..//..//resouce//bloon//White.png");
    private static final Image LEAD = new Image("file:@..//..//resouce//bloon//Lead.png");
    private static final Image CAMO = new Image("file:@..//..//resouce//bloon//Camo.png");
    private static final Image ZEBRA = new Image("file:@..//..//resouce//bloon//Zebra.png");
    private static final Image RAINBOW = new Image("file:@..//..//resouce//bloon//Rainbow.png");
    private static final Image CERAMIC = new Image("file:@..//..//resouce//bloon//Ceramic.png");
    private static final Image MOAB = new Image("file:@..//..//resouce//bloon//MOAB.png");



    private String type;
    public ImageView imageView;
    private int health;
    private int moneyValue;
    private double speed=1.0;
    private double width;
    private double height;
    private SVGPath path;
    private PathTransition transition;
    private AnchorPane root ;
    private double progress;
    public boolean isRemoved = false;
    public bloon(String type, AnchorPane root , double progress) {
        this.type = type;
        width = 40;
        height = 40;
        if (type.equals("R")) {
			//health = 1;
			//moneyValue = 2;
			speed *= 1;
			//width = ;
			//height = (double)redbloonImage.getHeight(null);
		}
		else if (type.equals("B")) {
			//health = 1;
			//moneyValue = 3;
			speed *= 1.4;
			//width = (double)bluebloonImage.getWidth(null);
			//height = (double)bluebloonImage.getHeight(null);
		}
		else if (type.equals("G")) {
			health = 1;
			moneyValue = 4;
			speed *= 1.8;
			//width = (double)greenbloonImage.getWidth(null);
			//height = (double)greenbloonImage.getHeight(null);
		}
		else if (type.equals("Y")) {
			health = 1;
			moneyValue = 5;
			speed *= 3.2;
			//width = (double)yellowbloonImage.getWidth(null);
			//height = (double)yellowbloonImage.getHeight(null);
		}
		else if (type.equals("P")) {
			health = 1;
			moneyValue = 6;
			speed *= 3.5;
			//width = (double)pinkbloonImage.getWidth(null);
			//height = (double)pinkbloonImage.getHeight(null);
		}
		else if (type.equals("Black")) {
			health = 1;
			moneyValue = 10;
			speed *= 1.8;
			//width = (double)blackbloonImage.getWidth(null);
			//height = (double)blackbloonImage.getHeight(null);
			//explosionImmunity = true;
		}
		else if (type.equals("White")) {
			health = 1;
			moneyValue = 10;
			speed *= 2;
			//width = (double)whitebloonImage.getWidth(null);
			//height = (double)whitebloonImage.getHeight(null);
			//freezeImmunity = true;
		}
		else if (type.equals("Camo")) {
			health = 1;
			moneyValue = 1;
			speed *= 1;
			//width = (double)camobloonImage.getWidth(null);
			//height = (double)camobloonImage.getHeight(null);
			//detectionImmunity = true;
		}
		else if (type.equals("Lead")) {
			health = 1;
			moneyValue = 12;
			speed *= 1;
			//width = (double)leadbloonImage.getWidth(null);
			//height = (double)leadbloonImage.getHeight(null);
			//sharpImmunity = true;
		}
		else if (type.equals("Zebra")) {
			health = 1;
			moneyValue = 13;
			speed *= 1.8;
			//width = (double)zebrabloonImage.getWidth(null);
			//height = (double)zebrabloonImage.getHeight(null);
			//explosionImmunity = true;
			//freezeImmunity = true;
		}
		else if (type.equals("Rainbow")) {
			health = 1;
			moneyValue = 15;
			speed *= 2.2;
			//width = (double)rainbowbloonImage.getWidth(null);
			//height = (double)rainbowbloonImage.getHeight(null);
		}
		else if (type.equals("Ceramic")) {
			health = 10;
			moneyValue = 0;
			speed *= 2.5;
			//width = (double)ceramicbloonImage.getWidth(null);
			//height = (double)ceramicbloonImage.getHeight(null);
			//glueImmunity = true;

		}
		else if (type.equals("MOAB")) {
			health = 200;
			moneyValue = 200;
			speed *= 1;
			width = MOAB.getWidth();
			height = MOAB.getHeight();
			//freezeImmunity = true;
			//glueImmunity = true;
		}
        this.imageView = new ImageView(getbloonImage(type));
        
        this.imageView.toFront();
        this.imageView.setFitWidth(width);
        this.imageView.setFitHeight(height);
        this.imageView.setTranslateX(-1000);
        this.root=root;

        //root.getChildren().add(this.imageView);
        

        try {
            Path filePath = Paths.get("resouce//svg.txt");
            String content = Files.readString(filePath);
            this.path = new SVGPath();
            this.path.setContent(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.speed *= 1;

        startAnimation(progress);
    }

    private void startAnimation(double progress) {
        this.transition = new PathTransition(Duration.millis(10000 / this.speed), this.path, this.imageView);
        root.getChildren().add(this.imageView);
        this.transition.setInterpolator(Interpolator.LINEAR);
        if(type.equals("MOAB")){
            this.transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        }
        this.transition.jumpTo(Duration.millis(10000 / this.speed *progress));
        this.transition.setOnFinished((ActionEvent event) -> {
            root.getChildren().remove(this.imageView);
            Controller.removeBloon(this);
            //System.out.println("hihi");
        });
        this.transition.play();

        /*AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (checkCollision(imageView, Controller.targetImageView,Controller.targetImageView2)) {
                    System.out.println("Collision Detected!");
                    System.out.println(cun++);
                    handleCollision(this);
                }
            }
        };
        timer.start();*/
    }

    /*private boolean checkCollision(ImageView iv1, ImageView iv2,ImageView iv3) {
        return iv1.getBoundsInParent().intersects(iv2.getBoundsInParent())|| iv1.getBoundsInParent().intersects(iv3.getBoundsInParent());
    }*/

    public List<bloon> handleCollision() {
        double currentTime = this.transition.getCurrentTime().toMillis();
        double oldTotalDuration = this.transition.getTotalDuration().toMillis();
        this.progress = currentTime / oldTotalDuration;
        
        List<bloon> newBloons = new ArrayList<>();
        if (!type.equals("R")) {
            newBloons=breakIntobloons();
        //new bloon(nextType, root,progress+0.05+0.05); // Create a new bloon with the next type
        }
        root.getChildren().remove(this.imageView);
        isRemoved = true;
        //Controller.removeBloon(this);
        this.transition.stop();
        System.out.println(cun++);
        return newBloons;
    }

    public List<bloon> breakIntobloons() {
        List<bloon> newBloons = new ArrayList<>();
        bloon bloon;
        switch (type) {
            case "B":
                bloon = new bloon("R", root, progress);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "G":
                bloon = new bloon("B", root, progress);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "Y":
                bloon = new bloon("G", root, progress);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "P":
                bloon = new bloon("Y", root, progress);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "Black":
                bloon = new bloon("P", root, progress);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new bloon("P", root, progress+0.001);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "White":
                bloon = new bloon("P", root, progress);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new bloon("P", root, progress+0.001);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "Camo":
                bloon = new bloon("Pink", root, progress);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "Lead":
                bloon = new bloon("Black", root, progress);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new bloon("Black", root, progress+0.001);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "Zebra":
                bloon = new bloon("Black", root, progress);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new bloon("White", root, progress+0.001);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "Rainbow":
                bloon = new bloon("Zebra", root, progress);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new bloon("Zebra", root, progress+0.001);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "Ceramic":
                bloon = new bloon("Rainbow", root, progress);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new bloon("Rainbow", root, progress+0.001);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "MOAB":
                bloon = new bloon("Ceramic", root, progress);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new bloon("Ceramic", root, progress+0.001);
                Controller.bloons.add(bloon);
                newBloons.add(bloon);
                break;
            default:
                // Default case if type does not match any of the above
                break;
        }
        return newBloons;
    }
    


    private Image getbloonImage(String type) {
        switch (type) {
            case "R": return RED;
            case "B": return BLUE;
            case "G": return GREEN;
            case "Y": return YELLOW;
            case "P": return PINK;
            case "Black":return BLACK;
            case "White":return WHITE;
            case "Lead":return LEAD;
            case "Camo":return CAMO;
            case "Zebra":return ZEBRA;
            case "Rainbow":return RAINBOW;
            case "Ceramic":return CERAMIC;
            case "MOAB":return MOAB;
            default: return null;
        }
    }

    /*private String bloonDestroy(String type) {
        switch (type) {
            case "R": return "0";
            case "B": return "R";
            case "G": return "B";
            case "Y": return "G";
            case "P": return "Y";
            default: return "0";
        }
    }*/
}