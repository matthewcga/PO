package agh.ics.oop;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class Card {
    private final String title;
    private final String description;
    private final String accept;
    private final String reject;
    private final Stats statsOnAccept;
    private final Stats statsOnReject;
    private final int id;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getAccept() { return accept; }
    public String getReject() { return reject; }
    public Stats getStats(boolean move) { return move? statsOnAccept : statsOnReject; }
    public int getId() { return id; }

    public Card(String[] line){
        id = Integer.parseInt(line[0]); title = line[1]; description = line[2]; accept = line[3]; reject = line[4];
        statsOnAccept = new Stats(Arrays.copyOfRange(line, 5, 9));
        statsOnReject = new Stats(Arrays.copyOfRange(line, 9, 13));
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}

