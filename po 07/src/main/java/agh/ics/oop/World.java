package agh.ics.oop;

public class World {
    public static void main(String[] args){
        try {
            args = new String[] {"f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f"};
            MoveDirection[] directions = new OptionsParser().parse(args);
            IWorldMap map = new GrassFiled(3); //new RectangularMap(20, 20);
            Vector2d[] positions = { new Vector2d(2,2), new Vector2d(2,2) }; //,new Vector2d(3,4) };
            IEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
        }
        catch (Exception ex) {System.out.println("symulacja nie powiodłą się!\n" + ex.getMessage());}
    }
}
