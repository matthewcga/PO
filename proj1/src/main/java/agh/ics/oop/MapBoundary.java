package agh.ics.oop;

import java.util.*;

public class MapBoundary implements IPositionChangeObserver {
    SortedSet<Vector2d> sortedX = new TreeSet<>(new ComparatorX());
    SortedSet<Vector2d> sortedY = new TreeSet<>(new ComparatorY());

    public void place(Vector2d position) { this.sortedX.add(position); this.sortedY.add(position); }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        sortedX.remove(oldPosition);
        sortedY.remove(oldPosition);
        sortedX.add(newPosition);
        sortedY.add(newPosition);
    }

    public Vector2d getUpperRight() { return new Vector2d(sortedX.last().x, sortedY.last().y); }

    public Vector2d getLowerLeft() { return new Vector2d(sortedX.first().x, sortedY.first().y); }
}

class ComparatorX implements Comparator<Vector2d> {

    @Override
    public int compare(Vector2d o1, Vector2d o2) {
        var result = (int) Math.signum(o1.x - o2.x);
        return (result != 0)? result : (int) Math.signum(o1.y - o2.y);
    }
}

class ComparatorY implements Comparator<Vector2d>{

    @Override
    public int compare(Vector2d o1, Vector2d o2) {
        var result = (int) Math.signum(o1.y - o2.y);
        return (result != 0)? result : (int) Math.signum(o1.x - o2.x);
    }
}