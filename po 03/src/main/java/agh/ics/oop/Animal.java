package agh.ics.oop;

public class Animal {
    private MapDirection direction;
    private Vector2d position;

    public Animal() {
        this.direction = MapDirection.NORTH;
        this.position = new Vector2d(2, 2);
    }

    public String toString()
    { return "direction: " + this.direction.toString() + ", position: " + this.position.toString(); }

    public void move(MoveDirection direction) {
        switch (direction) {
            case FORWARD -> {
                var newPosition = this.position.add(this.direction.toUnitVector());
                if (moveValidation(newPosition)) this.position = newPosition;
            }
            case BACKWARD -> {
                var newPosition = this.position.add(this.direction.toUnitVector().opposite());
                if (moveValidation(newPosition)) this.position = newPosition;
            }
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
        }
    }

    public Boolean moveValidation(Vector2d v)
    { return v.x <= 4 && v.x >= 0 && v.y <= 4 && v.y >= 0; }
}
