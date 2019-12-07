package map;


import elements.Animal;
import elements.Grass;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class WorldMapTest {

    WorldMap map = new WorldMap(100, 30);

    @Test
    void testRemoveDeadAnimals() {
        Animal animal1 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(10, 3)).withEnergy(-1).build();
        Animal animal2 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(2, 0)).withEnergy(-1).build();
        Animal animal3 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(0, 0)).withEnergy(-1).build();
        map.placeAnimal(animal1);
        map.placeAnimal(animal2);
        map.placeAnimal(animal3);
        assertEquals(3, map.animals.size());
        map.removeDeadAnimals();
        assertEquals(0, map.animals.size());
    }

    @Test
    void testPlaceAndRemoveAnimal() {
        Animal animal1 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(100, 30)).build();
        Animal animal2 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(-2, 0)).build();
        Animal animal3 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(0, 0)).build();
        Animal animal4 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(99, 29)).withEnergy(10).build();
        Animal animal5 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(99, 29)).withEnergy(12).build();
        Animal animal6 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(99, 29)).withEnergy(10).build();
        assertThrows(IllegalArgumentException.class, () -> map.placeAnimal(animal1));
        assertThrows(IllegalArgumentException.class, () -> map.placeAnimal(animal2));
        map.placeAnimal(animal3);
        map.placeAnimal(animal4);
        assertTrue(((TreeSet<Animal>) map.objectAt(new Vector2d(0, 0))).contains(animal3));
        assertTrue(map.animals.contains(animal3));
        assertTrue(((TreeSet<Animal>) map.objectAt(new Vector2d(99, 29))).contains(animal4));
        assertTrue(map.animals.contains(animal4));
        map.placeAnimal(animal5);
        assertTrue(((TreeSet<Animal>) map.objectAt(new Vector2d(99, 29))).contains(animal5));
        assertEquals(((TreeSet<Animal>) map.objectAt(new Vector2d(99, 29))).first(), animal5);
        assertTrue(map.animals.contains(animal5));
        map.placeAnimal(animal6);
        assertEquals(3, ((TreeSet<Animal>) map.objectAt(new Vector2d(99, 29))).size());

        map.removeAnimal(animal3);
        assertNull(map.objectAt(new Vector2d(0, 0)));
        assertFalse(map.animals.contains(animal3));
        map.removeAnimal(animal4);
        assertFalse(((TreeSet<Animal>) map.objectAt(new Vector2d(99, 29))).contains(animal4));
        assertFalse(map.animals.contains(animal4));
        assertTrue(((TreeSet<Animal>) map.objectAt(new Vector2d(99, 29))).contains(animal5));
        assertTrue(map.animals.contains(animal5));

    }

    @Test
    void testIsOccupied() {
        Animal animal1 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(0, 0)).build();
        map.placeAnimal(animal1);
        assertTrue(map.isOccupied(new Vector2d(0, 0)));
        assertFalse(map.isOccupied(new Vector2d(2, 0)));
    }

    @Test
    void testRun() {
        Animal animal1 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(99, 29)).facingDirection(MapDirection.NORTH).build();
        Animal animal2 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(50, 29)).facingDirection(MapDirection.NORTHEAST).build();
        Animal animal3 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(0, 3)).facingDirection(MapDirection.SOUTH).build();
        Animal animal4 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(0, 1)).facingDirection(MapDirection.NORTH).build();
        map.placeAnimal(animal1);
        map.run(new MoveDirection[]{MoveDirection.MOVE});
        assertNull(map.objectAt(new Vector2d(99, 29)));
        assertTrue(((TreeSet<Animal>) map.objectAt(new Vector2d(99, 0))).contains(animal1));
        map.removeAnimal(animal1);
        map.placeAnimal(animal2);
        map.run(new MoveDirection[]{MoveDirection.MOVE});
        assertNull(map.objectAt(new Vector2d(50, 29)));
        assertTrue(((TreeSet<Animal>) map.objectAt(new Vector2d(51, 0))).contains(animal2));
        map.removeAnimal(animal2);
        map.placeAnimal(animal3);
        map.placeAnimal(animal4);
        map.run(new MoveDirection[]{MoveDirection.MOVE, MoveDirection.MOVE});
        assertTrue(((TreeSet<Animal>) map.objectAt(new Vector2d(0, 2))).contains(animal3));
        assertTrue(((TreeSet<Animal>) map.objectAt(new Vector2d(0, 2))).contains(animal4));

    }

    @Test
    void testObjectAt() {
        assertNull(map.objectAt(new Vector2d(0, 0)));
        Animal animal1 = Animal.AnimalBuilder.newAnimalBuilder().onMap(map).atPosition(new Vector2d(0, 0)).build();
        map.placeGrass(new Grass(new Vector2d(0, 0)));
        map.placeAnimal(animal1);
        assertTrue(map.objectAt(new Vector2d(0, 0)) instanceof ArrayList);
        }

    @Test
    void testPlaceAndRemoveGrass() {
        Grass grass1 = new Grass(new Vector2d(2, 9));
        Grass grass2 = new Grass(new Vector2d(2, 9));
        map.placeGrass(grass1);
        assertEquals(grass1, map.objectAt(new Vector2d(2, 9)));
        assertThrows(IllegalArgumentException.class, () -> map.placeGrass(grass2));
        map.removeGrass(grass1);
        assertNull(map.objectAt(new Vector2d(2, 9)));
    }

    @Test
    void testNormalisePosition() {
        WorldMap map = new WorldMap(10, 10);
        Vector2d position = new Vector2d(-1, 8);
        assertEquals(new Vector2d(9, 8), map.normalisePosition(position));
        position = new Vector2d(-11, 8);
        assertEquals(new Vector2d(9, 8), map.normalisePosition(position));
        position = new Vector2d(-10, 8);
        assertEquals(new Vector2d(0, 8), map.normalisePosition(position));

    }
}