package elements.animal;

import data.MapDirection;
import data.Vector2d;
import elements.AbstractMapElement;
import map.MapElementObserver;

import java.util.HashSet;
import java.util.Set;

public class Animal extends AbstractMapElement {
    private int energy;
    private MapDirection direction;
    private Genotype genotype;
    private Set<MapElementObserver> observers = new HashSet<>();

    public Animal() {
    }

    public static Animal reproduce(Animal parent1, Animal parent2) {
        int childEnergy = parent1.energy / 4 + parent2.energy / 4;
        parent1.reduceEnergy(parent1.energy / 4);
        parent2.reduceEnergy(parent2.energy / 4);
        return Animal.newAnimalBuilder().atPosition(parent1.position).fromParents(parent1, parent2).withEnergy(childEnergy).build();
    }

    public static AnimalBuilder newAnimalBuilder() {
        return new AnimalBuilder();
    }

    public boolean canReproduce() {
        return true;
    }

    public int getEnergy() {
        return energy;
    }

    public void reduceEnergy(int energy) {
        this.energy -= energy;
    }

    public void increaseEnergy(int energy) {
        this.energy += energy;
    }

    public void turn() {
        this.direction = genotype.getNewDirection(this.direction);
    }

    public void move() {
        Vector2d oldPosition = this.position;
        position = position.add(this.direction.toUnitVector());
        notifyPositionChange(oldPosition);
    }

    public boolean isDead() {
        return energy <= 0;
    }

    @Override
    public String toString() {
        return direction.toString();
    }

    public static class AnimalBuilder {

        Genotype genotype;
        private Vector2d position = new Vector2d(0, 0);
        private int energy = 20;
        private MapDirection direction = MapDirection.getRandom();

        public AnimalBuilder atPosition(Vector2d position) {
            this.position = position;
            return this;
        }

        public AnimalBuilder facingDirection(MapDirection direction) {
            this.direction = direction;
            return this;
        }

        public AnimalBuilder fromParents(Animal parent1, Animal parent2) {
            genotype = new Genotype(parent1.genotype, parent2.genotype);
            return this;
        }

        public AnimalBuilder withEnergy(int energy) {
            this.energy = energy;
            return this;
        }

        public Animal build() {
            Animal animal = new Animal();
            animal.energy = energy;
            animal.position = position;
            animal.direction = direction;
            if (genotype == null) {
                genotype = new Genotype();
            }
            animal.genotype = genotype;
            return animal;
        }
    }
}
