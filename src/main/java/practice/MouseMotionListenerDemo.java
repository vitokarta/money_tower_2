package practice;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MouseMotionListenerDemo extends Application {

    Label a1, a2, a3;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Demo");

        Pane root = new Pane();
        Scene scene = new Scene(root, 640, 480);

        a1 = new Label("demo1");
        a1.setLayoutX(100);
        a1.setLayoutY(30);
        root.getChildren().add(a1);

        a2 = new Label("demo2");
        a2.setLayoutX(100);
        a2.setLayoutY(70);
        root.getChildren().add(a2);

        a3 = new Label("demo3");
        a3.setLayoutX(100);
        a3.setLayoutY(110);
        root.getChildren().add(a3);

        scene.setOnMouseDragged(this::handleMouseDragged);
        scene.setOnMouseMoved(this::handleMouseMoved);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleMouseDragged(MouseEvent e) {
        a3.setText("Mouse dragged!");
    }

    private void handleMouseMoved(MouseEvent e) {
        a1.setText("x: " + e.getX());
        a2.setText("y: " + e.getY());
    }
}
