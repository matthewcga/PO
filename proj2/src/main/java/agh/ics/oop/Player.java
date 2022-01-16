package agh.ics.oop;

import java.util.Objects;

public class Player implements Comparable {
    protected final String nick;
    protected final int score;

    public String getNick() { return nick; }
    public Integer getScore() { return score; }

    public Player(String[] line) { nick = line[0]; score = Integer.parseInt(line[1]); }
    public Player() { nick = NewPlayer.getNick(); score = NewPlayer.getScore(); }

    @Override
    public int hashCode() { return Objects.hash(nick); }

    @Override
    public int compareTo(Object o) {
        Player p = (Player) o;
        return (int) Math.signum(score - p.score);
    }
}
