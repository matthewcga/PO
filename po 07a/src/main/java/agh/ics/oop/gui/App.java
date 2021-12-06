package agh.ics.oop.gui;


import agh.ics.oop.*;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {


    IWorldMap map = new GrassFiled(10);

    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();

        //var args = new String[] {"f", "r", "f", "f", "r", "f", "f", "f", "f", "l", "r", "f", "f", "f", "f", "f", "f", "f", "f", "f"};
        //getParameters().getRaw();
        MoveDirection[] directions = new OptionsParser().parse(getParameters().getRaw().toArray(String[]::new));
        IWorldMap map = new GrassFiled(10); //new RectangularMap(20, 20);
        Vector2d[] positions = { new Vector2d(0,0), new Vector2d(1,0)};
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();



        var label2 = new Label(map.toString());



        grid.add(label2, 2, 2);
        //grid.add(label, 0, 0);
        grid.setGridLinesVisible(true);

        Scene scene2 = new Scene(grid, 400, 400);

        //grid.getColumnConstraints().add(new ColumnConstraints(map.));
        //grid.getRowConstraints().add(new RowConstraints(height));

        primaryStage.setScene(scene2);
        primaryStage.show();
    }
}
