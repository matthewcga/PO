package agh.ics.oop;

public class Grass{
    private Vector2d position;
    private int energy;

    public Grass(Vector2d initialPosition, int initialEnergy) { position = initialPosition; energy = initialEnergy; }
    public Vector2d getPosition() { return position; }
    public int getEnergy() { return energy; }

    @Override
    public String toString() { return "*"; }
}
