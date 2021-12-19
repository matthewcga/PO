package agh.ics.oop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Animal{
    private final Random rand = new Random();
    private int direction;
    private Vector2d position;
    private int[] genes;
    private GrassFiled map;
    private int energy;
    private int age = 0;
    private int kids = 0;
    private boolean isAlive = true;
    private List<IPositionChangeObserver> observers;

    public Animal(GrassFiled wm, Vector2d initialPosition, int initialEnergy) {
        direction = rand.nextInt(8);
        position = initialPosition;
        map = wm;
        observers = new ArrayList<>();
        energy = initialEnergy;

        genes = new int[32];
        for (int i = 0; i < 32; i++) genes[i] = rand.nextInt(8);
        Arrays.sort(genes);
    }

    public Animal(GrassFiled wm, Animal ma, Animal pa, int initialEnergy) {
        direction = rand.nextInt(8);
        position = ma.getPosition();
        map = wm;
        observers = new ArrayList<>();
        energy = initialEnergy;

        genes = new int[32];
        if (rand.nextBoolean()) {
            int index = (int) ((ma.energy / (ma.energy + pa.energy)) * 32);
            for (int i = 0; i < index; i++) genes[i] = ma.genes[i];
            for (int i = index; i < 32; i++) genes[i] = pa.genes[i];
        }
        else {
            int index = (int) ((pa.energy / (ma.energy + pa.energy)) * 32);
            for (int i = 0; i < index; i++) genes[i] = pa.genes[i];
            for (int i = index; i < 32; i++) genes[i] = ma.genes[i];
        }
        Arrays.sort(genes);
    }

    public void move() {
        var newDirection = this.genes[rand.nextInt(32)];
        if (newDirection == 0 || newDirection == 4) updatePosition(map.fixVector(position.pushVector(direction)));
        direction = (direction + newDirection) % 8; energy--; age++;
    }

    public void eat(int plant) { energy += plant; }
    public void makeChild() { energy = (int)(energy * 0.75); kids++; }
    public int getDirection() { return direction; }
    public Vector2d getPosition() { return position; }
    public int getEnergy() { return energy; }
    public int[] getGenes() {return genes; }
    public void kys() { isAlive = false;}
    public int getAge() {
        return age;
    }
    public int getKids() { return kids; }
    public void addObserver(IPositionChangeObserver observer) { observers.add(observer); }
    public void rmObserver(IPositionChangeObserver observer) { observers.remove(observer); }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition)
    { for (var observer : observers) observer.positionChanged(oldPosition, newPosition); }

    public void updatePosition(Vector2d newPosition) {
        var oldPosition = position;
        position = newPosition;
        positionChanged(oldPosition, newPosition);
    }

    @Override
    public String toString() { return "O"; }
}
