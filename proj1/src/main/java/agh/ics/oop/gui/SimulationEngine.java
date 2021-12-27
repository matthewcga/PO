package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.scene.control.ToggleButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JFileChooser;

public class SimulationEngine implements Runnable{
    private final GrassFiled map;
    private final IPositionChangeObserver observer;
    private final ToggleButton button;
    private int day = 1, magicEvents = 0;
    private StringBuilder data = new StringBuilder("-1; -1 - opis, -2 - wartosci usrednione, x > 0 - wartosci z dnia x, l. zwierzat, l. trawy, srednia energia, srednia wiek zgonu, srednia ilosc dzieci;\n");
    private int animalsCount, grassCount, animalsCountSum, grassCountSum;
    private float avgEng, avgAgeOfDeath, avgKids, avgEngSum, avgAgeOfDeathSum, avgKidsSum;
    private String name;

    public SimulationEngine(String mapName, GrassFiled gf, IPositionChangeObserver obs, ToggleButton butt) {
        name = mapName; map = gf; observer = obs; button = butt;

        for (int i = 0; i < AppSettings.Settings.initAnimalCount; i++) {
            int failCount = 0;
            while (failCount < 30) {
                Vector2d newPosition = map.getRandomVector();
                if (!map.isOccupied(newPosition)) { map.place(new Animal(map, newPosition)); break; }
                failCount++;
            }
        }
    }

    @Override
    public void run() {
        while (button.isSelected()) {
            map.growGrass(); map.eatGrassMakeKids(); map.killDead(day);
            if (magicEvents < 3) magicEventAction();

            Queue<Animal> animals = new LinkedList(map.getAnimals());
            while (!animals.isEmpty()) animals.poll().move();

            updateStats(); makeRow();

            if (day % AppSettings.Settings.refreshRate == 0) {
                observer.positionChanged(null, null);
                try { Thread.sleep(AppSettings.Settings.delay); } catch (Exception ignored) { }
            }
            day++;
        }

    }

    public String getStats() {
        return "dzien: " + day + ", l. zwierzat: " + animalsCount + ", l. trawy " + grassCount + ", srednia energia "
                + avgEng + ", sredni wiek zgonu " + avgAgeOfDeath + ", srednia ilosc dzieci " + avgKids;
    }

    public void makeRow() {
        data.append(day).append(";").append(animalsCount).append(";").append(grassCount)
            .append(";").append(avgEng).append(";").append(avgAgeOfDeath).append(";").append(avgKids).append(";\n");
    }

    public void saveFile() throws IOException {
        data.append("-2;").append(animalsCountSum / day).append(";").append(grassCountSum / day)
                .append(";").append(avgEngSum / day).append(";").append(avgAgeOfDeathSum / day)
                .append(";").append(avgKidsSum / day).append(";\n");

        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = j.showSaveDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) throw new IOException("problem z folderem!");

        File dir = j.getSelectedFile();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String fileName = dir.getPath() + "\\" + name + "_symulacja_" + currentDate + ".csv";
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

    public void magicEventAction() {
        List<Animal> a = map.performMagic();
        if (a == null) return;
        for (Animal clone : a) map.place(new Animal(this.map, map.getRandomVector(), clone));
        magicEvents++;
    }

    public int getMagicEventsCount() { return magicEvents; }
}
