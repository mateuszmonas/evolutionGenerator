package elements.animal;

import data.MapDirection;
import data.Vector2d;
import elements.AbstractMapElement;
import javafx.util.Pair;
import util.Config;

import java.util.Optional;

public class Animal extends AbstractMapElement {
    int birthDay;
    int deathDay = 0;
    Animal parent1;
    Animal parent2;
    int descendantsCount = 0;
    int childrenCount = 0;
    private int energy;
    private MapDirection direction;
    private Genotype genotype;

    private Animal() {
    }

    public static Optional<Animal> reproduce(Pair<Animal, Animal> parents, Vector2d position, int birthDay) {
        Animal parent1 = parents.getKey();
        Animal parent2 = parents.getValue();
        Animal child = null;
        if (parent1.energy > Config.getInstance().getInitialAnimalEnergy() / 2 && parent2.energy > Config.getInstance().getInitialAnimalEnergy() / 2) {
            int childEnergy = parent1.energy / 4 + parent2.energy / 4;
            parent1.reduceEnergy(parent1.energy / 4);
            parent2.reduceEnergy(parent2.energy / 4);
            child = Animal.newAnimalBuilder().atPosition(position).fromParents(parent1, parent2).withBirthDay(birthDay).withEnergy(childEnergy).build();
            parent1.increaseChildrenCount();
            parent2.increaseChildrenCount();
        }
        return Optional.ofNullable(child);
    }

    public static AnimalBuilder newAnimalBuilder() {
        return new AnimalBuilder();
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public int getEnergy() {
        return energy;
    }

    public void reduceEnergy(int energy) {
        this.energy -= energy;
    }


    @Override
    public Icon getIcon() {
        int initialAnimalEnergy = Config.getInstance().getInitialAnimalEnergy();
        Icon icon;
        if (initialAnimalEnergy / 3 * 2 < energy) icon = Icon.ANIMAL_HIGH_ENERGY;
        else if (initialAnimalEnergy / 3 < energy) icon = Icon.ANIMAL_MEDIUM_ENERGY;
        else icon = Icon.ANIMAL_LOW_ENERGY;
        return icon;
    }

    public boolean isDead(int day) {
        if (energy <= 0) {
            deathDay = day;
            return true;
        }
        return false;
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

    public int getLifeSpan(int currentDay) {
        int lifeSpan = currentDay - birthDay;
        if (deathDay != 0) {
            lifeSpan = deathDay - birthDay;
        }
        return lifeSpan;
    }

    public int getChildCount() {
        return childrenCount;
    }

    public int getDescendantsCount() {
        return descendantsCount;
    }

    public void increaseChildrenCount() {
        childrenCount++;
        increaseDescendantsCount();
    }

    public void increaseDescendantsCount() {
        descendantsCount++;
        if (parent1 != null && parent2 != null) {
            parent1.increaseDescendantsCount();
            parent2.increaseDescendantsCount();
        }
    }

    @Override
    public String toDetails() {
        return "birthDay=" + birthDay +
                "\ndeathDay=" + (deathDay == 0 ? "NaN" : deathDay) +
                "\nchildCount=" + getChildCount() +
                "\ndescendantsCount=" + getDescendantsCount() +
                "\ngenome=" + genotype +
                "\nenergy=" + energy;
    }

    @Override
    public String toString() {
        return genotype.toString();
    }

    public static class AnimalBuilder {

        Genotype genotype = new Genotype();
        Animal parent1;
        Animal parent2;
        private Vector2d position;
        private int energy = Config.getInstance().getInitialAnimalEnergy();
        private MapDirection direction = MapDirection.getRandom();
        private int birthDay;

        public AnimalBuilder atPosition(Vector2d position) {
            this.position = position;
            return this;
        }

        public AnimalBuilder facingDirection(MapDirection direction) {
            this.direction = direction;
            return this;
        }

        public AnimalBuilder fromParents(Animal parent1, Animal parent2) {
            this.parent1 = parent1;
            this.parent2 = parent2;
            genotype = new Genotype(parent1.genotype, parent2.genotype);
            return this;
        }

        public AnimalBuilder withEnergy(int energy) {
            this.energy = energy;
            return this;
        }

        public AnimalBuilder withBirthDay(int birthDay) {
            this.birthDay = birthDay;
            return this;
        }

        public Animal build() {
            Animal animal = new Animal();
            animal.energy = energy;
            animal.position = position;
            animal.direction = direction;
            animal.genotype = genotype;
            animal.birthDay = birthDay;
            animal.parent1 = parent1;
            animal.parent2 = parent2;
            return animal;
        }
    }
}
