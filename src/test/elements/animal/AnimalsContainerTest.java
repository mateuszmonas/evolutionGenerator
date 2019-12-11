package elements.animal;

import util.MapDirection;
import util.MoveDirection;
import util.Rectangle;
import util.Vector;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AnimalsContainerTest {
    AnimalsContainer animals = new AnimalsContainer(new Rectangle(new Vector(0, 0), new Vector(10, 10)));

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
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector(2, 2)).build();
        Animal animal2 = Animal.newAnimalBuilder().atPosition(new Vector(2, 2)).build();
        animals.add(animal1);
        animals.add(animal2);
        assertTrue(animals.contains(animal1));
        assertTrue(animals.get(new Vector(2, 2)).contains(animal1));
        assertTrue(animals.get(new Vector(2, 2)).contains(animal2));
        animals.remove(animal1);
        assertTrue(animals.contains(animal2));
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
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector(2, 2)).build();
        animals.add(animal1);
        animals.remove(animal1);
        assertNull(animals.get(new Vector(2, 2)));
    }

    @Test
    void TestChangePosition() {
        Animal animal1 = Animal.newAnimalBuilder().facingDirection(MapDirection.NORTH).atPosition(new Vector(2, 2)).build();
        animals.add(animal1);
        Vector oldPosition = new Vector(2, 2);
        animal1.move();
        animals.changePosition(animal1, oldPosition);
        assertNull(animals.get(new Vector(2, 2)));
        assertNotNull(animals.get(new Vector(2, 3)));

    }

    @Test
    void TestAdd() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector(2, 2)).build();
        assertNull(animals.get(new Vector(2, 2)));
        assertTrue(animals.isEmpty());
        animals.add(animal1);
        assertTrue(animals.contains(animal1));
        assertNotNull(animals.get(new Vector(2, 2)));
    }

    @Test
    void TestContainsKey() {
        Animal animal1 = Animal.newAnimalBuilder().atPosition(new Vector(2, 2)).build();
        Animal animal2 = Animal.newAnimalBuilder().atPosition(new Vector(2, 2)).build();
        animals.add(animal1);
        animals.add(animal2);
        assertTrue(animals.containsKey(new Vector(2, 2)));
        animals.remove(animal1);
        assertTrue(animals.containsKey(new Vector(2, 2)));
        animals.remove(animal2);
        assertFalse(animals.containsKey(new Vector(2, 2)));
    }

    @Test
    void TestGetStrongestAt() {
        Animal animal1 = Animal.newAnimalBuilder().withEnergy(0).atPosition(new Vector(2, 2)).build();
        Animal animal2 = Animal.newAnimalBuilder().withEnergy(2).atPosition(new Vector(2, 2)).build();
        Animal animal3 = Animal.newAnimalBuilder().withEnergy(2).atPosition(new Vector(2, 2)).build();
        animals.add(animal1);
        animals.add(animal2);
        animals.add(animal3);
        Set<Animal> strongest = animals.getStrongestAt(new Vector(2, 2));
        assertFalse(strongest.contains(animal1));
        assertTrue(strongest.contains(animal2));
        assertTrue(strongest.contains(animal3));
    }

}