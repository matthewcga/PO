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
    //protected MapBoundary mapBounder = new MapBoundary();
    protected boolean hasBorder;

    protected int deadCount = 0, deadAgeSum = 0, aliveEngSum, aliveChildSum;

    public GrassFiled(int mapSize, int jungelSize, boolean border, int grassEnergy) {
        upperRight = new Vector2d(mapSize, mapSize);
        lowerLeftJungle = new Vector2d((mapSize - jungelSize) / 2, (mapSize - jungelSize) / 2);
        upperRightJungle = new Vector2d((mapSize + jungelSize) / 2, (mapSize + jungelSize) / 2);
        hasBorder = border;
        animals = new ListDictionary<>();
        grassEnergySetting = grassEnergy;
        grass = new HashMap<>();
        for(int i = 0; i < 10; i++) growGrass();
    }

    public boolean place(Animal animal) {
        if (!inBounds(animal.getPosition())) return false;
        animals.put(animal.getPosition(), animal);
        //this.mapBounder.place(animal.getPosition());
        animal.addObserver(this);
        //animal.addObserver(this.mapBounder);
        return true;
    }

    public Vector2d getLowerLeft() { return lowerLeft; } //{ return this.mapBounder.getLowerLeft(); }
    public Vector2d getUpperRight() { return upperRight; } //{ return this.mapBounder.getUpperRight(); }
    public Grass getGrassAt(Vector2d position) { return grass.get(position); }

    public boolean isOccupied(Vector2d position) { return animals.containsKey(position) || grass.containsKey(position); }
    public boolean isOccupiedByAnimal(Vector2d position) { return animals.containsKey(position); }
    public boolean isOccupiedByGrass(Vector2d position) { return grass.containsKey(position); }

    public Object objectAt(Vector2d position) {
        if (!isOccupied(position)) return null;
        else if (animals.containsKey(position)) return animals.get(position);
        else return grass.get(position);
    }
    public Grass grassAt(Vector2d position) { return grass.get(position); }
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
            x = (position.x > upperRight.x)? upperRight.x : (position.x < lowerLeft.x)? lowerLeft.x : position.x;
            y = (position.y > upperRight.y)? upperRight.y : (position.x < lowerLeft.y)? lowerLeft.y : position.y;
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
            var newPosition = new Vector2d(rand.nextInt(upperRight.x + 1), rand.nextInt(upperRight.y + 1));
            if (!isOccupied(newPosition) && !inJungleBounds(newPosition)) {
                grass.put(newPosition, new Grass(newPosition, grassEnergySetting));
                //this.mapBounder.place(newPosition);
                break;
            }
            failCount++;
        }

        failCount = 0;
        while (failCount < failLimit) {
            var newPosition = new Vector2d(rand.nextInt(upperRightJungle.x- lowerLeftJungle.x + 1) + lowerLeftJungle.x , rand.nextInt(upperRightJungle.y- lowerLeftJungle.y + 1) + lowerLeftJungle.y);
            if (!isOccupied(newPosition)) {
                grass.put(newPosition, new Grass(newPosition, grassEnergySetting));
                //this.mapBounder.place(newPosition);
                break;
            }
            failCount++;
        }
    }

    public List<Animal> getAnimals() { return animals.values(); }
    public List<Grass> getGrass() { return new LinkedList(grass.values()); }
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
        Map<String, Integer> genoms = new HashMap<>();

        int topScore = 0; String topGenome = "brak";

        for (Animal a : aliveAnimals) {
            String g = Arrays.toString(a.getGenes());
            if (genoms.containsKey(g)) {
                int score = genoms.get(g);
                if (score > topScore) { topScore = score; topGenome = g; }
                genoms.replace(g, score + 1);
            }
            else genoms.put(g, 1);
        }
        return topGenome + ", wystapil " + topScore + " razy";
    }

    public void killDead() {
        aliveEngSum = 0; aliveChildSum = 0;
        Queue<Vector2d> positions = new LinkedList<>(animals.keys());
        while (!positions.isEmpty()) {
            Vector2d position = positions.poll();
            Queue<Animal> animalsQueue = new LinkedList<>(animalsAt(position));
            while (!animalsQueue.isEmpty()) {
                Animal animal = animalsQueue.poll();
                if (animal.getEnergy() <= 0) {
                    deadCount++; deadAgeSum += animal.getAge();
                    animals.remove(position, animal);
                }
                else { aliveEngSum += animal.getEnergy(); aliveChildSum += animal.getKids(); }
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
                 if (animalsAtPosition.length >= 2 && animalsAtPosition[0].getEnergy() > 1 && animalsAtPosition[1].getEnergy() > 1)
                     place(new Animal(this, animalsAtPosition[0], animalsAtPosition[1]));

                 List<Animal> strongest = new ArrayList();
                 for (Animal a : animalsAtPosition) {
                     strongest.add(a);
                     if (a.getEnergy() != maxEnergy) break;
                 }

                 int devidedEnergy = grassAt(position).getEnergy() / strongest.size();
                 for (Animal a : strongest) a.addEnergy(devidedEnergy);
                 grass.remove(position);
             }
         }
     }
}
