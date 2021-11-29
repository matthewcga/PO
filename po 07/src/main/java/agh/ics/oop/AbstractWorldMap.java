package agh.ics.oop;

import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected Map<Vector2d, Object> objects;
    protected Vector2d upperRight;
    protected Vector2d lowerLeft;
    protected MapVisualizer mapVisualizer = new MapVisualizer(this);

    public boolean place(Animal animal) throws IllegalArgumentException {
        if (!this.canMoveTo(animal.getPosition())) throw new IllegalArgumentException("nie można dodać objektu na zajęte pole: " + animal.getPosition());
        objects.put(animal.getPosition(), animal);
        animal.addObserver(this);
        return true;
    }

    public boolean canMoveTo(Vector2d position)
    { return (position.follows(lowerLeft) && position.precedes(upperRight) && !isOccupied(position)); }

    public String toString() { return mapVisualizer.draw(lowerLeft, upperRight); }

    public boolean isOccupied(Vector2d position) { return objects.containsKey(position); }

    public Object objectAt(Vector2d position) { return (isOccupied(position))? objects.get(position) : null; }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) { // assumes correct position is given
        Object object = objectAt(oldPosition);
        objects.remove(oldPosition);
        objects.put(newPosition, object);
    }
}
