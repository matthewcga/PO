package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("Start");
        for (String arg : args) System.out.print(Run(DirectionParse(arg)));
        System.out.println("Stop");
    }

    public static String Run(Direction val) {
        return switch (val) {
            case f -> "Zwierzak idzie do przodu\n";
            case b -> "Zwierzak idzie do tylu\n";
            case l -> "Zwierzak idzie w lewo\n";
            case r -> "Zwierzak idzie w prawo\n";
            default -> "";
        };
    }

    public static Direction DirectionParse(String str) {
        return switch (str) {
            case "f" -> Direction.f;
            case "b" -> Direction.b;
            case "l" -> Direction.l;
            case "r" -> Direction.r;
            default -> Direction.err;
        };
    }
}
