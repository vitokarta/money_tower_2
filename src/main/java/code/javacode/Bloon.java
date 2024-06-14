package code.javacode;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
public class Bloon {
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
    private static final Image GLUE = new Image("file:@..//..//resouce//glue.png");


    public String type;
    public ImageView imageView;
    public ImageView glueimage = new ImageView(GLUE);
    public Group imageGroup;
    private int health;
    private double speed=0.5f;
    private double width;
    private double height;
    private SVGPath path;
    public PathTransition transition;
    private PathTransition transition2;
    private AnchorPane root ;
    private double progress;
    public boolean isRemoved = false;
    public boolean isglue = false;
    public boolean glueable = true;
    public boolean sharpImmunity = false;
    public boolean Visible = true;
    
    public Bloon(String type, AnchorPane root , double progress) {
        this.type = type;
        width = 40;
        height = 40;
        if (type.equals("R")) {
			health = 1;
			speed *= 1;
		}
		else if (type.equals("B")) {
		    health = 1;
			speed *= 1.4;
		}
		else if (type.equals("G")) {
			health = 1;
			speed *= 1.8;
		}
		else if (type.equals("Y")) {
			health = 1;
			speed *= 3.2;
		}
		else if (type.equals("P")) {
			health = 1;
			speed *= 3.5;
		}
		else if (type.equals("Black")) {
			health = 1;
			speed *= 1.8;
		}
		else if (type.equals("White")) {
			health = 1;
			speed *= 2;
		}
		else if (type.equals("Camo")) {
			health = 1;
			speed *= 1;
			Visible=false;
		}
		else if (type.equals("Lead")) {
			health = 1;
			speed *= 1;
			sharpImmunity = true;
            glueable = false;
		}
		else if (type.equals("Zebra")) {
			health = 1;
			speed *= 1.8;
		}
		else if (type.equals("Rainbow")) {
			health = 1;
			speed *= 2.2;
		}
		else if (type.equals("Ceramic")) {
			health = 10;
			speed *= 2.5;
			glueable = false;

		}
		else if (type.equals("MOAB")) {
			health = 200;
			speed *= 1;
            width = MOAB.getWidth();
			height = MOAB.getHeight();
			glueable = false;
		}
        this.imageView = new ImageView(getbloonImage(type));
        

        this.imageView.toFront();
        this.imageView.setFitWidth(width);
        this.imageView.setFitHeight(height);
        this.imageView.setTranslateX(-20000);
        glueimage.setFitWidth(width);
        glueimage.setFitHeight(height*1.2);
        glueimage.setTranslateX(-20000);
        this.root=root;

        try {
            Path filePath = Paths.get(Stage_menu.filePath);
            String content = Files.readString(filePath);
            this.path = new SVGPath();
            this.path.setContent(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //this.speed *= 1;

        startAnimation(progress);
    }

    private void startAnimation(double progress) {
        imageGroup = new Group();
        //imageGroup.getChildren().add(this.imageView);        
        
        this.transition = new PathTransition(Duration.millis(10000 / this.speed), this.path, this.imageView);
        root.getChildren().add(this.imageView);
        //root.getChildren().add(imageGroup);
        this.transition.setInterpolator(Interpolator.LINEAR);
        if(type.equals("MOAB")){
            this.transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        }
        this.transition.jumpTo(Duration.millis(10000 / this.speed * progress));
        this.transition.setOnFinished((ActionEvent event) -> {
            root.getChildren().remove(imageView);
            //System.out.println(imageView.getTranslateX()+" "+imageView.getTranslateY());
            Controller.health-=TypeToHealth(type);
            Controller.removeBloon(this);
        });
        this.transition.play();
    }
    public void isglue(){
        root.getChildren().remove(imageView);
        double currentTime = transition.getCurrentTime().toMillis();
        double oldTotalDuration = transition.getTotalDuration().toMillis();
        progress = currentTime / oldTotalDuration;
        transition.stop();
        
        transition = new PathTransition(Duration.millis(10000 / this.speed*2), this.path, this.imageView);
        root.getChildren().add(imageView);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.jumpTo(Duration.millis(10000 / this.speed *2 * progress));
        transition.setOnFinished((ActionEvent event) -> {
            root.getChildren().remove(imageView);
            Controller.health-=TypeToHealth(type);
            Controller.removeBloon(this);
        });
        this.transition.play();

        transition2 = new PathTransition(Duration.millis(10000 / this.speed *2), this.path, glueimage);
        root.getChildren().add(glueimage);
        transition2.jumpTo(Duration.millis(10000 / this.speed *2 * progress));
        transition2.setOnFinished((ActionEvent event) -> {
            root.getChildren().remove(glueimage);
        });
        transition2.setInterpolator(Interpolator.LINEAR);
        transition2.play();
    }

    

    public List<Bloon> handleCollision(int attack) {
        List<Bloon> newBloons = new ArrayList<>();
        health-= attack;
        newBloons.add(this);
        if(health<=0)
        {
            double currentTime = this.transition.getCurrentTime().toMillis();
            double oldTotalDuration = this.transition.getTotalDuration().toMillis();
            this.progress = currentTime / oldTotalDuration;
            
            
            newBloons=breakIntobloons(HealthToType(TypeToHealth(type)+health));
            isRemoved = true;
            Controller.getInstance().increaseMoneyByAmount(1);
            root.getChildren().remove(imageView);
            this.transition.stop();
            if(root.getChildren().contains(glueimage))
            {
                root.getChildren().remove(glueimage);
                transition2.stop();
            }
            //ystem.out.println(cun++);
        }
        return newBloons;
    }

    public List<Bloon> breakIntobloons(String type) {
        List<Bloon> newBloons = new ArrayList<>();
        Bloon bloon;
        switch (type) {
            case "B":
                bloon = new Bloon("R", root, progress);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "G":
                bloon = new Bloon("B", root, progress);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "Y":
                bloon = new Bloon("G", root, progress);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "P":
                bloon = new Bloon("Y", root, progress);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "Black":
                bloon = new Bloon("P", root, progress);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new Bloon("P", root, progress+0.001);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "White":
                bloon = new Bloon("P", root, progress);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new Bloon("P", root, progress+0.001);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "Camo":
                bloon = new Bloon("P", root, progress);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "Lead":
                bloon = new Bloon("Black", root, progress);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new Bloon("Black", root, progress+0.001);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "Zebra":
                bloon = new Bloon("Black", root, progress);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new Bloon("White", root, progress+0.001);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "Rainbow":
                bloon = new Bloon("Zebra", root, progress);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new Bloon("Zebra", root, progress+0.001);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "Ceramic":
                bloon = new Bloon("Rainbow", root, progress);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new Bloon("Rainbow", root, progress+0.001);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                break;
            case "MOAB":
                bloon = new Bloon("Ceramic", root, progress);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new Bloon("Ceramic", root, progress+0.001);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new Bloon("Ceramic", root, progress+0.002);
                Controller.Bloons.add(bloon);
                newBloons.add(bloon);
                bloon = new Bloon("Ceramic", root, progress+0.003);
                Controller.Bloons.add(bloon);
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

    private String HealthToType(int health) {
        switch (health) {
            case 0: return "0";
            case 1: return "R";
            case 2: return "B";
            case 3: return "G";
            case 4: return "Y";
            case 5: return "P";
            case 6: return "Black";
            case 7: return "White";
            case 8: return "Lead";
            case 9: return "Camo";
            case 10: return "Zebra";
            case 11: return "Rainbow";
            case 12: return "Ceramic";
            case 13: return "MOAB";
            default: return "0";
        }
    }
    private int TypeToHealth(String type) {
        switch (type) {
            case "0": return 0;
            case "R": return 1;
            case "B": return 2;
            case "G": return 3;
            case "Y": return 4;
            case "P": return 5;
            case "Black":return 6;
            case "White":return 7;
            case "Lead":return 8;
            case "Camo":return 9;
            case "Zebra":return 10;
            case "Rainbow":return 11;
            case "Ceramic":return 12;
            case "MOAB":return 13;
            default: return 0;
        }
    }
}