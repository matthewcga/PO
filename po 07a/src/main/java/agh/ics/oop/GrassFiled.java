package agh.ics.oop;

import static java.lang.Math.sqrt;

import java.util.HashMap;
import java.util.Random;

public class GrassFiled extends AbstractWorldMap {
    private final int grassRange;
    private final Random rand;

    public GrassFiled(int quantity) {
        grassRange = (int)sqrt(quantity * 10);
        lowerLeft = new Vector2d(0, 0);
        objects = new HashMap<>();
        rand = new Random();

        for (int i = 0; i < quantity; i++) {
            while (true) {
                var newPosition = new Vector2d(rand.nextInt(grassRange), rand.nextInt(grassRange));
                if (!isOccupied(newPosition)) {
                    objects.put(newPosition, new Grass(newPosition));
                    this.mapBounder.place(newPosition);
                    break;
                }
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.follows(lowerLeft)) && (!isOccupied(position) || objectAt(position).getClass() == Grass.class);
    }
}
