package agh.ics.oop;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static java.lang.Math.max;

public class RectangularMap extends AbstractWorldMap {

    public RectangularMap(int width, int height) {
        upperRight = new Vector2d(max(0, width), max(0, height));
        lowerLeft = new Vector2d(0, 0);
        objects = new LinkedHashMap<>();
    }
}
