package elements.animal;

import data.Config;
import data.MapDirection;
import data.Vector2d;
import elements.AbstractMapElement;

import java.util.Optional;

public class Animal extends AbstractMapElement {
    private int energy;
    private MapDirection direction;
    private Genotype genotype;

    private Animal() {
    }

    public static Optional<Animal> reproduce(Animal parent1, Animal parent2) {
        Animal child = null;
        if (parent1.energy > Config.getInstance().getInitialAnimalEnergy() / 2 && parent2.energy > Config.getInstance().getInitialAnimalEnergy() / 2) {
            int childEnergy = parent1.energy / 4 + parent2.energy / 4;
            parent1.reduceEnergy(parent1.energy / 4);
            parent2.reduceEnergy(parent2.energy / 4);
            child = Animal.newAnimalBuilder().atPosition(parent1.position).fromParents(parent1, parent2).withEnergy(childEnergy).build();
        }
        return Optional.ofNullable(child);
    }

    public static AnimalBuilder newAnimalBuilder() {
        return new AnimalBuilder();
    }

    public int getEnergy() {
        return energy;
    }

    public void reduceEnergy(int energy) {
        this.energy -= energy;
        if (energy <= 0) notifyRemove();
    }

    public void increaseEnergy(int energy) {
        this.energy += energy;
    }

    public void move() {
        this.direction = genotype.getNewDirection(this.direction);
        Vector2d oldPosition = this.position;
        position = position.add(this.direction.toUnitVector());
        notifyPositionChange(oldPosition);
    }

    @Override
    public String toString() {
        return direction.toString();
    }

    public static class AnimalBuilder {

        Genotype genotype = new Genotype();
        private Vector2d position = new Vector2d(0, 0);
        private int energy = Config.getInstance().getInitialAnimalEnergy();
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
            animal.genotype = genotype;
            return animal;
        }
    }
}
