package agh.ics.oop.gui;


import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class App extends Application implements IPositionChangeObserver {

    Stage stage;
    GridPane grid = new GridPane();
    AbstractWorldMap map = new GrassFiled(10); //new RectangularMap(20, 20);
    Image up = new Image(new FileInputStream("src/main/resources/up.png"));
    Image down = new Image(new FileInputStream("src/main/resources/down.png"));
    Image left = new Image(new FileInputStream("src/main/resources/left.png"));
    Image right = new Image(new FileInputStream("src/main/resources/right.png"));
    Image grass = new Image(new FileInputStream("src/main/resources/grass.png"));

    public App() throws FileNotFoundException { }

    public void start(Stage primaryStage) {
        Vector2d[] positions = {new Vector2d(0, 0), new Vector2d(1, 0)};
        SimulationEngine engine = new SimulationEngine(map, positions, this);
        OptionsParser op = new OptionsParser(); VBox vb = new VBox(); HBox hb = new HBox();
        Scene scene = new Scene(vb, 800, 800); Button button = new Button(); TextField tf = new TextField();
        grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10, 10, 10, 10));
        stage = primaryStage;
        button.setText("start");
        button.setOnAction(id -> { engine.setDirections(op.parse(tf.getText().split(","))); new Thread(engine).start();});
        hb.getChildren().addAll(tf, button); vb.getChildren().addAll(grid, hb);
        makeGrid();
        primaryStage.setScene(scene); primaryStage.show();
    }

    public void clearGrid() {
        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
    }

    public void makeGrid() {
        Vector2d lowerLeft = map.getLowerLeft();
        Vector2d upperRight = map.getUpperRight();
        VBox yx = new VBox(new Label("y/x"));
        yx.setAlignment(Pos.CENTER);
        grid.add(yx, 0, 0, 1, 1);
        grid.getColumnConstraints().add(new ColumnConstraints(40));
        grid.getRowConstraints().add(new RowConstraints(40));
        makeCol(lowerLeft.x, upperRight.x);
        makeRow(lowerLeft.y, upperRight.y);
        makeFileds(lowerLeft, upperRight);
    }

    private VBox makeVb(int i) {
        VBox vb = new VBox(new Label(Integer.toString(i)));
        vb.setAlignment(Pos.CENTER);
        return vb;
    }

    private void makeCol(int l, int r) {
        for (int x = l, i = 1; x <= r; x++, i++) {
            VBox vb = makeVb(x);
            grid.add(vb, i, 0, 1, 1);
            grid.getColumnConstraints().add(new ColumnConstraints(40));
        }
    }

    public void makeRow(int l, int r) {
        for (int y = r, i = 1; y >= l; y--, i++) {
            VBox vb = makeVb(y);
            grid.add(vb, 0, i, 1, 1);
            grid.getRowConstraints().add(new RowConstraints(40));
        }
    }

    public void makeFileds(Vector2d lowerLeft, Vector2d upperRight) {
        for (int x = lowerLeft.x, i = 1; x <= upperRight.x; x++, i++) {
            for (int y = upperRight.y, j = 1; y >= lowerLeft.y; y--, j++) {
                Vector2d position = new Vector2d(x, y); Image image; String label; Object o = map.objectAt(position);
                if (o != null) {
                    if (Animal.class.equals(o.getClass()))
                    { Animal a = (Animal) o; image = GetAnimalImage((Animal) o); label = a.getPosition().toString(); }
                    else
                    { image = grass; label = "Grass"; }
                    grid.add(new GuiElementBox(label, image).vb, i, j, 1, 1);
                }
            }
        }
    }

    public Image GetAnimalImage(Animal a) {
        return switch (a.getDirection().toString()) {
            case "^" -> this.up;
            case "v" -> this.down;
            case "<" -> this.left;
            case ">" -> this.right;
            default -> throw new IllegalArgumentException("bledny kierunek zwierzecia w GetAnimal()");
        };
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Platform.runLater(() -> {
            Node node = grid.getChildren().get(0);
            clearGrid();
            grid.getChildren().add(node);
            makeGrid();
        });
    }
}