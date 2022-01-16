package agh.ics.oop.gui;

import agh.ics.oop.Player;
import agh.ics.oop.ScoreBoard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ScoreBoardVM {
    private final ScrollPane scrollPane = new ScrollPane();
    private final VBox scoreBoard = new VBox();

    public ScoreBoardVM() {
        scoreBoard.setAlignment(Pos.CENTER);
        scoreBoard.setPadding(new Insets(10, 10, 10, 10));
        scrollPane.setContent(scoreBoard);
        scrollPane.setMaxHeight(750);
        refreshScoreBoard();
    }

    public ScrollPane GetScoreBoardVM() { return scrollPane; }

    public void refreshScoreBoard() {
        scoreBoard.getChildren().clear();
        for (Player player : ScoreBoard.GetPlayers()) {
            HBox row = new HBox();
            row.setAlignment(Pos.BASELINE_LEFT);
            row.setPadding(new Insets(10, 10, 10, 10));
            row.setSpacing(10);
            row.getChildren().addAll(new Text(player.getScore().toString()), new Text(player.getNick()));
            scoreBoard.getChildren().addAll(row);
        }
    }
}
