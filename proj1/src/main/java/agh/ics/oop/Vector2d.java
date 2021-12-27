package agh.ics.oop;

import java.util.Objects;

public class Vector2d {
        public final int x, y;

        public Vector2d(int x, int y) { this.x = x; this.y = y; }

        public String toString() { return "(" + x + "," + y + ")"; }

        @Override
        public int hashCode() { return Objects.hash(x, y); }
        public boolean precedes(Vector2d other) { return x <= other.x && y <= other.y; }
        public boolean follows(Vector2d other) { return x >= other.x && y >= other.y; }
        public Vector2d add(Vector2d other) { return new Vector2d(x + other.x, y + other.y); }

        @Override
        public boolean equals(Object object) {
                Vector2d other = (Vector2d) object;
                return x == other.x && y == other.y;
        }

        public Vector2d pushVector(int direction) {
                return switch (direction) {
                        case 0 -> add(new Vector2d(0, 1));
                        case 1 -> add(new Vector2d(1, 1));
                        case 2 -> add(new Vector2d(1, 0));
                        case 3 -> add(new Vector2d(1, - 1));
                        case 4 -> add(new Vector2d(0, - 1));
                        case 5 -> add(new Vector2d(- 1, - 1));
                        case 6 -> add(new Vector2d(- 1, 0));
                        case 7 -> add(new Vector2d(- 1, 1));
                        default -> throw new IllegalArgumentException("nieprawidlowy kierunek");
                };
        }
}
