package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static agh.ics.oop.MoveDirection.*;

class AnimalTest {

    @Test
    void testAll() {
        testHeading();
        testMovement();
        testBorder();
        testOptionParser();
    }

    @Test
    void testHeading() {
        var animal = new Animal();
        animal.move(RIGHT); animal.move(RIGHT);
        assertEquals(animal.toString(), "direction: South, position: (2,2)");
        animal.move(RIGHT); animal.move(RIGHT);
        assertEquals(animal.toString(), "direction: North, position: (2,2)");
    }

    @Test
    void testMovement() {
        var animal = new Animal();
        animal.move(FORWARD); animal.move(FORWARD);
        assertEquals(animal.toString(), "direction: North, position: (4,2)");
        animal.move(BACKWARD); animal.move(BACKWARD);
        assertEquals(animal.toString(), "direction: North, position: (2,2)");
        animal.move(RIGHT); animal.move(FORWARD); animal.move(FORWARD);
        assertEquals(animal.toString(), "direction: East, position: (2,4)");
    }

    @Test
    void testBorder() {
        var animal = new Animal();
        animal.move(FORWARD); animal.move(FORWARD); animal.move(FORWARD); animal.move(FORWARD);
        animal.move(FORWARD); animal.move(FORWARD); animal.move(FORWARD); animal.move(FORWARD);
        assertEquals(animal.toString(), "direction: North, position: (4,2)");
        animal.move(BACKWARD); animal.move(BACKWARD); animal.move(BACKWARD); animal.move(BACKWARD);
        animal.move(BACKWARD); animal.move(BACKWARD); animal.move(BACKWARD); animal.move(BACKWARD);
        assertEquals(animal.toString(), "direction: North, position: (0,2)");
    }

    @Test
    void testOptionParser() {
        var parsed = new OptionsParser().parse(new String[]{"f", "b", "r", "l"}).toArray();
        var original = new MoveDirection[] {FORWARD, BACKWARD, RIGHT, LEFT};
        for (int i = 0; i < original.length; i++) assertEquals(parsed[i], original[i]);
    }
}
