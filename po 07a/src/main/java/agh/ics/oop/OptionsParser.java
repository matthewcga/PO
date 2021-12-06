package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OptionsParser {
    private HashMap<String, MoveDirection> dict = new HashMap<>() {{
        put("f", MoveDirection.FORWARD);
        put("forward", MoveDirection.FORWARD);
        put("b", MoveDirection.BACKWARD);
        put("backward", MoveDirection.BACKWARD);
        put("r", MoveDirection.RIGHT);
        put("right", MoveDirection.RIGHT);
        put("l", MoveDirection.LEFT);
        put("left", MoveDirection.LEFT);
    }};

    public MoveDirection[] parse(String[] input) throws IllegalArgumentException {
        List<MoveDirection> output = new ArrayList<MoveDirection>();
        for (String i : input)
            if (dict.containsKey(i)) output.add(dict.get(i));
            else throw new IllegalArgumentException("'" + i + "' nie jest poprawnym argumentem!");

        return output.toArray(new MoveDirection[output.size()]);
    }
}
