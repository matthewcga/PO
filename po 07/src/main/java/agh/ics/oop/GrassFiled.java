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
                    break;
                }
            }
        }
    }

    //public void updateUpperRight(Vector2d position) { upperRight = upperRight.upperRight(position); }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position.follows(lowerLeft)) && (!isOccupied(position) || objectAt(position).getClass() == Grass.class);
        //if (position.follows(lowerLeft)) {
            // if (!isOccupied(position)) { updateUpperRight(position); return true; }
            // else if (objectAt(position).getClass() == Grass.class) { eat(position); return true; }
            // else return false;
        //else return false;
    }

//    public void eat(Vector2d position) {
//        while (true) {
//            var newPosition = new Vector2d(rand.nextInt(grassRange), rand.nextInt(grassRange));
//            if (!isOccupied(newPosition)) {
//                objects.put(newPosition, new Grass(newPosition));
//                updateUpperRight(newPosition);
//                break;
//            }
//        }
//        objects.remove(position);
//    }
}
