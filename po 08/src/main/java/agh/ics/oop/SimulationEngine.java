package agh.ics.oop;

public class SimulationEngine implements IEngine, Runnable{

    private MoveDirection[] moves;
    private IWorldMap map;
    private Vector2d[] positions;
    private IPositionChangeObserver observer;
    private int counter = 0;

    public SimulationEngine(IWorldMap map, Vector2d[] positions, IPositionChangeObserver observer) {
        this.map = map; this.positions = positions; this.observer = observer;
        for (Vector2d position : this.positions) this.map.place(new Animal(this.map, position));
    }

    @Override
    public void run() {
        Animal animal; Vector2d oldPosition, newPosition;

        for (MoveDirection move : moves) {
            animal = (Animal) this.map.objectAt(positions[counter]);
            oldPosition = animal.getPosition();
            animal.move(move);
            newPosition = animal.getPosition();
            positions[counter] = newPosition;

            if (this.observer != null) this.observer.positionChanged(oldPosition, newPosition);
            counter = (counter + 1) % positions.length;

            //try { Thread.sleep(300); } catch (Exception ex) {}
        }
    }
    
    public String toString() { return map.toString(); }

    public void setDirections(MoveDirection[] moves) {
        this.moves = moves;
    }
}
