package agh.ics.oop;

public class Stats {
    private int army, gold, food, tech;

    public Stats() { ClearStats(); }

    public Stats(String[] line) {
        army = Integer.parseInt(line[0]);
        gold = Integer.parseInt(line[1]);
        food = Integer.parseInt(line[2]);
        tech = Integer.parseInt(line[3]);
    }

    public int getArmy() { return army; }
    public int getGold() { return gold; }
    public int getFood() { return food; }
    public int getTech() { return tech; }

    public boolean UpdateStats(Stats stats) {
        army = Math.min(army + stats.getArmy(), 100);
        gold = Math.min(gold + stats.getGold(), 100);
        food = Math.min(food + stats.getFood(), 100);
        tech = Math.min(tech + stats.getTech(), 100);
        return army > 0 && gold > 0 && food > 0 && tech > 0;
    }

    public void ClearStats() { army = 50; gold = 50; food = 50; tech = 50; }
}
