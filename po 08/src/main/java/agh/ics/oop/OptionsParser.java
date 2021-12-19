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
        List<MoveDirection> output = new ArrayList<MoveDirection>(); String j;
        for (String i : input) {
            j = i.trim();
            if (dict.containsKey(j)) output.add(dict.get(j));
            else throw new IllegalArgumentException("'" + j + "' nie jest poprawnym argumentem!");
        }
        return output.toArray(new MoveDirection[output.size()]);
    }
}
