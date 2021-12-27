package agh.ics.oop.gui;

import agh.ics.oop.AppSettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class SettingsImporter {
    private final TextField mapSize = new TextField();
    private final TextField jungleSize = new TextField();
    private final TextField initAnimalCount = new TextField();
    private final TextField initAnimalEnergy = new TextField();
    private final TextField grassEnergy = new TextField();
    private final TextField energyLoss = new TextField();
    private final TextField refreshRate = new TextField();
    private final TextField plantsCount = new TextField();
    private final Label importErrors = new Label();
    private final GridPane grid = new GridPane();
    private final CheckBox magic  = new CheckBox();

    public SettingsImporter() {
        magic.setAllowIndeterminate(true);
        grid.setAlignment(Pos.CENTER); grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        grid.add(new Label("zmiana wartosci domyslnych symulacji"), 1, 0, 2, 1);
        grid.add(new Label("rozmiar mapy"), 1, 2);
        grid.add(new Label("rozmiar dzungli"), 3, 2);
        grid.add(new Label("poczatkowa liczba zwierzat"), 1, 3);
        grid.add(new Label("poczatkowa ilosc energii zwerzatka"), 3, 3);
        grid.add(new Label("energia rosliny"), 1, 4);
        grid.add(new Label("koszt ruchu zwierzecia"), 3, 4);
        grid.add(new Label("maginczna ewolucja"), 1, 5);
        grid.add(new Label("okres odswiezania mapy (w dniach)"), 3, 5);
        grid.add(new Label("poczatkowa liczba roslin"), 1, 6);
        grid.add(mapSize, 2, 2);
        grid.add(jungleSize, 4, 2);
        grid.add(initAnimalCount, 2, 3);
        grid.add(initAnimalEnergy, 4, 3);
        grid.add(grassEnergy, 2, 4);
        grid.add(energyLoss, 4, 4);
        grid.add(magic, 2, 5);
        grid.add(refreshRate, 4, 5);
        grid.add(plantsCount, 2, 6);
        grid.add(importErrors, 1, 7, 2, 3);
    }

    public String ImportSettings() {
        AppSettings.Settings.UpdateSettings( tryParseInt(mapSize.getText(), AppSettings.Settings.mapSize), tryParseInt(jungleSize.getText(), AppSettings.Settings.jungleSize),
                tryParseInt(initAnimalCount.getText(), AppSettings.Settings.initAnimalCount), tryParseInt(initAnimalEnergy.getText(), AppSettings.Settings.initAnimalEnergy),
                tryParseInt(grassEnergy.getText(), AppSettings.Settings.grassEnergy), tryParseInt(energyLoss.getText(), AppSettings.Settings.energyLoss),
                magic.isIndeterminate()? magic.isSelected() : AppSettings.Settings.isMagicOn, tryParseInt(refreshRate.getText(), AppSettings.Settings.refreshRate),
                tryParseInt(plantsCount.getText(), AppSettings.Settings.initPlantCount)
        );
        importErrors.setText(AppSettings.Settings.validate());
        return importErrors.getText();
    }

    public GridPane getSettingsUI() { return grid; }

    public int tryParseInt(String value, int defaultValue) {
        if (value.length() == 0) return defaultValue;
        try { return Integer.parseInt(value); }
        catch (NumberFormatException e) { return defaultValue; }
    }
}
