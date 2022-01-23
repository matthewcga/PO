package agh.ics.oop;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class NewPlayer {
    private static int score;
    private static String nick;
    private static final List<Pair<Integer, Boolean>> history = new ArrayList<>();
    private static boolean canSave;
    private final Stats stats = new Stats();

    public NewPlayer(String nickName) {
        nick = nickName;
        score = 0;
        history.clear();
        stats.ClearStats();
        canSave = true;
    }

    public static String getNick() { return nick; }
    public static Integer getScore() { return score; }
    public Stats getStats() { return stats; }
    public static List<Pair<Integer, Boolean>> GetHistory() { return history; }
    public static void disableSave() { canSave = false; }
    public static boolean getCanSave() { return canSave; }

    public boolean MakeMove(Card card, Boolean move) {
        score++;
        history.add(new Pair(card.getId(), move));
        return stats.UpdateStats(card.getStats(move));
    }

}
