package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        try { Application.launch(App.class); }
        catch (Exception ex) {
            System.out.println("symulacja nie powiodla sie!\n" + ex.getMessage() );
            ex.printStackTrace(); System.exit(1);
        }
        System.exit(0);
    }
}
