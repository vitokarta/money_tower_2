package com.example;

import javafx.scene.control.*;
import javafx.scene.image.*;
import java.io.IOException;
import javafx.fxml.FXML;

public class stage_menu {
    public static String filePath = "resouce//path//svgStage";
    public static String manualmap = "resouce//gamemap//ManualMap";
    public static String stageBloon = "resouce//stage_bloon//stage";


    private static final ImageView backward_button = new ImageView(new Image("file:/C://Users//maste//OneDrive//桌面//JAVA_project//money_tower_2//resouce//backward.png"));
    private static final ImageView hovered_backward_button = new ImageView(new Image("file:@..//..//resouce//backward_hovered.png"));
    private static final ImageView stage1 = new ImageView(new Image("file:@..//..//resouce//gamemap//Map1.jpg"));
    private static final ImageView stage2 = new ImageView(new Image("file:@..//..//resouce//gamemap//Map2.jpg"));
    private static final ImageView stage3 = new ImageView(new Image("file:@..//..//resouce//gamemap//Map3.jpg"));
    private static final ImageView stage4 = new ImageView(new Image("file:@..//..//resouce//gamemap//Map4.jpg"));

    @FXML
    private Button backwardButton;
    @FXML
    private Button stage1Button;
    @FXML
    private Button stage2Button;
    @FXML
    private Button stage3Button;
    @FXML
    private Button stage4Button;

    @FXML
    private void initialize() {
        filePath = "resouce//path//svgStage";
        manualmap = "resouce//gamemap//ManualMap";
        stageBloon = "resouce//stage_bloon//stage";
        backward_button.setFitHeight(50);
        backward_button.setFitWidth(50);
        hovered_backward_button.setFitHeight(50);
        hovered_backward_button.setFitWidth(50);
        stage1.setFitHeight(140);
        stage1.setFitWidth(220.0);
        
        stage1Button.setOnAction(e -> {
            try {
                switchToStage1();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        stage1Button.setGraphic(stage1);

        stage2.setFitHeight(140);
        stage2.setFitWidth(220.0);
        stage2Button.setOnAction(e -> {
            try {
                switchToStage2();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        stage2Button.setGraphic(stage2);

        stage3.setFitHeight(140);
        stage3.setFitWidth(220.0);
        stage3Button.setOnAction(e -> {
            try {
                switchToStage3();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        stage3Button.setGraphic(stage3);

        stage4.setFitHeight(140);
        stage4.setFitWidth(220.0);
        stage4Button.setOnAction(e -> {
            try {
                switchToStage4();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        stage4Button.setGraphic(stage4);

        backwardButton.setOnAction(e -> {
            try {
                switchToMenu();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        backwardButton.setGraphic(backward_button);
        backwardButton.setOnMouseEntered(e ->ahover());
        backwardButton.setOnMouseExited(e ->not_ahover());

    }
    @FXML
    private void switchToStage1() throws IOException {
        filePath = filePath + "1.txt";
        manualmap = manualmap + "1.jpg";
        stageBloon = stageBloon + "1.txt";
        Main.setRoot("scene");
    }
    private void switchToStage2() throws IOException {
        filePath = filePath + "2.txt";
        manualmap = manualmap + "2.jpg";
        stageBloon = stageBloon + "2.txt";
        Main.setRoot("scene2");
    }
    private void switchToStage3() throws IOException {
        filePath = filePath + "3.txt";
        manualmap = manualmap + "3.jpg";
        stageBloon = stageBloon + "3.txt";
        Main.setRoot("scene3");
    }
    private void switchToStage4() throws IOException {
        filePath = filePath + "4.txt";
        manualmap = manualmap + "4.jpg";
        stageBloon = stageBloon + "4.txt";
        Main.setRoot("scene4");
    }

    @FXML
    private void switchToMenu() throws IOException {
        Main.setRoot("menu");
    }
    @FXML
    private void ahover() {
        backwardButton.setGraphic(hovered_backward_button);
    }
    @FXML
    private void not_ahover() {
        backwardButton.setGraphic(backward_button);
    }
}