package agh.ics.oop.gui;

import agh.ics.oop.AppSettings;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Chart{
    private int counter = 0;
    private final NumberAxis xAxis = new NumberAxis(), yAxis = new NumberAxis();
    public final LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);
    private XYChart.Series series = new XYChart.Series();

    public Chart() { lineChart.getData().add(series); lineChart.setCreateSymbols(false); }
    public void addData(int n) { series.getData().add(new XYChart.Data(counter * AppSettings.Settings.refreshRate, n)); counter++; }
}
