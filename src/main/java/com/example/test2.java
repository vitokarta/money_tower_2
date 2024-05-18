package com.example;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.SnapshotParameters;
import javafx.stage.Stage;

public class test2 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600);

        Button button = new Button("Click Me!");
        root.getChildren().add(button);

        // 图片视图用于显示按钮的图像
        ImageView imageView = new ImageView();
        imageView.setVisible(false); // 初始时不可见
        root.getChildren().add(imageView);

        button.setOnAction(event -> {
            // 捕捉按钮的图像
            WritableImage image = button.snapshot(new SnapshotParameters(), null);
            imageView.setImage(image);
            imageView.setVisible(true);
            imageView.toFront(); // 确保图像显示在最前面
        });

        // 鼠标移动事件，更新图像位置
        scene.setOnMouseMoved(event -> {
            if (imageView.isVisible()) {
                imageView.setX(event.getX() - imageView.getImage().getWidth() / 2);
                imageView.setY(event.getY() - imageView.getImage().getHeight() / 2);
            }
        });

        primaryStage.setTitle("Image Follow Mouse");
        primaryStage.setScene(scene);
        primaryStage.show();
        

    }

    public static void main(String[] args) {
        launch(args);
    }
}