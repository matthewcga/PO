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
        var newPosition = new Vector2d(0, 0);
        final boolean checker = newPosition.x <= 4 && newPosition.x >= 0 && newPosition.y <= 4 && newPosition.y >= 0;

        switch (direction) {
            case FORWARD -> {
                newPosition = this.position.add(this.direction.toUnitVector());
                if (checker) this.position = newPosition;
            }
            case BACKWARD -> {
                newPosition = this.position.add(this.direction.toUnitVector().opposite());
                if (checker) this.position = newPosition;
            }
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
        }
    }


}
