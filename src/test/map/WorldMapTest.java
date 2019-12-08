package map;


import com.sun.source.tree.Tree;
import elements.Animal;
import elements.Grass;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.*;

class WorldMapTest {

    WorldMap map = WorldMap.newMapBuilder().withHeight(30).withWidth(100).build();

    @Test
    void testRemoveDeadAnimals() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(10, 3)).withEnergy(-1).build();
        Animal animal2 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 0)).withEnergy(-1).build();
        Animal animal3 = Animal.newAnimalBuilder().atPosition(new Vector2d(0, 0)).withEnergy(-1).build();
        map.placeAnimal(animal1);
        map.placeAnimal(animal2);
        map.placeAnimal(animal3);
        assertEquals(3, map.animals.size());
        map.removeDeadAnimals();
        assertEquals(0, map.animals.size());
    }

    @Test
    void testPlaceAndRemoveAnimal() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(100, 30)).build();
        Animal animal2 = Animal.newAnimalBuilder().atPosition(new Vector2d(-2, 0)).build();
        Animal animal3 = Animal.newAnimalBuilder().atPosition(new Vector2d(0, 0)).build();
        Animal animal4 = Animal.newAnimalBuilder().atPosition(new Vector2d(99, 29)).withEnergy(10).build();
        Animal animal5 = Animal.newAnimalBuilder().atPosition(new Vector2d(99, 29)).withEnergy(12).build();
        Animal animal6 = Animal.newAnimalBuilder().atPosition(new Vector2d(99, 29)).withEnergy(9).build();
        assertThrows(IllegalArgumentException.class, () -> map.placeAnimal(animal1));
        assertThrows(IllegalArgumentException.class, () -> map.placeAnimal(animal2));
        map.placeAnimal(animal3);
        map.placeAnimal(animal4);
        assertEquals(animal3, map.objectAt(new Vector2d(0, 0)));
        assertEquals(animal4, map.objectAt(new Vector2d(99, 29)));
        map.placeAnimal(animal5);
        assertEquals(animal5, map.objectAt(new Vector2d(99, 29)));
        map.placeAnimal(animal6);
        assertEquals(animal5, map.objectAt(new Vector2d(99, 29)));

        map.removeAnimal(animal3);
        assertNull(map.objectAt(new Vector2d(0, 0)));
        map.removeAnimal(animal5);
        assertEquals(animal4, map.objectAt(new Vector2d(99, 29)));
    }

    @Test
    void testIsOccupied() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(0, 0)).build();
        map.placeAnimal(animal1);
        assertTrue(map.isOccupied(new Vector2d(0, 0)));
        assertFalse(map.isOccupied(new Vector2d(2, 0)));
    }

    @Test
    void testRun() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(99, 29)).facingDirection(MapDirection.NORTH).build();
        Animal animal2 = Animal.newAnimalBuilder().atPosition(new Vector2d(50, 29)).facingDirection(MapDirection.NORTHEAST).build();
        Animal animal3 = Animal.newAnimalBuilder().withEnergy(1).atPosition(new Vector2d(0, 3)).facingDirection(MapDirection.SOUTH).build();
        Animal animal4 = Animal.newAnimalBuilder().withEnergy(2).atPosition(new Vector2d(0, 1)).facingDirection(MapDirection.NORTH).build();
        map.placeAnimal(animal1);
        map.run(new MoveDirection[]{MoveDirection.MOVE});
        assertNull(map.objectAt(new Vector2d(99, 29)));
        assertEquals(animal1, map.objectAt(new Vector2d(99, 0)));
        map.removeAnimal(animal1);
        map.placeAnimal(animal2);
        map.run(new MoveDirection[]{MoveDirection.MOVE});
        assertNull(map.objectAt(new Vector2d(50, 29)));
        assertEquals(animal2, map.objectAt(new Vector2d(51, 0)));
        map.removeAnimal(animal2);
        map.placeAnimal(animal3);
        map.placeAnimal(animal4);
        map.run(new MoveDirection[]{MoveDirection.MOVE, MoveDirection.MOVE});
        assertEquals(animal4, map.objectAt(new Vector2d(0, 2)));

    }

    @Test
    void testObjectAt() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(0, 0)).build();
        Grass grass = new Grass(new Vector2d(0, 0));
        assertNull(map.objectAt(new Vector2d(0, 0)));
        map.placeGrass(grass);
        assertEquals(grass, map.objectAt(new Vector2d(0, 0)));
        map.placeAnimal(animal1);
        assertEquals(animal1, map.objectAt(new Vector2d(0, 0)));
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
    void testFeedAnimals() {
        Grass grass1 = new Grass(new Vector2d(2, 9));
        Grass grass2 = new Grass(new Vector2d(2, 2));
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 9)).withEnergy(3).build();
        Animal animal2 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 9)).withEnergy(3).build();
        Animal animal3 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 2)).withEnergy(4).build();
        Animal animal4 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 2)).withEnergy(3).build();
        map.placeGrass(grass1);
        map.placeGrass(grass2);
        map.placeAnimal(animal1);
        map.placeAnimal(animal2);
        map.placeAnimal(animal3);
        map.placeAnimal(animal4);
        map.feedAnimals();
        assertFalse(map.grasses.containsKey(grass1.getPosition()));
        assertEquals(3 + grass1.getNutritionValue() / 2, animal1.getEnergy());
        assertEquals(3 + grass1.getNutritionValue() / 2, animal2.getEnergy());

        assertFalse(map.grasses.containsKey(grass2.getPosition()));
        assertEquals(4 + grass2.getNutritionValue(), animal3.getEnergy());
        assertEquals(3, animal4.getEnergy());
    }

    @Test
    void testReproduceAnimals() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 9)).withEnergy(10).build();
        Animal animal2 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 9)).withEnergy(8).build();
        Animal animal3 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 9)).withEnergy(7).build();
        map.placeAnimal(animal1);
        map.placeAnimal(animal2);
        map.placeAnimal(animal3);
        map.reproduceAnimals();
        assertEquals(8, animal1.getEnergy());
        assertEquals(6, animal2.getEnergy());
        assertEquals(7, animal3.getEnergy());
        map.removeAnimal(animal1);
        map.removeAnimal(animal2);
        map.removeAnimal(animal3);
        Animal child = (Animal) map.objectAt(new Vector2d(2, 9));
        assertEquals(4, child.getEnergy());
    }
}