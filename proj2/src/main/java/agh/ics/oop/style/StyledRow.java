package agh.ics.oop.style;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

public class StyledRow extends HBox {

    public StyledRow(String value1, String value2) {
        this.setAlignment(Pos.BASELINE_LEFT);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setSpacing(10);
        StyledText text1 = new StyledText(value1), text2 = new StyledText(value2);
        text2.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().addAll(text1, text2);
    }
}

