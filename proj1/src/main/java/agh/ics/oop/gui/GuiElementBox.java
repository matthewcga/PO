package agh.ics.oop.gui;

import javafx.geometry.Pos;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    public VBox vb;

    public GuiElementBox(Image image, int size) {
        ImageView iv = new ImageView(image);
        iv.setFitWidth(size); iv.setFitHeight(size);
        this.vb = new VBox(iv);
        this.vb.setAlignment(Pos.CENTER);
    }


}
