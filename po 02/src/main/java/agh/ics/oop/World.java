package agh.ics.oop;

import static java.lang.System.out;
import static agh.ics.oop.MoveDirection.*;

public class World {
    public static void main(String[] args){
        var animal1 = new Animal();
        //out.println(animal1.toString());

        var actions1 = new MoveDirection[]{RIGHT, FORWARD, FORWARD, FORWARD};
        for (MoveDirection action : actions1) {
            animal1.move(action);
            //out.println(animal1.toString());
        }

        var animal2 = new Animal();
        out.println(animal2.toString());
        var actions2 = new OptionsParser().parse(new String[]{"f", "f", "f", "b", "c", "f", "f", "l", "l"});
        for (MoveDirection action : actions2) {
            animal1.move(action);
            out.println(animal1.toString());
        }
    }
}
