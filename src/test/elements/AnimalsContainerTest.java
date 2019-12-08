package elements;

import map.MapDirection;
import map.MoveDirection;
import map.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalsContainerTest {
    AnimalsContainer animals = new AnimalsContainer(new Vector2d(10, 10));

    @Test
    void TestIsEmpty() {
        Animal animal1 = Animal.newAnimalBuilder().build();
        assertTrue(animals.isEmpty());
        animals.add(animal1);
        assertFalse(animals.isEmpty());
        animals.remove(animal1);
        assertTrue(animals.isEmpty());
    }

    @Test
    void TestSize() {
        Animal animal1 = Animal.newAnimalBuilder().build();
        Animal animal2 = Animal.newAnimalBuilder().build();
        assertEquals(0, animals.size());
        animals.add(animal1);
        assertEquals(1, animals.size());
        animals.add(animal2);
        assertEquals(2, animals.size());
        animals.remove(animal1);
        animals.remove(animal2);
        assertEquals(0, animals.size());
    }

    @Test
    void TestGet() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 2)).build();
        Animal animal2 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 2)).build();
        animals.add(animal1);
        animals.add(animal2);
        assertEquals(animal1, animals.get(0));
        assertTrue(animals.get(new Vector2d(2, 2)).contains(animal1));
        assertTrue(animals.get(new Vector2d(2, 2)).contains(animal2));
        animals.remove(animal1);
        assertEquals(animal2, animals.get(0));
    }

    @Test
    void TestFilter() {
        Animal animal1 = Animal.newAnimalBuilder().withEnergy(0).build();
        Animal animal2 = Animal.newAnimalBuilder().withEnergy(1).build();
        Animal animal3 = Animal.newAnimalBuilder().withEnergy(-2).build();
        animals.add(animal1);
        animals.add(animal2);
        animals.add(animal3);
        List<Animal> deadAnimals = animals.filter(Animal::isDead);
        assertTrue(deadAnimals.contains(animal1));
        assertFalse(deadAnimals.contains(animal2));
        assertTrue(deadAnimals.contains(animal3));
    }

    @Test
    void TestRemove() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 2)).build();
        animals.add(animal1);
        animals.remove(animal1);
        assertNull(animals.get(new Vector2d(2, 2)));
    }

    @Test
    void TestChangePosition() {
        Animal animal1 = Animal.newAnimalBuilder().facingDirection(MapDirection.NORTH).atPosition(new Vector2d(2, 2)).build();
        animals.add(animal1);
        Vector2d oldPosition = new Vector2d(2, 2);
        animal1.move(MoveDirection.MOVE);
        animals.changePosition(animal1, oldPosition);
        assertNull(animals.get(new Vector2d(2, 2)));
        assertNotNull(animals.get(new Vector2d(2, 3)));

    }

    @Test
    void TestAdd() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 2)).build();
        assertNull(animals.get(new Vector2d(2, 2)));
        assertTrue(animals.isEmpty());
        animals.add(animal1);
        assertEquals(animal1, animals.get(0));
        assertNotNull(animals.get(new Vector2d(2, 2)));
    }

    @Test
    void TestContainsKey() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 2)).build();
        Animal animal2 = Animal.newAnimalBuilder().atPosition(new Vector2d(2, 2)).build();
        animals.add(animal1);
        animals.add(animal2);
        assertTrue(animals.containsKey(new Vector2d(2, 2)));
        animals.remove(animal1);
        assertTrue(animals.containsKey(new Vector2d(2, 2)));
        animals.remove(animal2);
        assertFalse(animals.containsKey(new Vector2d(2, 2)));
    }


    @Test
    void TestNormalisePosition() {
        AnimalsContainer animals = new AnimalsContainer(new Vector2d(9, 9));
        Vector2d position = new Vector2d(-1, 8);
        assertEquals(new Vector2d(9, 8), animals.normalisePosition(position));
        position = new Vector2d(-11, 8);
        assertEquals(new Vector2d(9, 8), animals.normalisePosition(position));
        position = new Vector2d(-10, 8);
        assertEquals(new Vector2d(0, 8), animals.normalisePosition(position));
    }

}