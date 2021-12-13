package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationEngineTest {

    @Test
    void run() {
        String[] args = {"l", "r", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4), new Vector2d(3,2) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        //System.out.println(map);

        assertTrue(map.isOccupied(new Vector2d(0, 2)));
        assertTrue(map.isOccupied(new Vector2d(10, 4)));
        assertTrue(map.isOccupied(new Vector2d(3, 5)));
        assertFalse(map.isOccupied(new Vector2d(0, 0)));
        assertFalse(map.isOccupied(new Vector2d(5, 5)));
        assertFalse(map.isOccupied(new Vector2d(100, 100)));
    }
}