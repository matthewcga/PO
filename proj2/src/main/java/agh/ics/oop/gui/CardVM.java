package agh.ics.oop.gui;

import agh.ics.oop.Card;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CardVM {
    private final VBox cardVM = new VBox();

    public CardVM() {
        cardVM.setAlignment(Pos.CENTER);
        cardVM.setPadding(new Insets(10, 10, 10, 10));
    }

    public void updateCardVM(Card card) {
        cardVM.getChildren().clear();
        Label description = new Label(card.getDescription());
        description.setWrapText(true);
        cardVM.getChildren().addAll(new Text(card.getTitle()), description);
    }

    public VBox GetCardVM() { return cardVM; }
}
