package agh.ics.oop.gui;

import agh.ics.oop.*;
import agh.ics.oop.gui.App;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import javax.swing.JFileChooser;

public class SimulationEngine implements IEngine, Runnable{
    private GrassFiled map;
    private IPositionChangeObserver observer;
    private int delay;
    private Random rand = new Random();
    private ToggleButton button;
    int day = 0;
    int animalEnergy;
    int magicEvents;
    StringBuilder data = new StringBuilder("-1; dzien, l. zwierzat, l. trawy, srednia energia, srednia wiek zgonu, srednia ilosc dzieci, na koncu wartosci usrednione;\n");

    public int animalsCount, grassCount;
    public float avgEng, avgAgeOfDeath, avgKids;

    public int animalsCountSum, grassCountSum;
    public float avgEngSum, avgAgeOfDeathSum, avgKidsSum;

    Settings setting = new Settings();

    public SimulationEngine(GrassFiled gf, IPositionChangeObserver obs, ToggleButton butt) {
        map = gf; observer = obs; delay = setting.delay; button = butt; animalEnergy = setting.initAnimalEnergy;
        for (int i = 0; i < setting.initAnimalCount; i++)
            map.place(new Animal(this.map, new Vector2d(rand.nextInt(this.map.upperRight.x), rand.nextInt(this.map.upperRight.y))));
    }

    @Override
    public void run() {
        while (button.isSelected()) {
            day++;
            //Set<Vector2d> positions = new HashSet<>();
            map.growGrass();
            map.eatGrassMakeKids();
            map.killDead(day);

            if (magicEvents < 3) {
                List<Animal> a = map.performMagic();
                if (a != null) {
                    for (Animal clone : a)
                        map.place(new Animal(this.map, new Vector2d(rand.nextInt(this.map.upperRight.x), rand.nextInt(this.map.upperRight.y)), clone));
                    magicEvents++;
                    System.out.println("mahia");
                    // inform GUI !!!!!!!!!!!!!!!!
                }
            }

            Queue<Animal> animals = new LinkedList(map.getAnimals());
            while (!animals.isEmpty()) {
                Animal animal = animals.poll();
                animal.move();
            }

            updateStats();
            makeRow();

            observer.positionChanged(null, null);
            if (delay > 0) try { Thread.sleep(delay); } catch (Exception ex) { }
        }
    }

    public String getStats() {
        return "dzien: " + day + ", l. zwierzat: " + animalsCount + ", l. trawy " + grassCount + ", srednia energia "
                + avgEng + ", sredni wiek zgonu " + avgAgeOfDeath + ", srednia ilosc dzieci " + avgKids;
    }

    public void makeRow() { data.append(day + ";" + animalsCount + ";" + grassCount  + ";" + avgEng  + ";" + avgAgeOfDeath  + ";" + avgKids + ";\n"); }

    public void saveFile() throws IOException {
        data.append("-2;" + animalsCountSum / day + ";" + grassCountSum / day + ";" + avgEngSum / day  + ";" +
                avgAgeOfDeathSum / day  + ";" + avgKidsSum / day + ";\n");

        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = j.showSaveDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) throw new IOException("problem z folderem!");

        File dir = j.getSelectedFile();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = dir.getPath() + "\\symulacja_" + currentDate + ".csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(data.toString());
        writer.close();
    }

    public void updateStats() {
        animalsCount = map.getAnimals().size(); grassCount = map.getGrass().size(); avgEng = map.getAvgEng();
        avgAgeOfDeath = map.getAvgAgeOfDeath(); avgKids = map.getAvgKids();

        animalsCountSum += animalsCount; grassCountSum += grassCount; avgEngSum += avgEng;
        avgAgeOfDeathSum += avgAgeOfDeath; avgKidsSum += avgKids;
    }
}
