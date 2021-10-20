package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void testAll()
    {
        testToString();
        testNext();
        testPrevious();
        testToUnitVector();
    }

    @Test
    void testToString() {
        assertEquals("Północ", MapDirection.NORTH.toString());
        assertEquals("Wschód", MapDirection.EAST.toString());
        assertEquals("Południe", MapDirection.SOUTH.toString());
        assertEquals("Zachód", MapDirection.WEST.toString());
    }

    @Test
    void testNext() {
        assertEquals(MapDirection.NORTH, MapDirection.WEST.next());
        assertEquals(MapDirection.EAST, MapDirection.NORTH.next());
        assertEquals(MapDirection.SOUTH, MapDirection.EAST.next());
        assertEquals(MapDirection.WEST, MapDirection.SOUTH.next());
    }

    @Test
    void testPrevious() {
        assertEquals(MapDirection.NORTH, MapDirection.EAST.previous());
        assertEquals(MapDirection.EAST, MapDirection.SOUTH.previous());
        assertEquals(MapDirection.SOUTH, MapDirection.WEST.previous());
        assertEquals(MapDirection.WEST, MapDirection.NORTH.previous());
    }

    @Test
    void testToUnitVector() {
        assertTrue(new Vector2d(1, 0).equals(MapDirection.NORTH.toUnitVector()));
        assertTrue(new Vector2d(0, 1).equals(MapDirection.EAST.toUnitVector()));
        assertTrue(new Vector2d(-1, 0).equals(MapDirection.SOUTH.toUnitVector()));
        assertTrue(new Vector2d(0, -1).equals(MapDirection.WEST.toUnitVector()));
    }
}