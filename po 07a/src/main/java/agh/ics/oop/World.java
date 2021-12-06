package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        args = new String[] {"f", "r", "f", "f", "r", "f", "f", "f", "f", "l", "r", "f", "f", "f", "f", "f", "f", "f", "f", "f"};
        try { Application.launch(App.class, args); }
        catch (Exception ex) {System.out.println("symulacja nie powiodla sie!\n" + ex.getMessage() ); ex.printStackTrace();}
    }
}
