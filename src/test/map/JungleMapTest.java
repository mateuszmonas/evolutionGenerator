package map;


import data.Rectangle;
import data.Vector2d;
import elements.animal.Animal;
import elements.grass.Grass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JungleMapTest {

    JungleMap map = new JungleMap(new Rectangle(new Vector2d(0, 0), new Vector2d(99, 29)));

    @Test
    void testAddAndRemoveElement() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(100, 30)).build();
        Animal animal2 = Animal.newAnimalBuilder().atPosition(new Vector2d(-2, 0)).build();
        Animal animal3 = Animal.newAnimalBuilder().atPosition(new Vector2d(0, 0)).build();
        Animal animal4 = Animal.newAnimalBuilder().atPosition(new Vector2d(99, 29)).withEnergy(10).build();
        Animal animal5 = Animal.newAnimalBuilder().atPosition(new Vector2d(99, 29)).withEnergy(12).build();
        assertThrows(IllegalArgumentException.class, () -> map.addElement(animal1));
        assertThrows(IllegalArgumentException.class, () -> map.addElement(animal2));
        map.addElement(animal3);
        map.addElement(animal4);
        assertTrue(map.objectsAt(new Vector2d(0, 0)).contains(animal3));
        assertTrue(map.objectsAt(new Vector2d(99, 29)).contains(animal4));
        map.addElement(animal5);
        assertTrue(map.objectsAt(new Vector2d(99, 29)).contains(animal5));

        map.removeElement(animal3);
        assertNull(map.objectsAt(new Vector2d(0, 0)));
        map.removeElement(animal5);
        assertTrue(map.objectsAt(new Vector2d(99, 29)).contains(animal4));
    }

    @Test
    void testObjectsAt() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(0, 0)).build();
        Grass grass = new Grass(new Vector2d(0, 0));
        assertNull(map.objectsAt(new Vector2d(0, 0)));
        map.addElement(grass);
        assertTrue(map.objectsAt(new Vector2d(0, 0)).contains(grass));
        map.addElement(animal1);
        assertTrue(map.objectsAt(new Vector2d(0, 0)).contains(animal1));
    }
}