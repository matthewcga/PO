package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class UniverseEngine implements IPositionChangeObserver {

    private final VBox universe = new VBox();

    private final GridPane grid = new GridPane();
    private final GrassFiled map;
    private final SimulationEngine engine;
    private final ToggleButton highLight = new ToggleButton();
    private final Button resetSelected = new Button();
    private final ToggleButton playButton = new ToggleButton();
    private final Chart animalCount = new Chart();
    private final Chart grassCount = new Chart();
    private final Text stats = new Text("brak danych przed startem symulacji");
    private final Text bestGenome = new Text("brak danych przed startem symulacji");
    private final Text selectedInfo = new Text("nie wybrano stworka");
    private final Text magicInfo = new Text("brak danych");
    private String bestGenomeValue;
    private Animal selectedAnimal = null;
    private final int tileSize;

    private final Image grass = new Image(new FileInputStream("src/main/resources/grass.png"));
    private final Image grassJ = new Image(new FileInputStream("src/main/resources/grassJ.png"));
    private final Image empty = new Image(new FileInputStream("src/main/resources/empty.png"));
    private final Image emptyJ = new Image(new FileInputStream("src/main/resources/emptyJ.png"));
    private final Image animal010 = new Image(new FileInputStream("src/main/resources/animal10.png"));
    private final Image animal010J = new Image(new FileInputStream("src/main/resources/animal10J.png"));
    private final Image animal100 = new Image(new FileInputStream("src/main/resources/animal30.png"));
    private final Image animal100J = new Image(new FileInputStream("src/main/resources/animal30J.png"));
    private final Image animal150 = new Image(new FileInputStream("src/main/resources/animal60.png"));
    private final Image animal150J = new Image(new FileInputStream("src/main/resources/animal60J.png"));
    private final Image animalInf = new Image(new FileInputStream("src/main/resources/animal90.png"));
    private final Image animalInfJ = new Image(new FileInputStream("src/main/resources/animal90J.png"));
    private final Image animalS = new Image(new FileInputStream("src/main/resources/animalS.png"));
    private final Image animalSJ = new Image(new FileInputStream("src/main/resources/animalSJ.png"));
    private final Image animalG = new Image(new FileInputStream("src/main/resources/animalG.png"));
    private final Image animalGJ = new Image(new FileInputStream("src/main/resources/animalGJ.png"));



    public UniverseEngine(boolean border, String mapName) throws FileNotFoundException {
        grid.setAlignment(Pos.CENTER); //grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        //grid.setGridLinesVisible(true);

        GridPane chartsGrid = new GridPane();
        chartsGrid.setAlignment(Pos.CENTER); chartsGrid.setHgap(10); chartsGrid.setVgap(10);
        chartsGrid.setPadding(new Insets(10, 10, 10, 10));

        GridPane optionsGrid = new GridPane();
        optionsGrid.setAlignment(Pos.BASELINE_LEFT); optionsGrid.setHgap(10); optionsGrid.setVgap(10);
        optionsGrid.setPadding(new Insets(10, 10, 10, 10));

        Button saveStats = new Button();

        tileSize = Math.min(AppSettings.Settings.windowHeight / (2 * AppSettings.Settings.mapSize), 40);
        map = new GrassFiled(border, AppSettings.Settings.isMagicOn);
        engine = new SimulationEngine(mapName, map, this, playButton);

        playButton.setText("start");
        playButton.setOnAction(e -> {
            playButton.setText(playButton.isSelected()? "zatrzymaj" : "start");
            saveStats.setDisable(playButton.isSelected());
            new Thread(engine).start();
        });

        resetSelected.setText("odznacz zwierze");
        resetSelected.setDisable(true);
        resetSelected.setOnAction(e -> {
                selectedAnimal.resetKidsList();
                selectedAnimal = null;
                selectedInfo.setText("nie wybrano stworka");
                resetSelected.setDisable(true);
                if (!playButton.isSelected()) { clearGrid(); makeGrid(); }
            }
        );


        highLight.setText("zaznacz dom. genom");
        highLight.setOnAction(e -> { if (!playButton.isSelected()) { clearGrid(); makeGrid(); } });

        saveStats.setText("zapisz statystyki");
        saveStats.setOnAction(e -> { try { engine.saveFile(); } catch (IOException ex) { ex.printStackTrace(); }});

        magicInfo.setText(AppSettings.Settings.isMagicOn? "magiczna zasada: wl" : "magiczna zasada: wyl");

        chartsGrid.add(animalCount.lineChart, 0, 0);
        chartsGrid.add(grassCount.lineChart, 1, 0);
        chartsGrid.add(makeVb("ilosc zwierzat"), 0, 1);
        chartsGrid.add(makeVb("ilosc trawy"), 1, 1);

        optionsGrid.add(playButton, 0, 0);
        optionsGrid.add(resetSelected, 0, 1, 1, 3);
        optionsGrid.add(saveStats, 0, 4);
        optionsGrid.add(highLight, 0, 5);

        optionsGrid.add(stats, 1, 0);
        optionsGrid.add(selectedInfo, 1, 1, 1, 3);
        optionsGrid.add(magicInfo, 1, 4);
        optionsGrid.add(bestGenome, 1, 5);

        universe.getChildren().addAll(grid, chartsGrid, optionsGrid);

        makeGrid();
    }

    public void clearGrid() {
        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
    }

    public void makeGrid() {
        Vector2d lowerLeft = map.getLowerLeft(), upperRight = map.getUpperRight();
        VBox yx = new VBox(new Label("y/x"));
        yx.setAlignment(Pos.CENTER);
        grid.add(yx, 0, 0, 1, 1);
        grid.getColumnConstraints().add(new ColumnConstraints(tileSize));
        grid.getRowConstraints().add(new RowConstraints(tileSize));
        makeCol(lowerLeft.x, upperRight.x);
        makeRow(lowerLeft.y, upperRight.y);
        makeFields(lowerLeft, upperRight);
    }

    private VBox makeVb(String str) {
        VBox vb = new VBox(new Label(str));
        vb.setAlignment(Pos.CENTER);
        return vb;
    }

    private void makeCol(int l, int r) {
        for (int x = l, i = 1; x <= r; x++, i++) {
            grid.add(makeVb(Integer.toString(x)), i, 0, 1, 1);
            grid.getColumnConstraints().add(new ColumnConstraints(tileSize));
        }
    }

    public void makeRow(int l, int r) {
        for (int y = r, i = 1; y >= l; y--, i++) {
            grid.add(makeVb(Integer.toString(y)), 0, i, 1, 1);
            grid.getRowConstraints().add(new RowConstraints(tileSize));
        }
    }

    public void makeFields(Vector2d lowerLeft, Vector2d upperRight) {
        for (int x = lowerLeft.x, i = 1; x <= upperRight.x; x++, i++)
            for (int y = upperRight.y, j = 1; y >= lowerLeft.y; y--, j++)
                makeFiled(new Vector2d(x, y), i, j);
    }

    public void makeFiled(Vector2d position, int i, int j) {
        if (map.isOccupiedByAnimal(position)) {
            Animal a = safeIteration(position);
            var vb = getGui(getAnimalImage(position, a));
            vb.setOnMouseClicked(e -> {
                selectedAnimal = a; a.resetKidsList();
                selectedInfo.setText(selectedAnimal.getTrackingInfo());
                resetSelected.setDisable(false);
                if (!playButton.isSelected()) { clearGrid(); makeGrid(); }
            });
            grid.add(vb, i, j, 1, 1);
        }
        else if (map.isOccupiedByGrass(position)) grid.add(getGui(getGrassImage(position)), i, j, 1, 1);
        else grid.add(getGui(getEmptyImage(position)), i, j, 1, 1);
    }

    public Animal safeIteration(Vector2d position) { while(true) try { return map.animalsAt(position).iterator().next(); } catch (Exception ignored) {} }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Platform.runLater(() -> {
            clearGrid(); makeGrid();
            bestGenomeValue = map.getBestGenome();
            animalCount.addData(map.getAnimals().size());
            grassCount.addData(map.getGrass().size());
            stats.setText(engine.getStats());
            bestGenome.setText("najpopularniejszy genotyp: " + bestGenomeValue);
            if (AppSettings.Settings.isMagicOn) magicInfo.setText("magiczne zdarzenia: " + engine.getMagicEventsCount() + "/3");
            if (selectedAnimal != null) selectedInfo.setText(selectedAnimal.getTrackingInfo());
        });
    }

    public Image getAnimalImage(Vector2d position, Animal a) {
        boolean inJungle = map.inJungleBounds(position);
        if (a.equals(selectedAnimal)) return (inJungle)? animalSJ : animalS;
        if (checkGenome(a)) return (inJungle)? animalGJ : animalG;
        if (a.getEnergy() <= AppSettings.Settings.initAnimalEnergy * 0.1) return (inJungle)? animal010J : animal010;
        if (a.getEnergy() <= AppSettings.Settings.initAnimalEnergy * 1.0) return (inJungle)? animal100J : animal100;
        if (a.getEnergy() <= AppSettings.Settings.initAnimalEnergy * 1.5) return (inJungle)? animal150J : animal150;
        else return (inJungle)? animalInfJ : animalInf;
    }

    private Image getGrassImage(Vector2d position) { return (map.inJungleBounds(position))? grassJ : grass; }
    private Image getEmptyImage(Vector2d position) { return (map.inJungleBounds(position))? emptyJ : empty; }
    public boolean checkGenome(Animal a){ return highLight.isSelected() && Arrays.toString(a.getGenes()).equals(bestGenomeValue); }
    public VBox getUniverse() { return universe; }
    public VBox getGui(Image image) { return new GuiElementBox(image, tileSize).vb; }
}