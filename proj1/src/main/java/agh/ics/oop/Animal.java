package agh.ics.oop;

import java.util.*;

public class Animal{
    private final Random rand = new Random();
    private int direction;
    private Vector2d position;
    private int[] genes;
    private GrassFiled map;
    private int energy;
    private int age = 1;
    private List<IPositionChangeObserver> observers;

    private Boolean isTracked = false;
    private int trackedKids = 0;
    private boolean isDead = false;
    private List<Animal> kids = new ArrayList();
    private int dayOfDeath;

    Settings settings = new Settings();

    public Animal(GrassFiled wm, Vector2d initialPosition) {
        direction = rand.nextInt(8);
        position = initialPosition;
        map = wm;
        observers = new ArrayList<>();
        energy = settings.initAnimalEnergy;

        genes = new int[32];
        for (int i = 0; i < 32; i++) genes[i] = rand.nextInt(8);
        Arrays.sort(genes);
    }

    public Animal(GrassFiled wm, Animal ma, Animal pa) {
        direction = rand.nextInt(8);
        position = ma.getPosition();
        map = wm;
        observers = ma.getObservers();
        energy = ma.makeChild() + pa.makeChild();

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

    public Animal(GrassFiled wm, Vector2d initialPosition, Animal donor) {
        direction = rand.nextInt(8);
        position = initialPosition;
        map = wm;
        observers = donor.getObservers();
        energy = settings.initAnimalEnergy;
        genes = donor.getGenes();
    }

    public void move() {
        var newDirection = this.genes[rand.nextInt(32)];
        if (newDirection == 0 || newDirection == 4) {
            updatePosition(map.fixVector(position.pushVector(direction)));
            energy -= 5;
        }
        direction = (direction + newDirection) % 8; energy--; age++;
    }

    public int makeChild() {
        trackedKids++;
        int energyForKid = (int)(energy * 0.25);
        energy = (int)(energy * 0.75);
        return energyForKid;
    }

    public void linkChild(Animal child) { if (isTracked) kids.add(child); }
    public int getDirection() { return direction; }
    public Vector2d getPosition() { return position; }
    public int getEnergy() { return energy; }
    public void addEnergy(int e) { energy += e; }
    public int[] getGenes() {return genes; }
    public int getAge() { return age; }
    public int getKidsCount() { return kids.size(); }
    public int getSuccessorsCount() {
        int sum = kids.size();
        for (Animal child : kids) sum += child.getSuccessorsCount();
        return sum;
    }
    public List<IPositionChangeObserver> getObservers() { return observers; }
    public void addObserver(IPositionChangeObserver observer) { observers.add(observer); }
    public void rmObserver(IPositionChangeObserver observer) { observers.remove(observer); }
    public void die(int day) { isDead = true; dayOfDeath = day; }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition)
    { for (var observer : observers) observer.positionChanged(oldPosition, newPosition); }

    public void updatePosition(Vector2d newPosition) {
        var oldPosition = position;
        position = newPosition;
        positionChanged(oldPosition, newPosition);
    }

    @Override
    public String toString() { return "A"; }

    public void startTracking() { isTracked = true; }

    public void stopTracking() { isTracked = false; trackedKids = 0; kids = new ArrayList(); }

    public String getTrackingInfo() {
        return "zwierze (id): " + this.hashCode() +
                ", dzieci: " + getKidsCount() +
                ", potomkowie: " + getSuccessorsCount() +
                ", energia: " + energy +
                ", genom: " + Arrays.toString(genes) +
                ((isDead)? ", zmarlo w " + dayOfDeath + " dniu majac " + age + " dni": ", wiek: " + age);
    }
}
