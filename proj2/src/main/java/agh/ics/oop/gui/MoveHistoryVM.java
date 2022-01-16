package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;

public class MoveHistoryVM {
    private final ScrollPane scrollPane = new ScrollPane();
    private final VBox historyBoard = new VBox();

    public MoveHistoryVM() {
        historyBoard.setAlignment(Pos.CENTER);
        historyBoard.setPadding(new Insets(10, 10, 10, 10));
        scrollPane.setContent(historyBoard);
        scrollPane.setMaxHeight(750);
        refreshMoveHistory();
    }

    public ScrollPane GetMoveHistoryVM() { return scrollPane; }

    public void refreshMoveHistory() {
        historyBoard.getChildren().clear();
        for (Pair<Integer, Boolean> move : NewPlayer.GetHistory()) {
            Card card = CardsLibrary.GetCard(move.getKey());
            HBox row = new HBox();
            row.setAlignment(Pos.BASELINE_LEFT);
            row.setPadding(new Insets(10, 10, 10, 10));
            row.setSpacing(10);
            row.getChildren().addAll(new Text(card.getTitle()), new Text(move.getValue()? card.getAccept() : card.getReject()));
            historyBoard.getChildren().addAll(row);
        }
    }
}
