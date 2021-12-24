package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

import java.io.IOException;

public class World {
    public static void main(String[] args) throws IOException {


        try { Application.launch(App.class); }
        catch (Exception ex) {System.out.println("symulacja nie powiodla sie!\n" + ex.getMessage() ); ex.printStackTrace(); System.in.read(); System.exit(1);}
        System.exit(0);
    }
}
