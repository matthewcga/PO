package agh.ics.oop;

public class AppSettings {

    public static class Settings {
        public static int windowWidth = 1600;
        public static int windowHeight = 1000;
        public static int mapSize = 20;
        public static int jungleSize = 1;
        public static int initAnimalCount = 20;
        public static int initAnimalEnergy = 200;
        public static int grassEnergy = 30;
        public static int delay = 30;
        public static int energyLoss = 5;
        public static boolean isMagicOn = true;
        public static int refreshRate = 10;
        public static int initPlantCount = 3;

        public static String validate() {
            StringBuilder sb = new StringBuilder();
            if (mapSize < 1 || mapSize > 100) sb.append("bledny rozmiar mapy (1, 1000)\n");
            if (jungleSize < 1 || jungleSize >= mapSize) sb.append("bledny rozmiar dzungli (1, rozmiar mapy)\n");
            if (initAnimalCount < 1 || initAnimalCount > mapSize * mapSize) sb.append("bledna liczba zwierzat (1, rozmiar mapy ^ 2)\n");
            if (initAnimalEnergy < 1) sb.append("bledna ilosc energi poczatkowej zwierzat\n");
            if (grassEnergy < 1) sb.append("bledna ilosc energi trawy\n");
            if (energyLoss < 1) sb.append("bledny koszt ruchu zwierzat\n");
            if (refreshRate < 1) sb.append("bledny okres odswiezania (> 1)\n");
            return sb.toString();
        }

        public static void UpdateSettings(int size, int sizeJ, int animals, int energy, int energyG, int movementCost, boolean magic, int refresh, int plants) {
            mapSize = size; jungleSize = sizeJ; initAnimalCount = animals; initAnimalEnergy = energy; grassEnergy = energyG;
            energyLoss = movementCost; isMagicOn = magic; refreshRate = refresh; initPlantCount = plants;
        }
    }
}
