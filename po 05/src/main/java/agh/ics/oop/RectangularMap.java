package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class RectangularMap implements IWorldMap{

    private Vector2d upperRight;
    private Vector2d lowerLeft;
    private List<Animal> animals;
    private MapVisualizer mapVisualizer = new MapVisualizer(this);

    public RectangularMap(int width, int height)
    { this.upperRight = new Vector2d(max(0, width), max(0, height)); this.lowerLeft = new Vector2d(0, 0); this.animals = new ArrayList<>(); }

    @Override
    public boolean canMoveTo(Vector2d position)
    { return (position.follows(lowerLeft) && position.precedes(upperRight) && !this.isOccupied(position)); }

    @Override
    public boolean place(Animal animal) {
        if (this.canMoveTo(animal.getPosition())) return animals.add(animal);
        else return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animals) if (animal.getPosition().equals(position)) return true;
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals) if (animal.getPosition().equals(position)) return animal;
        return null;
    }

    public String toString() { return mapVisualizer.draw(lowerLeft, upperRight); }
}
