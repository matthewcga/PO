package agh.ics.oop;

public class Player implements Comparable {
    protected final String nick;
    protected final int score;

    public Player(String[] line) { nick = line[0]; score = Integer.parseInt(line[1]); }
    public Player() { nick = NewPlayer.getNick(); score = NewPlayer.getScore(); }

    @Override
    public int compareTo(Object o) { Player p = (Player) o; return (int) Math.signum(score - p.score); }
    public String getNick() { return nick; }
    public Integer getScore() { return score; }
}
