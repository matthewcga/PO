package agh.ics.oop;

public enum MapDirection {
    NORTH, SOUTH, WEST, EAST;

    public String toString() {
        return switch (this) {
            case NORTH -> "North";
            case SOUTH -> "South";
            case WEST -> "West";
            case EAST -> "East";
        };
    }

    public MapDirection next() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    public MapDirection previous() {
        return switch (this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(1, 0);
            case EAST -> new Vector2d(0, 1);
            case SOUTH -> new Vector2d(-1, 0);
            case WEST -> new Vector2d(0, -1);
        };
    }
}
