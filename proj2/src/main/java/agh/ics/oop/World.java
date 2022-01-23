package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;
import static agh.ics.oop.CardsLibrary.LoadCardsMap;
import static agh.ics.oop.ImageLibrary.LoadImages;
import static agh.ics.oop.ScoreBoard.LoadPlayersMap;

public class World {
    public static void main(String[] args) {
        try { LoadCardsMap(); LoadPlayersMap(); LoadImages(); Application.launch(App.class); }
        catch (Exception ex) { CatchException(ex); }
        System.exit(0);
    }

    public static void CatchException(Exception ex)
    { System.out.println(ex.getMessage()); ex.printStackTrace(); System.exit(1); }
}
