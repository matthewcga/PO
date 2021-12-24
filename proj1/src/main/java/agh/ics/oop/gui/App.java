package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

public class App extends Application {
    AppSettings.Settings settings;

    public void start(Stage primaryStage) throws FileNotFoundException {
        VBox ui = new VBox();
        HBox output = new HBox();

        Scene scene = new Scene(ui, settings.windowWidth, settings.windowHeight);
        UniverseEngine left = new UniverseEngine(false, settings.isMagicOn, "lewa");
        UniverseEngine right = new UniverseEngine(true, settings.isMagicOn, "prawa");

        Button starter = new Button();
        starter.setText("wczytaj ustawienia");
        starter.setOnAction(e -> {ui.getChildren().addAll(output);});

        Button exitButton = new Button();
        exitButton.setText("wyjdz");
        exitButton.setOnAction(e -> {System.exit(0);});

        ui.getChildren().addAll(starter, exitButton);
        output.getChildren().addAll(left.getUniverse(), right.getUniverse());
        primaryStage.setScene(scene); primaryStage.show();
    }
}