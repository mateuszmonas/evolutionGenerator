package elements.animal;

import data.Config;
import data.MapDirection;
import data.Vector2d;
import elements.AbstractMapElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Animal extends AbstractMapElement {
    int birthDay;
    int deathDay = 0;
    List<Animal> children = new ArrayList<>();
    private int energy;
    private MapDirection direction;
    private Genotype genotype;

    private Animal() {
    }

    public static Optional<Animal> reproduce(Animal parent1, Animal parent2, int birthDay) {
        Animal child = null;
        if (parent1.energy > Config.getInstance().getInitialAnimalEnergy() / 2 && parent2.energy > Config.getInstance().getInitialAnimalEnergy() / 2) {
            int childEnergy = parent1.energy / 4 + parent2.energy / 4;
            parent1.reduceEnergy(parent1.energy / 4);
            parent2.reduceEnergy(parent2.energy / 4);
            child = Animal.newAnimalBuilder().atPosition(parent1.position).fromParents(parent1, parent2).withBirthDay(birthDay).withEnergy(childEnergy).build();
            parent1.children.add(child);
            parent2.children.add(child);
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
        if(initialAnimalEnergy/3*2<energy) icon = Icon.ANIMAL_HIGH_ENERGY;
        else if(initialAnimalEnergy/3<energy) icon = Icon.ANIMAL_MEDIUM_ENERGY;
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
        this.energy = Math.min(this.energy + energy, Config.getInstance().getInitialAnimalEnergy());
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
        return children.size();
    }

    public int getDescendantsCount() {
        if (children.isEmpty()) return 0;
        return getChildCount() + children.stream().mapToInt(Animal::getDescendantsCount).sum();
    }

    @Override
    public String toDetails() {
        return "birthDay=" + birthDay +
                "\ndeathDay=" + (deathDay==0?"NaN":deathDay) +
                "\nchildCount=" + getChildCount() +
                "\ndescendantsCount=" + getDescendantsCount();
    }

    @Override
    public String toString() {
        return genotype.toString();
    }

    public static class AnimalBuilder {

        Genotype genotype = new Genotype();
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
            return animal;
        }
    }
}
