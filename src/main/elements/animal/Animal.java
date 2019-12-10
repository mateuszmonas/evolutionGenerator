package elements.animal;

import elements.IMapElement;
import map.*;

import java.util.HashSet;
import java.util.Set;

public class Animal implements IMapElement {
    private int energy;
    private MapDirection direction;
    private Vector2d position;
    private IWorldMap map;
    private Genotype genotype;
    private Set<IPositionChangeObserver> observers = new HashSet<>();

    public Animal() {
    }

    public static AnimalBuilder newAnimalBuilder() {
        return new AnimalBuilder();
    }

    public void setMap(WorldMap map) {
        addObserver(map);
        this.map = map;
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

    public void increaseEnergy(int energy) {
        this.energy += energy;
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case TURN:
                this.direction = genotype.getNewDirection(this.direction);
                break;
            case MOVE:
                Vector2d oldPosition = this.position;
                position = position.add(this.direction.toUnitVector());
                observers.forEach(e -> e.positionChanged(this, oldPosition));
                break;
        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    public Vector2d getPosition() {
        return position;
    }

    public boolean isDead() {
        return energy <= 0;
    }

    @Override
    public String toString() {
        return direction.toString();
    }

    public static class AnimalBuilder {

        private Vector2d position = new Vector2d(0, 0);
        private int energy = 20;
        private MapDirection direction = MapDirection.NORTH;
        Genotype genotype;

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
