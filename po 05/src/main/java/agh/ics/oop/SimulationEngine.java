package agh.ics.oop;

public class SimulationEngine implements IEngine{

    private MoveDirection[] moves;
    private IWorldMap map;
    private Vector2d[] positions;

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] positions) {
        this.moves = moves; this.map = map; this.positions = positions;
        for (Vector2d position : this.positions) this.map.place(new Animal(this.map, position));
    }

    @Override
    public void run() {
        var counter = 0;
        for (MoveDirection move : moves) {
            Animal animal = (Animal) this.map.objectAt(positions[counter]);
            animal.move(move);
            positions[counter] = animal.getPosition();
            counter = (counter + 1) % positions.length;
            System.out.print(map);
        }
    }
    
    public String toString() { return map.toString();}
}
