package agh.ics.oop;

import java.util.*;

public class GrassFiled implements IPositionChangeObserver {
    private final Random rand = new Random();
    protected ListDictionary<Vector2d, Animal> animals;
    protected Map<Vector2d, Grass> grass;
    public Vector2d upperRight;
    public Vector2d lowerLeft = new Vector2d(0, 0);
    protected Vector2d upperRightJungle;
    protected Vector2d lowerLeftJungle;
    protected int grassEnergySetting;
    protected boolean hasBorder, magicRuleOn;

    protected int deadCount = 0, deadAgeSum = 0, aliveEngSum, aliveChildSum;

    public GrassFiled(boolean border, boolean magic) {
        upperRight = new Vector2d(AppSettings.Settings.mapSize - 1, AppSettings.Settings.mapSize - 1);
        lowerLeftJungle = new Vector2d((int)Math.floor((AppSettings.Settings.mapSize - AppSettings.Settings.jungleSize) / 2), (int)Math.floor((AppSettings.Settings.mapSize - AppSettings.Settings.jungleSize) / 2));
        upperRightJungle = new Vector2d((int)Math.ceil((AppSettings.Settings.mapSize + AppSettings.Settings.jungleSize) / 2), (int)Math.ceil((AppSettings.Settings.mapSize + AppSettings.Settings.jungleSize) / 2));
        hasBorder = border; magicRuleOn = magic;
        animals = new ListDictionary<>();
        grassEnergySetting = AppSettings.Settings.grassEnergy;
        grass = new HashMap<>();

        for(int i = 0; i < AppSettings.Settings.initPlantCount / 2; i++) growGrass();
    }

    public void place(Animal animal) {
        animals.put(animal.getPosition(), animal);
        animal.addObserver(this);
    }

    public Vector2d getLowerLeft() { return lowerLeft; }
    public Vector2d getUpperRight() { return upperRight; }
    public boolean isOccupied(Vector2d position) { return animals.containsKey(position) || grass.containsKey(position); }
    public boolean isOccupiedByAnimal(Vector2d position) { return animals.containsKey(position); }
    public boolean isOccupiedByGrass(Vector2d position) { return grass.containsKey(position); }
    public HashSet<Animal> animalsAt(Vector2d position) { return animals.get(position); }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) { // assumes correct position is given
        if (oldPosition.equals(newPosition)) return;

        var animalsFound = animals.get(oldPosition);
        for (Animal animal : animalsFound) {
            if (animal.getPosition().equals(newPosition)) {
                animals.remove(oldPosition, animal);
                animals.put(newPosition, animal);
                return;
            }
        }
    }

    public boolean inBounds(Vector2d position) { return (position.follows(lowerLeft) && position.precedes(upperRight)); }
    public boolean inJungleBounds(Vector2d position) { return (position.follows(lowerLeftJungle) && position.precedes(upperRightJungle)); }

    public Vector2d fixVector(Vector2d position) {
        int x, y;
        if (hasBorder)
        {
            x = (position.x > upperRight.x)? upperRight.x : Math.max(position.x, lowerLeft.x);
            y = (position.y > upperRight.y)? upperRight.y : Math.max(lowerLeft.y, position.y);
        }
        else {
            x = (position.x > upperRight.x)? position.x % upperRight.x : (position.x < lowerLeft.x)? upperRight.x - Math.abs(position.x) : position.x;
            y = (position.y > upperRight.y)? position.y % upperRight.y : (position.y < lowerLeft.y)? upperRight.y - Math.abs(position.y) : position.y;
        }
        return new Vector2d(x, y);
    }

    public void growGrass() {
        int failCount = 0, failLimit = 10;
        while (failCount < failLimit) {
            Vector2d newPosition = new Vector2d(rand.nextInt(upperRight.x + 1), rand.nextInt(upperRight.y + 1));
            if (!isOccupied(newPosition) && !inJungleBounds(newPosition)) { grass.put(newPosition, new Grass()); break; }
            failCount++;
        }

        failCount = 0;
        while (failCount < failLimit) {
            Vector2d newPosition = new Vector2d(rand.nextInt(upperRightJungle.x- lowerLeftJungle.x + 1) + lowerLeftJungle.x , rand.nextInt(upperRightJungle.y- lowerLeftJungle.y + 1) + lowerLeftJungle.y);
            if (!isOccupied(newPosition)) { grass.put(newPosition, new Grass()); break; }
            failCount++;
        }
    }

    public List<Animal> getAnimals() { return animals.values(); }
    public List<Grass> getGrass() { while(true) try { return grass.values().stream().toList(); } catch (Exception ignored){} }
    public float getAvgAgeOfDeath() { return (deadCount == 0)? 0 : Math.round((deadAgeSum / deadCount) * 100) / 100; }
    public float getAvgEng() {
        int count = getAnimals().size();
        return (count == 0)? 0 : Math.round((aliveEngSum / count) * 100) / 100;
    }
    public float getAvgKids() {
        int count = getAnimals().size();
        return (count == 0)? 0 : Math.round((aliveChildSum / count) * 100) / 100;
    }
    public String getBestGenome() {
        List<Animal> aliveAnimals =  getAnimals();
        Map<String, Integer> genomes = new HashMap<>();
        int topScore = 0; String topGenome = "brak";

        if (aliveAnimals.size() == 0) return "wszystkie zwierzatka nie zyja!";

        for (Animal a : aliveAnimals) {
            String g = Arrays.toString(a.getGenes());
            if (genomes.containsKey(g)) {
                int score = genomes.get(g) + 1;
                if (score > topScore) { topScore = score; topGenome = g; }
                genomes.replace(g, score);
            }
            else genomes.put(g, 1);
        }
        return (topScore != 0)? topGenome : genomes.keySet().iterator().next();
    }

    public void killDead(int day) {
        aliveEngSum = 0; aliveChildSum = 0;
        Queue<Vector2d> positions = new LinkedList<>(animals.keys());
        while (!positions.isEmpty()) {
            Vector2d position = positions.poll();
            Queue<Animal> animalsQueue = new LinkedList<>(animalsAt(position));
            while (!animalsQueue.isEmpty()) {
                Animal animal = animalsQueue.poll();
                if (animal.getEnergy() <= 0) {
                    deadCount++; deadAgeSum += animal.getAge(); animal.die(day);
                    animals.remove(position, animal);
                }
                else { aliveEngSum += animal.getEnergy(); aliveChildSum += animal.getKidsCount(); }
            }
        }
    }

    public void eatGrassMakeKids() {
         Queue<Vector2d> positions = new LinkedList<>(animals.keys());
         while (!positions.isEmpty()) {
             Vector2d position = positions.poll();
             if (isOccupiedByGrass(position)) {
                 Animal[] animalsAtPosition = animalsAt(position).toArray(new Animal[0]);
                 Arrays.sort(animalsAtPosition, (a1, a2) -> (int)Math.signum(a1.getEnergy() - a2.getEnergy()));

                 int maxEnergy = animalsAtPosition[0].getEnergy();
                 if (animalsAtPosition.length >= 2 && animalsAtPosition[0].getEnergy() > 1 && animalsAtPosition[1].getEnergy() > 1) {
                     Animal newKid = new Animal(this, animalsAtPosition[0], animalsAtPosition[1]);
                     place(newKid);
                     animalsAtPosition[0].linkChild(newKid); animalsAtPosition[1].linkChild(newKid);
                 }


                 List<Animal> strongest = new ArrayList();
                 for (Animal a : animalsAtPosition) {
                     strongest.add(a);
                     if (a.getEnergy() != maxEnergy) break;
                 }

                 int dividedEnergy = AppSettings.Settings.grassEnergy / strongest.size();
                 for (Animal a : strongest) a.addEnergy(dividedEnergy);
                 grass.remove(position);
             }
         }
    }

    public List<Animal> performMagic() {
        if (!magicRuleOn) return null;
        List<Animal> a = animals.values();
        return (a.size() == 5)? a : null;
    }

    public Vector2d getRandomVector() { return new Vector2d(rand.nextInt(upperRight.x), rand.nextInt(upperRight.y)); }
}
