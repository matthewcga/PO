package agh.ics.oop.gui;


import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class App extends Application {

    GridPane grid = new GridPane();
    GuiElementBox up = new GuiElementBox("up");
    GuiElementBox down = new GuiElementBox("down");
    GuiElementBox left = new GuiElementBox("left");
    GuiElementBox right = new GuiElementBox("right");
    GuiElementBox grass = new GuiElementBox("grass");

    public App() throws FileNotFoundException {
    }

    public void start(Stage primaryStage) {
        MoveDirection[] directions = new OptionsParser().parse(getParameters().getRaw().toArray(String[]::new));
        AbstractWorldMap map = new GrassFiled(10); //new RectangularMap(20, 20);
        Vector2d[] positions = { new Vector2d(0,0), new Vector2d(1,0)};
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        VBox[][] vBoxMap = GetVBoxMap(map);

        for (int i = 0; i < vBoxMap.length; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(35));
            for (int j = 0; j < vBoxMap[0].length; j++) {
                if (i == 0) grid.getRowConstraints().add(new RowConstraints(35));
                grid.add(vBoxMap[i][j], i, j);
                GridPane.setHalignment(vBoxMap[i][j], HPos.CENTER);
            }
        }

        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public VBox[][] GetVBoxMap(AbstractWorldMap map) {
        Vector2d lowerLeft = map.getLowerLeft();
        Vector2d upperRight = map.getUpperRight();
        VBox[][] output = new VBox[upperRight.y - lowerLeft.y + 2][upperRight.x- lowerLeft.x + 2];

        output[0][0] = new VBox(new Label("x/y"));
        for (int j = upperRight.x, i = 1; j >= lowerLeft.x; j--, i++) output[0][i] = new VBox(new Label(Integer.toString(j)));
        for (int j = lowerLeft.y, i = 1; j <= upperRight.y; j++, i++) output[i][0] = new VBox(new Label(Integer.toString(j)));

        for (int i = 1, y = lowerLeft.y; i < output.length; i++, y++)
            for (int j = 1, x = lowerLeft.x; j < output[0].length; j++, x++)
                output[i][output[0].length - j] = GetVBox(map.objectAt(new Vector2d(x,y)));

        return output;
    }

    public VBox GetVBox(Object o) {
        if (o == null) return new VBox(new Label(""));
        else if (Animal.class.equals(o.getClass())) {
            Animal a = (Animal) o;
            return new VBox(GetAnimal(a), new Label(a.getPosition().toString()));
        }
        else {
            Grass g = (Grass) o;
            return new VBox(GetImageView(this.grass.image), new Label(g.getPosition().toString()));
        }
    }

    public ImageView GetAnimal(Animal a) {
        return switch (a.getDirection().toFilename()) {
            case "up" -> GetImageView(this.up.image);
            case "down" -> GetImageView(this.down.image);
            case "left" -> GetImageView(this.left.image);
            case "right" -> GetImageView(this.right.image);
            default -> throw new IllegalArgumentException("bledny kierunek zwierzecia w GetAnimal()");
        };
    }

    public ImageView GetImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        return imageView;
    }
}
