package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.sqrt;
import java.util.HashMap;
import java.util.Random;

public class GrassFiled extends AbstractWorldMap {

    private int grassRange;
    private List<Grass> grass;

    public GrassFiled(int quantity) {
        this.grassRange = (int)sqrt(quantity * 10);
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d (0, 0);
        this.animals = new ArrayList<>();

        var grassMap = new HashMap<Vector2d, Grass>();
        var rand = new Random();

        for (int i = 0; i < quantity; i++) {
            while (true) {
                var position = new Vector2d(rand.nextInt(grassRange), rand.nextInt(grassRange));
                if (!grassMap.containsKey(position)) {
                    grassMap.put(position, new Grass(position));
                    updateUpperRight(position);
                    break;
                }
            }
        }

        this.grass = new ArrayList<>(grassMap.values());
    }

    public void updateUpperRight(Vector2d position)
    { this.upperRight = new Vector2d(max(upperRight.x, position.x), max(upperRight.y, position.y)); }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(lowerLeft) && !this.isOccupiedByAnimal(position)) {
            updateUpperRight(position);
            tryEat(position);
            return true;
        }
        else return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animals) if (animal.getPosition().equals(position)) return true;
        for (Grass singleGrass : grass) if (singleGrass.getPosition().equals(position)) return true;
        return false;
    }

    public boolean isOccupiedByAnimal(Vector2d position) {
        for (Animal animal : animals) if (animal.getPosition().equals(position)) return true;
        return false;
    }

    public void plantGrass(Vector2d position) {
        var grassMap = new HashMap<Vector2d, Grass>();
        var rand = new Random();

        for (Grass singleGrass : grass) grassMap.put(singleGrass.getPosition(), singleGrass);

        while (true) {
            var newPosition = new Vector2d(rand.nextInt(grassRange), rand.nextInt(grassRange));
            if (!grassMap.containsKey(newPosition)) {
                grassMap.put(newPosition, new Grass(position));
                updateUpperRight(newPosition);
                break;
            }
        }
    }

    public boolean tryEat(Vector2d position) {
        for (int i = 0; i < grass.size(); i++)
            if (grass.get(i).getPosition().equals(position)) {
                grass.remove(i);
                plantGrass(position);
                return true;
            }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals) if (animal.getPosition().equals(position)) return animal;
        for (Grass singleGrass : grass) if (singleGrass.getPosition().equals(position)) return singleGrass;
        return null;
    }
}
