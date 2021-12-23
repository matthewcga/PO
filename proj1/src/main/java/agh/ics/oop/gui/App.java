package agh.ics.oop.gui;


import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class App extends Application implements IPositionChangeObserver {

    Stage stage;
    GridPane grid = new GridPane();
    GrassFiled map;

    Chart animalCount = new Chart();
    Chart grassCount = new Chart();
    Text avgDeadAge = new Text("brak danych");
    Text avgEnergy = new Text("brak danych");
    Text avgKids = new Text("brak danych");
    Text bestGenome = new Text("brak danych");

    Image grass = new Image(new FileInputStream("src/main/resources/grass.png"));
    Image grassJ = new Image(new FileInputStream("src/main/resources/grassJ.png"));
    Image empty = new Image(new FileInputStream("src/main/resources/empty.png"));
    Image emptyJ = new Image(new FileInputStream("src/main/resources/emptyJ.png"));
    Image animal10 = new Image(new FileInputStream("src/main/resources/animal10.png"));
    Image animal10J = new Image(new FileInputStream("src/main/resources/animal10J.png"));
    Image animal30 = new Image(new FileInputStream("src/main/resources/animal30.png"));
    Image animal30J = new Image(new FileInputStream("src/main/resources/animal30J.png"));
    Image animal60 = new Image(new FileInputStream("src/main/resources/animal60.png"));
    Image animal60J = new Image(new FileInputStream("src/main/resources/animal60J.png"));
    Image animal90 = new Image(new FileInputStream("src/main/resources/animal90.png"));
    Image animal90J = new Image(new FileInputStream("src/main/resources/animal90J.png"));
    //Color empty = Color.LIGHTGREEN;
    //Color jungle = Color.DARKGREEN;
    int year = 0;



    public App() throws FileNotFoundException { }

    public void start(Stage primaryStage) {
        VBox vb = new VBox(); HBox hb = new HBox();
        Scene scene = new Scene(vb, 1600, 1000); ToggleButton button = new ToggleButton(); //TextField tf = new TextField();


        map = new GrassFiled(25, 15, false, 40); //new RectangularMap(20, 20);
        SimulationEngine engine = new SimulationEngine(map, 50, 300,this, 20, button);

        grid = new GridPane();
        grid.setGridLinesVisible(true);
        //grid.setPadding(new Insets(0, 0, 0, 0));
        stage = primaryStage;

        button.setText("start/pause");
        button.setOnAction(id -> { new Thread(engine).start(); });
        hb.getChildren().addAll(grid, animalCount.lineChart, grassCount.lineChart);
        vb.getChildren().addAll(hb, avgDeadAge, avgEnergy, avgKids, bestGenome, button);
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
        grid.getColumnConstraints().add(new ColumnConstraints(20));
        grid.getRowConstraints().add(new RowConstraints(20));
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
            grid.getColumnConstraints().add(new ColumnConstraints(20));
        }
    }

    public void makeRow(int l, int r) {
        for (int y = r, i = 1; y >= l; y--, i++) {
            VBox vb = makeVb(y);
            grid.add(vb, 0, i, 1, 1);
            grid.getRowConstraints().add(new RowConstraints(20));
        }
    }

    public void makeFileds(Vector2d lowerLeft, Vector2d upperRight) {
        for (int x = lowerLeft.x, i = 1; x <= upperRight.x; x++, i++) {
            for (int y = upperRight.y, j = 1; y >= lowerLeft.y; y--, j++) makeFiled(new Vector2d(x, y), i, j);
        }
    }

    public void makeFiled(Vector2d position, int i, int j) {
        if (map.isOccupiedByAnimal(position)) grid.add(new GuiElementBox(getAnimalImage(position, map.animalsAt(position).iterator().next().getEnergy())).vb, i, j, 1, 1);
        else if (map.isOccupiedByGrass(position)) grid.add(new GuiElementBox(getGrassImage(position)).vb, i, j, 1, 1);
        //else grid.add(new GuiElementBox(getEmptyImage(position)).vb, i, j, 1, 1);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Platform.runLater(() -> {
            if (year % 10 == 0) {
                Node node = grid.getChildren().get(0);
                clearGrid();
                grid.getChildren().add(node);
                makeGrid();

                animalCount.addData(map.getAnimals().size());
                grassCount.addData(map.getGrass().size());
                avgEnergy.setText("srednia energia: " + map.getAvgEng());
                avgDeadAge.setText("sredni wiek smierci: " + map.getAvgAgeOfDeath());
                avgKids.setText("srednia ilosc dzieci: " + map.getAvgKids());
                bestGenome.setText("najpopularniejszy genotyp: " + map.getBestGenome());
            }
            year++;
        });
    }

    public Image getAnimalImage(Vector2d position, int energy) {
        boolean inJungle = map.inJungleBounds(position);
        if (energy <= 50) return (inJungle)? animal10J : animal10;
        if (energy <= 100) return (inJungle)? animal30J : animal30;
        if (energy <= 150) return (inJungle)? animal60J : animal60;
        else return (inJungle)? animal90J : animal90;
    }

    private Image getGrassImage(Vector2d position) { return (map.inJungleBounds(position))? grassJ : grass; }

    private Image getEmptyImage(Vector2d position) { return (map.inJungleBounds(position))? emptyJ : empty ; }
}