package agh.ics.oop;

import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap {
    protected List<Animal> animals;
    protected Vector2d upperRight;
    protected Vector2d lowerLeft;
    protected MapVisualizer mapVisualizer = new MapVisualizer(this);

    @Override
    public boolean place(Animal animal) {
        if (this.canMoveTo(animal.getPosition())) return animals.add(animal);
        else return false;
    }

    public String toString() { return mapVisualizer.draw(lowerLeft, upperRight); }
}
