package agh.ics.oop;

import java.util.Objects;

import static java.lang.Math.*;

public class Vector2d {
        public final int x, y;

        public Vector2d(int x, int y) { this.x = x; this.y = y; }

        public String toString() { return "(" + x + "," + y + ")"; }

        @Override
        public int hashCode() { return Objects.hash(x, y); }

        public boolean precedes(Vector2d other) { return x <= other.x && y <= other.y; }

        public boolean follows(Vector2d other) { return x >= other.x && y >= other.y; }

        public Vector2d upperRight(Vector2d other) { return new Vector2d(max(x, other.x), max(y, other.y)); }

        public Vector2d lowerLeft(Vector2d other) { return new Vector2d(min(x, other.x), min(y, other.y)); }

        public Vector2d add(Vector2d other) { return new Vector2d(x + other.x, y + other.y); }

        public Vector2d subtract(Vector2d other) { return new Vector2d(x - other.x, y - other.y); }

        @Override
        public boolean equals(Object object) {
                var other = (Vector2d) object;
                return x == other.x && y == other.y;
        }

        public Vector2d opposite() { return new Vector2d(-x, -y); }

        public Vector2d pushVector(int direction) {
                return switch (direction) {
                        case 0 -> new Vector2d(this.x, this.y + 1);
                        case 1 -> new Vector2d(this.x + 1, this.y + 1);
                        case 2 -> new Vector2d(this.x + 1, this.y);
                        case 3 -> new Vector2d(this.x + 1, this.y - 1);
                        case 4 -> new Vector2d(this.x, this.y - 1);
                        case 5 -> new Vector2d(this.x - 1, this.y - 1);
                        case 6 -> new Vector2d(this.x - 1, this.y);
                        case 7 -> new Vector2d(this.x - 1, this.y + 1);
                        default -> throw new IllegalArgumentException("nieprawidłowy kierunek");
                };

        }
}
