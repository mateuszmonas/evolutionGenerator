import elements.Animal;
import map.Vector2d;
import map.WorldMap;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class World {
    public static void main(String[] args) throws InterruptedException {
        WorldMap map = new WorldMap(10, 10);
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 2)).build();
        Animal animal2 = Animal.newAnimalBuilder().atPosition(new Vector2d(4, 2)).build();
        Animal animal3 = Animal.newAnimalBuilder().atPosition(new Vector2d(6, 4)).build();
        Animal animal4 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 6)).build();
        Animal animal5 = Animal.newAnimalBuilder().atPosition(new Vector2d(6, 6)).build();
        Animal animal6 = Animal.newAnimalBuilder().atPosition(new Vector2d(9, 9)).build();
        map.placeAnimal(animal1);
        map.placeAnimal(animal2);
        map.placeAnimal(animal3);
        map.placeAnimal(animal4);
        map.placeAnimal(animal5);
        map.placeAnimal(animal6);
        for (int i = 0; i < 100; i++) {
            map.simulate(true);
        }
    }
}
