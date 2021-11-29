package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void parse() {
        assertTrue(MoveDirection.FORWARD == new OptionsParser().parse(new String[] {"f"})[0]);
        assertTrue(MoveDirection.FORWARD == new OptionsParser().parse(new String[] {"forward"})[0]);
        assertFalse(MoveDirection.FORWARD == new OptionsParser().parse(new String[] {"b"})[0]);
        assertThrows(IllegalArgumentException.class, () -> {new OptionsParser().parse(new String[] {"abcd"});});
    }
}