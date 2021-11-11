package agh.ics.oop;

public class Animal {
    private MapDirection direction;
    private Vector2d position;
    private IWorldMap map;

    public Animal(IWorldMap map) {
        this.direction = MapDirection.NORTH;
        this.position = new Vector2d(2, 2);
        this.map = map;
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.direction = MapDirection.NORTH;
        this.position = initialPosition;
        this.map = map;
    }

    public String toString() { return this.direction.toString(); }

    public void move(MoveDirection direction) {
        switch (direction) {
            case FORWARD -> {
                var newPosition = position.add(this.direction.toUnitVector());
                if (map.canMoveTo(newPosition)) position = newPosition;
            }
            case BACKWARD -> {
                var newPosition = position.add(this.direction.toUnitVector().opposite());
                if (map.canMoveTo(newPosition)) position = newPosition;
            }
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
        }
    }

    public Vector2d getPosition() { return position; }
}
