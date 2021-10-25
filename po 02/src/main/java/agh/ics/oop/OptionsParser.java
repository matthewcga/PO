package agh.ics.oop;

import java.util.List;

public class OptionsParser {

    public List<MoveDirection> parse(String[] input)
    {
        List<MoveDirection> output = null;
        for (String i : input)
            switch (i) {
                case "f": output.add(MoveDirection.FORWARD); break;
                case "b": output.add(MoveDirection.BACKWARD); break;
                case "r": output.add(MoveDirection.RIGHT); break;
                case "l": output.add(MoveDirection.LEFT); break;
            }
        return output;
    }
}
