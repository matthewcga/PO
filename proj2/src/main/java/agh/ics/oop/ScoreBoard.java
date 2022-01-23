package agh.ics.oop;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreBoard {
    private static final List<Player> players = new ArrayList<>();

    public static void LoadPlayersMap() throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/ScoreBoard.csv"))) {
            for(String line; (line = reader.readLine()) != null;) players.add(new Player(line.split(";")));
        }
        Collections.sort(players, Collections.reverseOrder());
    }

    public static void AddNewPlayer() throws IOException {
        players.add(new Player());
        Collections.sort(players, Collections.reverseOrder());
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/ScoreBoard.csv", true));
        writer.write(NewPlayer.getNick() + ";" + NewPlayer.getScore() + ";\n");
        writer.close();
        NewPlayer.disableSave();
    }

    public static List<Player> GetPlayers() { return players; }
}
