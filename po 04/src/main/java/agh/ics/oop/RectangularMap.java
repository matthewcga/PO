package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.max;

public class RectangularMap implements IWorldMap{

    private int width;
    private int height;
    private Map<Vector2d, Animal> map;

    public RectangularMap(int width, int height)
    { this.width = width; this.height = height; this.map = new HashMap<Vector2d, Animal>(); }

    @Override
    public boolean canMoveTo(Vector2d position)
    { return (0 <= position.x && position.x < width && 0 <= position.y && position.y < height); }

    @Override
    public boolean place(Animal animal) {
        if ()
    }

    @Override
    public boolean isOccupied(Vector2d position)
    { return (this.map.containsValue(position)) }

    @Override
    public Object objectAt(Vector2d position)
    { return (this.map.containsValue(position))? this.map.get(position) : null; }
}
