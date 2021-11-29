package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal {
    private MapDirection direction;
    private Vector2d position;
    private IWorldMap map;
    private List<IPositionChangeObserver> observers;

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.direction = MapDirection.NORTH;
        this.position = initialPosition;
        this.map = map;
        this.observers = new ArrayList<>();
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case FORWARD -> {
                var newPosition = position.add(this.direction.toUnitVector());
                if (map.canMoveTo(newPosition)) { positionChanged(position, newPosition); position = newPosition; }
            }
            case BACKWARD -> {
                var newPosition = position.add(this.direction.toUnitVector().opposite());
                if (map.canMoveTo(newPosition)) { positionChanged(position, newPosition); position = newPosition; }
            }
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
        }
    }

    public String toString() { return this.direction.toString(); }

    public Vector2d getPosition() { return position; }

    public void addObserver(IPositionChangeObserver observer) { observers.add(observer); }

    public void removeObserver(IPositionChangeObserver observer) { observers.remove(observer); }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition)
    { for (var observer : observers) observer.positionChanged(oldPosition, newPosition); }
}
