/*
 Created by Matt Johnston
 */
package card;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Gui class to represent card game as a UI
 */
public class Gui extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Rat-A-Tat CAT!");
        Button btn = new Button();
        btn.setText("Start Game");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Start");
            }
        });

        StackPane root = new StackPane();
        //root.getChildren().add(btn);
        stage.setScene(new Scene(root, 1440, 900));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
