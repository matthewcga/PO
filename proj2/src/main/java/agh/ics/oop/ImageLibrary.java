package agh.ics.oop;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ImageLibrary {
    public static ImageView armyIcon;
    public static ImageView goldIcon;
    public static ImageView foodIcon;
    public static ImageView techIcon;
    public static ImageView gameLogo;
    private static final HashMap<Integer, String> cardsImagesMap = new HashMap<>();

    public static void LoadImages() throws IOException {
        gameLogo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        armyIcon = new ImageView(new Image(new FileInputStream("src/main/resources/icons/army.png")));
        goldIcon = new ImageView(new Image(new FileInputStream("src/main/resources/icons/gold.png")));
        foodIcon = new ImageView(new Image(new FileInputStream("src/main/resources/icons/food.png")));
        techIcon = new ImageView(new Image(new FileInputStream("src/main/resources/icons/tech.png")));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/resources/CardsImagesLibrary.csv"), StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null; ) {
                String[] lineParts = line.split(";");
                cardsImagesMap.put(Integer.parseInt(lineParts[0]), lineParts[1]);
            }
        }
    }

    public static String GetCardImagePath(int id) { return cardsImagesMap.get(id); }
}
