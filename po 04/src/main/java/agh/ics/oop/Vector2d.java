package agh.ics.oop;

import static java.lang.Math.*;

public class Vector2d {
        public final int x, y;

        public Vector2d(int x, int y)
        { this.x = x; this.y = y; }

        public String toString() { return "(" + this.x + "," + this.y + ")"; }

        public Boolean precedes(Vector2d other) { return this.x <= other.x && this.y <= other.y; }

        public Boolean follows(Vector2d other) { return this.x >= other.x && this.y >= other.y; }

        public Vector2d upperRight(Vector2d other) { return new Vector2d(max(this.x, other.x), max(this.y, other.y)); }

        public Vector2d lowerLeft(Vector2d other) { return new Vector2d(min(this.x, other.x), min(this.y, other.y)); }

        public Vector2d add(Vector2d other) { return new Vector2d(this.x + other.x, this.y + other.y); }

        public Vector2d subtract(Vector2d other) { return new Vector2d(this.x - other.x, this.y - other.y); }

        public Boolean equals(Vector2d other) { return this.x == other.x && this.y == other.y; }

        public Vector2d opposite() { return new Vector2d(-this.x, -this.y); }
}
