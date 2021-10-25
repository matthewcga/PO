package agh.ics.oop;

import static java.lang.System.out;

public class World {
    public static void main(String[] args){
        Vector2d position1 = new Vector2d(1,2);
        out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        out.println(position2);
        out.println(position1.add(position2));
        /////////////////////////////////////////////////
        out.println(MapDirection.NORTH.toString());
        out.println(MapDirection.NORTH.next().toString());
        out.println(MapDirection.NORTH.previous().toString());
        out.println(MapDirection.NORTH.toUnitVector().toString());
    }
}
