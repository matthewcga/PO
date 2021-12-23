package agh.ics.oop.gui;

import agh.ics.oop.*;
import agh.ics.oop.gui.App;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

import java.util.*;

public class SimulationEngine implements IEngine, Runnable{
    private GrassFiled map;
    private IPositionChangeObserver observer;
    private int delay;
    private Random rand = new Random();
    public ToggleButton button;

    public SimulationEngine(GrassFiled gf, int animalCount, int initialEnergy, IPositionChangeObserver obs, int del, ToggleButton butt) {
        map = gf; observer = obs; delay = del; button = butt;
        for (int i = 0; i < animalCount; i++)
            this.map.place(new Animal(this.map, new Vector2d(rand.nextInt(this.map.upperRight.x), rand.nextInt(this.map.upperRight.y)), initialEnergy));
    }

    @Override
    public void run() {
        while (button.isSelected()) {
            Set<Vector2d> positions = new HashSet<>();
            map.growGrass();
            map.eatGrassMakeKids();
            map.killDead();

            Queue<Animal> animals = new LinkedList(map.getAnimals());
            while (!animals.isEmpty()) {
                Animal animal = animals.poll();
                animal.move();

            }

            observer.positionChanged(null, null);
            if (delay > 0) try { Thread.sleep(delay); } catch (Exception ex) { }
        }
    }
}
