package com.example;

import javafx.scene.control.*;
import javafx.scene.image.*;
import java.io.IOException;
import javafx.fxml.FXML;

public class menu {
    private static final ImageView notHovered = new ImageView(new Image("file:@..//..//resouce//start_button.png"));
    private static final ImageView Hovered = new ImageView(new Image("file:@..//..//resouce//hover_button.png"));
    @FXML
    private Button startButton;
    @FXML
    private void initialize() {
        notHovered.setFitHeight(90);
        notHovered.setFitWidth(200);
        Hovered.setFitHeight(90);
        Hovered.setFitWidth(200);
        startButton.setLayoutX(246.0);
        startButton.setLayoutY(271.0);
        startButton.setOnAction(e -> {
            try {
                switchToStageMenu();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        startButton.setGraphic(notHovered);
        startButton.setOnMouseEntered(e ->hover());
        startButton.setOnMouseExited(e ->not_hover());

    }
    @FXML
    private void switchToStageMenu() throws IOException {
        test.setRoot("stage_menu");
    }
    @FXML
    private void hover() {
        startButton.setGraphic(Hovered);
    }
    @FXML
    private void not_hover() {
        startButton.setGraphic(notHovered);
    }
}