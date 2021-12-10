package agh.ics.oop.gui;


import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class App extends Application {

    GridPane grid = new GridPane();

    public void start(Stage primaryStage) {

        MoveDirection[] directions = new OptionsParser().parse(getParameters().getRaw().toArray(String[]::new));
        AbstractWorldMap map = new GrassFiled(10); //new RectangularMap(20, 20);
        Vector2d[] positions = { new Vector2d(0,0), new Vector2d(1,0)};
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        String[][] gridMap = GetGridMap(map);

        for (int i = 0; i < gridMap.length; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(35));
            for (int j = 0; j < gridMap[0].length; j++) {
                if (i == 0) grid.getRowConstraints().add(new RowConstraints(35));
                Label label = new Label(gridMap[i][j]);
                grid.add(label, i, j);
                GridPane.setHalignment(label, HPos.CENTER);
            }
        }

        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public String[][] GetGridMap(AbstractWorldMap map) {
        Vector2d lowerLeft = map.getLowerLeft();
        Vector2d upperRight = map.getUpperRight();
        String[][] output = new String[upperRight.y - lowerLeft.y + 2][upperRight.x- lowerLeft.x + 2];

        output[0][0] = "y/x";
        for (int j = upperRight.x, i = 1; j >= lowerLeft.x; j--, i++) output[0][i] = Integer.toString(j);
        for (int j = lowerLeft.y, i = 1; j <= upperRight.y; j++, i++) output[i][0] = Integer.toString(j);

        for (int i = 1, y = lowerLeft.y; i < output.length; i++, y++)
            for (int j = 1, x = lowerLeft.x; j < output[0].length; j++, x++)
            {
                Object o = map.objectAt(new Vector2d(x,y));
                output[i][output[0].length - j] = (o == null)? " " : o.toString();
            }

        return output;
    }
}
