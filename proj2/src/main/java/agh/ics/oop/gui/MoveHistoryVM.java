package agh.ics.oop.gui;

import agh.ics.oop.*;
import agh.ics.oop.style.StyledRow;
import agh.ics.oop.style.StyledScrollable;
import javafx.util.Pair;

public class MoveHistoryVM extends StyledScrollable {
    @Override
    public void refreshScrollable() {
        scrollContent.getChildren().clear();
        for (Pair<Integer, Boolean> move : NewPlayer.GetHistory()) {
            Card card = CardsLibrary.GetCard(move.getKey());
            scrollContent.getChildren().addAll(new StyledRow(card.getTitle(), move.getValue()? card.getAccept() : card.getReject()));
        }
    }
}
