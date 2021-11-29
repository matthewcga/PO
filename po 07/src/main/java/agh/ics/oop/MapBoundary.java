package agh.ics.oop;

import java.util.Set;

public class MapBoundary implements IPositionChangeObserver {


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }


}

class AnimalX implements Comparable<Animal>{
    public Animal animal;

    @Override
    public int compareTo(Animal o) {
        return (int) Math.signum(this.animal.getPosition().x - o.getPosition().x);
    }
}

class AnimalY implements Comparable<Animal>{
    public Animal animal;

    @Override
    public int compareTo(Animal o) {
        return (int) Math.signum(this.animal.getPosition().y - o.getPosition().y);
    }
}
