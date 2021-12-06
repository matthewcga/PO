package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassFiledTest {

    @Test
    void canMoveTo() {
        IWorldMap map  = new GrassFiled(10);
        map.place(new Animal(map, new Vector2d(0, 0)));
        assertTrue(map.canMoveTo(new Vector2d(100, 100)));
        assertTrue(map.canMoveTo(new Vector2d(5, 5)));
    }

    @Test
    void place() {
        IWorldMap map  = new GrassFiled(10);
        map.place(new Animal(map, new Vector2d(0, 0)));
        assertTrue(map.place(new Animal(map, new Vector2d(1, 1))));
        assertTrue(map.place(new Animal(map, new Vector2d(100, 100))));
        assertThrows(IllegalArgumentException.class, () -> { map.place(new Animal(map, new Vector2d(0, 0))); });
    }

    @Test
    void isOccupied() {
        IWorldMap map  = new GrassFiled(10);
        map.place(new Animal(map, new Vector2d(0, 0)));
        assertTrue(map.isOccupied(new Vector2d(0, 0)));
        assertFalse(map.isOccupied(new Vector2d(1, 1)));
    }

    @Test
    void objectAt() {
        IWorldMap map  = new GrassFiled(10);
        var animal = new Animal(map, new Vector2d(0, 0));
        map.place(animal);
        assertEquals(map.objectAt(new Vector2d(0, 0)), animal);
        assertEquals(map.objectAt(new Vector2d(1, 1)), null);
    }
}