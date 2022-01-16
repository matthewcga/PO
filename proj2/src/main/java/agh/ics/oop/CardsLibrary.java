package agh.ics.oop;

import java.io.*;
import java.util.HashMap;

public class CardsLibrary {
    private static final HashMap<Integer, Card> cardsMap = new HashMap<>();

    public static void LoadCardsMap() throws IOException {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/CardsLibrary.csv"), "UTF-8"))) {
            for(String line; (line = reader.readLine()) != null;) {
                Card card = new Card(line.split(";"));
                cardsMap.put(card.getId(), card);
            }
        }
    }

    public static int GetCardsAmount() { return cardsMap.size(); }

    public static Card GetCard(int id) { return cardsMap.get(id); }
}
