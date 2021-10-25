package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void testAll() {
        testToString();
        testPrecedes();
        testFollows();
        testUpperRight();
        testLowerLeft();
        testAdd();
        testSubtract();
        testEquals();
        testOpposite();
    }

    @Test
    void testToString() { assertEquals("(0,0)", new Vector2d(0, 0).toString()); }

    @Test
    void testPrecedes() {
        assertTrue(new Vector2d(0, 0).precedes(new Vector2d(1, 1)));
        assertFalse(new Vector2d(0, 0).precedes(new Vector2d(-1, -1)));
    }

    @Test
    void testFollows() {
        assertTrue(new Vector2d(0, 0).follows(new Vector2d(-1, -1)));
        assertFalse(new Vector2d(0, 0).follows(new Vector2d(1, 1)));
    }

    @Test
    void testUpperRight() { assertTrue(new Vector2d(1, 1).equals(new Vector2d(0, 1).upperRight(new Vector2d (1, 0)))); }

    @Test
    void testLowerLeft() { assertTrue(new Vector2d(0, 0).equals(new Vector2d(0, 1).lowerLeft(new Vector2d (1, 0)))); }

    @Test
    void testAdd() { assertTrue(new Vector2d(2, 2).equals(new Vector2d(1, 1).add(new Vector2d (1, 1)))); }

    @Test
    void testSubtract() { assertTrue(new Vector2d(0, 0).equals(new Vector2d(1, 1).subtract(new Vector2d (1, 1)))); }

    @Test
    void testEquals() {
        assertTrue(new Vector2d(0, 0).equals(new Vector2d(0, 0)));
        assertFalse(new Vector2d(0, 0).equals((new Vector2d(1, 1))));
    }

    @Test
    void testOpposite() { assertTrue(new Vector2d(-1, -1).equals(new Vector2d(1, 1).opposite())); }
}