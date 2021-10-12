package agh.ics.oop;

import static agh.ics.oop.Direction.*;

public class World
{
    public static void main(String[] args)
    {
        System.out.println("Start");

        for (String arg : args) System.out.println(Run(DirectionParse(arg)));

        System.out.println("\nStop");
    }

    public static String Run(Direction arg)
    {
        return switch (arg) {
            case f -> "Zwierzak idzie do przodu";
            case b -> "Zwierzak idzie do tylu";
            case l -> "Zwierzak idzie w lewo";
            case r -> "Zwierzak idzie w prawo";
            default -> "";
        };
    }

    public static Direction DirectionParse(String str)
    {
        return switch (str) {
            case "f" -> f;
            case "b" -> b;
            case "l" -> l;
            case "r" -> r;
            default -> err;
        };
    }
}
