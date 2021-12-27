package agh.ics.oop.gui;

import agh.ics.oop.AppSettings;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class App extends Application {

    public void start(Stage primaryStage) {
        GridPane ui = new GridPane();
        ui.setAlignment(Pos.CENTER);
        Scene scene = new Scene(ui, AppSettings.Settings.windowWidth, AppSettings.Settings.windowHeight);

        SettingsImporter si = new SettingsImporter();
        Button starter = new Button();
        starter.setText("wczytaj ustawienia i rozpocznij symulacje");
        starter.setOnAction(e -> {
            try {
                if (si.ImportSettings().length() != 0) return;

                GridPane gird = new GridPane();
                gird.setAlignment(Pos.CENTER); gird.setHgap(10); gird.setVgap(10);
                gird.add(new UniverseEngine(false, "lewa").getUniverse(), 0, 0);
                gird.add(new UniverseEngine(true, "prawa").getUniverse(), 1, 0);

                ui.getChildren().remove(si.getSettingsUI()); ui.getChildren().remove(starter);
                ui.getChildren().add(gird);
            }
            catch (FileNotFoundException ex) { ex.printStackTrace(); }
        });

        ui.add(starter, 0, 0); ui.add(si.getSettingsUI(), 0, 1);
        primaryStage.setScene(scene); primaryStage.show();
    }
}