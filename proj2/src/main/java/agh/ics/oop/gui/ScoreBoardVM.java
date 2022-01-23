package agh.ics.oop.gui;

import agh.ics.oop.Player;
import agh.ics.oop.ScoreBoard;
import agh.ics.oop.style.StyledRow;
import agh.ics.oop.style.StyledScrollable;

public class ScoreBoardVM extends StyledScrollable {

    @Override
    public void refreshScrollable() {
        scrollContent.getChildren().clear();
        for (Player player : ScoreBoard.GetPlayers())
            scrollContent.getChildren().addAll(new StyledRow(player.getScore().toString(), player.getNick()));
    }
}
