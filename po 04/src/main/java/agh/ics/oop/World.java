package agh.ics.oop;

import static java.lang.System.out;

public class World {
    public static void main(String[] args){
        var animal = new Animal();
        out.println(animal);

        //var actions = new OptionsParser().parse(new String[]{"f", "r", "f", "r", "f", "r"});
        var actions = new OptionsParser().parse(args);

        for (MoveDirection action : actions) {
            animal.move(action);
            out.println(animal);
        }
    }
}
