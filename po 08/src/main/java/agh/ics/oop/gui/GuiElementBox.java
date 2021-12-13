package agh.ics.oop.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    public Image image;
    public ImageView imageView;

    public GuiElementBox(String filename) throws FileNotFoundException {
        this.image = new Image(new FileInputStream("src/main/resources/" + filename + ".png"));
    }


}
