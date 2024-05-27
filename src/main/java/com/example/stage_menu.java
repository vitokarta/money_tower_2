package com.example;

import javafx.scene.control.*;
import javafx.scene.image.*;
import java.io.IOException;
import javafx.fxml.FXML;

public class stage_menu {
    private static final ImageView backward_button = new ImageView(new Image("file:/C://Users//maste//OneDrive//桌面//JAVA_project//money_tower_2//resouce//backward.png"));
    private static final ImageView hovered_backward_button = new ImageView(new Image("file:@..//..//resouce//backward_hovered.png"));
    private static final ImageView stage1 = new ImageView(new Image("file:@..//..//resouce//file_Object Removal.jpg"));
    @FXML
    private Button backwardButton;
    @FXML
    private Button stage1Button;
    @FXML
    private void initialize() {
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
        test.setRoot("scene");
    }
    @FXML
    private void switchToMenu() throws IOException {
        test.setRoot("menu");
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