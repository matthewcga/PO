package agh.ics.oop;

import static java.lang.Math.sqrt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GrassFiled implements IPositionChangeObserver {
    private final Random rand = new Random();
    protected ListDictionary<Vector2d, Animal> animals;
    protected Map<Vector2d, Grass> grass;
    protected Vector2d upperRight;
    protected Vector2d lowerLeft = new Vector2d(0, 0);
    protected Vector2d upperRightJungle;
    protected Vector2d lowerLeftJungle;
    protected int grassEnergySetting;
    //protected MapBoundary mapBounder = new MapBoundary();
    protected boolean hasBorder;

    public GrassFiled(int mapSize, int jungelSize, boolean border, int grassEnergy) {
        upperRight = new Vector2d(mapSize, mapSize);
        lowerLeftJungle = new Vector2d((mapSize - jungelSize) / 2, (mapSize - jungelSize) / 2);
        upperRightJungle = new Vector2d((mapSize + jungelSize) / 2, (mapSize + jungelSize) / 2);
        hasBorder = border;
        animals = new ListDictionary<>();
        grassEnergySetting = grassEnergy;
        grass = new HashMap<>();
        growGrass();
    }

    public boolean place(Animal animal) throws IllegalArgumentException {
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

    public Object objectAt(Vector2d position) {
        if (!isOccupied(position)) return null;
        else if (animals.containsKey(position)) return animals.get(position);
        else return grass.get(position);
    }

    public List<Animal> animalsAt(Vector2d position) { return animals.get(position); }

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

    public Vector2d fixVector(Vector2d position) { return new Vector2d(position.x % upperRight.x, position.y % upperRight.y); }

    public void growGrass() {
        while (true) {
            var newPosition = new Vector2d(rand.nextInt(upperRight.x), rand.nextInt(upperRight.y));
            if (!isOccupied(newPosition) && !inJungleBounds(newPosition)) {
                grass.put(newPosition, new Grass(newPosition, grassEnergySetting));
                //this.mapBounder.place(newPosition);
                break;
            }
        }

        while (true) {
            var newPosition = new Vector2d(rand.nextInt(upperRightJungle.x- lowerLeftJungle.x) + lowerLeftJungle.x , rand.nextInt(upperRightJungle.y- lowerLeftJungle.y) + lowerLeftJungle.y);
            if (!isOccupied(newPosition)) {
                grass.put(newPosition, new Grass(newPosition, grassEnergySetting));
                //this.mapBounder.place(newPosition);
                break;
            }
        }
    }
}
