package agh.ics.oop.gui;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Chart{
    private int counter = 0;
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    public final LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);
    private XYChart.Series series = new XYChart.Series();

    public Chart() { lineChart.getData().add(series);}

    public void addData(int n) {
        series.getData().add(new XYChart.Data(counter, n));
        counter++;
    }
}
