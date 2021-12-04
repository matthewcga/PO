package agh.ics.oop;

public class World {
    public static void main(String[] args){
        try {
            args = new String[] {"f", "r", "f", "f", "r", "f", "f", "f", "f", "l", "r", "f", "f", "f", "f", "f", "f", "f", "f", "f"};
            MoveDirection[] directions = new OptionsParser().parse(args);
            IWorldMap map = new GrassFiled(10); //new RectangularMap(20, 20);
            Vector2d[] positions = { new Vector2d(0,0), new Vector2d(1,0)};
            IEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
        }
        catch (Exception ex) {System.out.println("symulacja nie powiodla sie!\n" + ex.getMessage() ); ex.printStackTrace();}
    }
}
