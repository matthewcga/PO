package agh.ics.oop.gui;

import agh.ics.oop.Card;
import agh.ics.oop.style.StyledText;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CardVM {
    private final VBox cardVM = new VBox();
    private final Label description = new Label();
    private final StyledText title = new StyledText(null);
    private final ImageView image = new ImageView();

    public CardVM() {
        description.setWrapText(true);
        description.setMinHeight(200);
        cardVM.setAlignment(Pos.CENTER);
        cardVM.setPadding(new Insets(10, 10, 10, 10));
        cardVM.getChildren().addAll(image, title, description);
    }

    public void updateCardVM(Card card) throws FileNotFoundException {
        image.setImage(new Image(new FileInputStream(card.getImagePath())));
        title.setText(card.getTitle());
        description.setText(card.getDescription());
    }

    public VBox GetCardVM() { return cardVM; }
}
