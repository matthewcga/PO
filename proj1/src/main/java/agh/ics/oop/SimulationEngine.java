package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimulationEngine implements IEngine, Runnable{
    private GrassFiled map;
    private Set<Vector2d> positions = new HashSet<>();
    private IPositionChangeObserver observer;
    private List<Animal> List = new ArrayList();
    //private int counter = 0;

    public SimulationEngine(GrassFiled gf, Vector2d[] pos, int initialEnergy, IPositionChangeObserver obs) {
        map = gf; observer = obs;
        for (Vector2d position : pos) {
            this.positions.add(position);
            this.map.place(new Animal(this.map, position, initialEnergy));
        }
    }

    @Override
    public void run() {
        List<Animal> animals; Vector2d newPosition;

        for (Vector2d position : positions) {
            positions.remove(position);
            animals = map.animalsAt(position);
            for (Animal animal : animals) {
                animal.move();
                newPosition = animal.getPosition();
                positions.add(newPosition);
                if (observer != null) observer.positionChanged(position, newPosition);
                try { Thread.sleep(300); } catch (Exception ex) { }
            }
        }
    }

    public void setDirections(String s) { }
}
