package map;


import data.MapDirection;
import data.Rectangle;
import data.Vector2d;
import elements.animal.Animal;
import elements.grass.Grass;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JungleWorldMapTest {

    JungleWorldMap map = new JungleWorldMap(new Rectangle(new Vector2d(0, 0), new Vector2d(99, 29)));

    @Test
    void testAddAndRemoveElement() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(0, 0)).build();
        Animal animal2 = Animal.newAnimalBuilder().atPosition(new Vector2d(99, 29)).withEnergy(10).build();
        Animal animal3 = Animal.newAnimalBuilder().atPosition(new Vector2d(99, 29)).withEnergy(12).build();
        map.addElement(animal1);
        map.addElement(animal2);
        assertTrue(map.objectsAt(new Vector2d(0, 0)).contains(animal1));
        assertTrue(map.objectsAt(new Vector2d(99, 29)).contains(animal2));
        map.addElement(animal3);
        assertTrue(map.objectsAt(new Vector2d(99, 29)).contains(animal3));

        map.removeElement(animal1);
        assertNull(map.objectsAt(new Vector2d(0, 0)));
        map.removeElement(animal3);
        assertTrue(map.objectsAt(new Vector2d(99, 29)).contains(animal2));

        assertThrows(IllegalArgumentException.class, () -> map.removeElement(animal3));
    }

    @Test
    void testOnPositionChange() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(99, 29)).facingDirection(MapDirection.NORTH).build();
        Animal animal2 = Animal.newAnimalBuilder().atPosition(new Vector2d(50, 29)).facingDirection(MapDirection.NORTHEAST).build();
        Animal animal3 = Animal.newAnimalBuilder().atPosition(new Vector2d(0, 3)).facingDirection(MapDirection.SOUTH).build();
        Animal animal4 = Animal.newAnimalBuilder().atPosition(new Vector2d(0, 1)).facingDirection(MapDirection.NORTH).build();
        map.addElement(animal1);
        animal1.move();
        assertNull(map.objectsAt(new Vector2d(99, 29)));
        assertTrue(map.objectsAt(animal1.getPosition()).contains(animal1));
        map.removeElement(animal1);
        map.addElement(animal2);
        animal2.move();
        assertNull(map.objectsAt(new Vector2d(50, 29)));
        assertTrue(map.objectsAt(animal2.getPosition()).contains(animal2));
        map.removeElement(animal2);
        map.addElement(animal3);
        map.addElement(animal4);
        animal3.move();
        animal4.move();
        assertTrue(map.objectsAt(animal3.getPosition()).contains(animal3));
        assertTrue(map.objectsAt(animal4.getPosition()).contains(animal4));
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

    @Test
    void testGetUnoccupiedPosition() {
        JungleWorldMap map = new JungleWorldMap(new Rectangle(new Vector2d(0, 0), new Vector2d(10, 10)));
        assertFalse(map.getUnoccupiedPositionNotInArea(new Rectangle(new Vector2d(0, 0), new Vector2d(10, 10))).isPresent());
        for (int i = 0; i < 10 * 10; i++) {
            Optional<Vector2d> position = map.getUnoccupiedPosition();
            assertTrue(position.isPresent());
            assertFalse(map.elements.containsKey(position.get()));
            map.addElement(Animal.newAnimalBuilder().atPosition(position.get()).build());
        }
        assertFalse(map.getUnoccupiedPosition().isPresent());
    }
}