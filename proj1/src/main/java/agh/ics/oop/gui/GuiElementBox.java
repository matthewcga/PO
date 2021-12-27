package agh.ics.oop.gui;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GuiElementBox {
    public VBox vb;

    public GuiElementBox(Image image, int size) {
        ImageView iv = new ImageView(image);
        iv.setFitWidth(size); iv.setFitHeight(size);
        this.vb = new VBox(iv); this.vb.setAlignment(Pos.CENTER);
    }


}
