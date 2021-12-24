package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class UniverseEngine implements IPositionChangeObserver {

    private VBox universe = new VBox();

    GridPane grid = new GridPane();
    GrassFiled map;
    SimulationEngine engine;

    ToggleButton button = new ToggleButton();
    ToggleButton highLight = new ToggleButton();

    Chart animalCount = new Chart();
    Chart grassCount = new Chart();
    Text stats = new Text("brak danych");
    Text bestGenome = new Text("brak danych");
    String bestGenomeValue;

    Animal selectedAnimal = null;
    Text selectedInfo = new Text("nie wybrano stworka");

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
    Image animalS = new Image(new FileInputStream("src/main/resources/animalS.png"));
    Image animalSJ = new Image(new FileInputStream("src/main/resources/animalSJ.png"));

    int day = 0, tileSize;

    AppSettings.Settings settings;

    public UniverseEngine(boolean border, boolean magic, String mapName) throws FileNotFoundException {
        HBox hbCharts = new HBox(); HBox hbOptions = new HBox(); VBox vbOptionsRow1 = new VBox(); VBox vbOptionsRow2 = new VBox();
        Button resetSelected = new Button();
        Button saveStats = new Button();

        tileSize = (int)(settings.windowHeight / (2 * settings.mapSize));

        map = new GrassFiled(border, magic);
        engine = new SimulationEngine(mapName, map, this, button);
        grid.setGridLinesVisible(true);

        button.setText("start/pauza"); resetSelected.setText("odznacz zwierze");
        highLight.setText("zaznacz dom. genom"); saveStats.setText("zapisz statystyki");

        button.setOnAction(e -> { new Thread(engine).start(); });
        resetSelected.setOnAction(e -> { selectedAnimal.stopTracking(); selectedAnimal = null; selectedInfo.setText("nie wybrano storka");});
        //highLight.setOnAction(e -> {});
        saveStats.setOnAction(e -> { try { engine.saveFile(); } catch (IOException ex) { ex.printStackTrace(); }});

        vbOptionsRow1.getChildren().addAll(stats, bestGenome, selectedInfo);
        vbOptionsRow2.getChildren().addAll(button, resetSelected, saveStats, highLight);
        hbCharts.getChildren().addAll(animalCount.lineChart, grassCount.lineChart);
        hbOptions.getChildren().addAll(vbOptionsRow2, vbOptionsRow1);
        universe.getChildren().addAll(grid, hbCharts, hbOptions);
        makeGrid();
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
        grid.getColumnConstraints().add(new ColumnConstraints(tileSize));
        grid.getRowConstraints().add(new RowConstraints(tileSize));
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
            grid.getColumnConstraints().add(new ColumnConstraints(tileSize));
        }
    }

    public void makeRow(int l, int r) {
        for (int y = r, i = 1; y >= l; y--, i++) {
            VBox vb = makeVb(y);
            grid.add(vb, 0, i, 1, 1);
            grid.getRowConstraints().add(new RowConstraints(tileSize));
        }
    }

    public void makeFileds(Vector2d lowerLeft, Vector2d upperRight) {
        for (int x = lowerLeft.x, i = 1; x <= upperRight.x; x++, i++) {
            for (int y = upperRight.y, j = 1; y >= lowerLeft.y; y--, j++) makeFiled(new Vector2d(x, y), i, j);
        }
    }

    public void makeFiled(Vector2d position, int i, int j) {
        if (map.isOccupiedByAnimal(position)) {
            Animal a = map.animalsAt(position).iterator().next();
            var vb = getVb(getAnimalImage(position, a.getEnergy(), checkGenome(a)));
            vb.setOnMouseClicked(e -> { selectedAnimal = a; a.startTracking(); selectedInfo.setText(selectedAnimal.getTrackingInfo());});
            grid.add(vb, i, j, 1, 1);
        }
        else if (map.isOccupiedByGrass(position)) grid.add(getVb(getGrassImage(position)), i, j, 1, 1);
        else grid.add(getVb(getEmptyImage(position)), i, j, 1, 1);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Platform.runLater(() -> {
            if (day % settings.refreshRate == 0) {
                bestGenomeValue = map.getBestGenome();

                Node node = grid.getChildren().get(0);
                clearGrid();
                grid.getChildren().add(node);
                makeGrid();

                animalCount.addData(map.getAnimals().size());
                grassCount.addData(map.getGrass().size());
                stats.setText(engine.getStats());
                bestGenome.setText("najpopularniejszy genotyp: " + bestGenomeValue);
                if (selectedAnimal != null) selectedInfo.setText(selectedAnimal.getTrackingInfo());
            }
            day++;
        });
    }

    public Image getAnimalImage(Vector2d position, int energy, boolean selected) {
        boolean inJungle = map.inJungleBounds(position);
        if (selected) return (inJungle)? animalSJ : animalS;
        if (energy <= 50) return (inJungle)? animal10J : animal10;
        if (energy <= 100) return (inJungle)? animal30J : animal30;
        if (energy <= 150) return (inJungle)? animal60J : animal60;
        else return (inJungle)? animal90J : animal90;
    }

    private Image getGrassImage(Vector2d position) { return (map.inJungleBounds(position))? grassJ : grass; }
    private Image getEmptyImage(Vector2d position) { return (map.inJungleBounds(position))? emptyJ : empty ; }

    public boolean checkGenome(Animal a){
        var l = Arrays.toString(a.getGenes());
        var r = bestGenomeValue;
        var isSlc = highLight.isSelected();
        return isSlc && l.equals(r) ;
    }

    public VBox getUniverse() { return universe; }

    public VBox getVb(Image image) { return new GuiElementBox(image, tileSize).vb; }
}