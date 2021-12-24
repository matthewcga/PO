package agh.ics.oop;

public class AppSettings {

    public static class Settings {
        public static int windowWidth = 1600;
        public static int windowHeight = 1000;
        public static int mapSize = 40;
        public static int jungleSize = 1;
        public static int initAnimalCount = 20;
        public static int initAnimalEnergy = 200;
        public static int grassEnergy = 30;
        public static int delay = 20;
        public static int energyLoss = 5;
        public static boolean isMagicOn = true;
        public static int refreshRate = 10;

        public String validate() {
            windowWidth = Math.max(windowHeight, 600);
            windowHeight = Math.max(windowHeight, 600);
            delay = Math.min(15, delay);
            refreshRate = Math.min(1, refreshRate);

            if (mapSize < 3 || mapSize > 100) return "bledny rozmiar mapy (3 <=> 1000)";
            if (jungleSize < 1 || jungleSize >= mapSize) return "bledny rozmiar dzungli (1 <=> rozmiar mapy)";
            if (initAnimalCount < 1 || initAnimalCount > mapSize * mapSize)
                return "bledna liczba zwierzat (1 <=> rozmiar mapy ^ 2)";
            if (initAnimalEnergy < 1) return "bledna ilosc energi poczatkowej zwierzat";
            if (grassEnergy < 1) return "bledna ilosc energi trawy";
            if (energyLoss < 1) return "bledny koszt ruchu zwierzat";

            return null;
        }
    }
}
