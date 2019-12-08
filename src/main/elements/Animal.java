package elements;

import map.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Animal implements IMapElement {
    private int MIN_REPRODUCTION_ENERGY = 5;
    private int energy;
    private MapDirection direction;
    private Vector2d position;
    private IWorldMap map;
    private int[] genome = new int[32];
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

    public boolean canReproduce() {
        return energy >= MIN_REPRODUCTION_ENERGY;
    }

    public int[] getGenome() {
        return genome;
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
                this.direction = MapDirection.values()[(this.direction.ordinal() + genome[ThreadLocalRandom.current().nextInt(genome.length)]) % MapDirection.values().length];
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
        private int[] genome;

        public AnimalBuilder atPosition(Vector2d position) {
            this.position = position;
            return this;
        }

        public AnimalBuilder facingDirection(MapDirection direction) {
            this.direction = direction;
            return this;
        }

        public AnimalBuilder fromParents(Animal parent1, Animal parent2) {
            genome = generateGenome(parent1.genome, parent2.genome);
            return this;
        }

        public AnimalBuilder withEnergy(int energy) {
            this.energy = energy;
            return this;
        }

        private int[] generateGenome(int[] genomeA, int[] genomeB) {
            int[] genome = new int[32];
            int div1 = ThreadLocalRandom.current().nextInt(1, 30);
            int div2 = ThreadLocalRandom.current().nextInt(div1, 31);
            System.arraycopy(genomeA, 0, genome, 0, div1 + 1);
            System.arraycopy(genomeB, div1 + 1, genome, div1 + 1, div2 - div1);
            System.arraycopy(genomeA, div2 + 1, genome, div2 + 1, genome.length - div2 - 1);
            Arrays.sort(genome);
            for (int i = 0; i < 7; i++) {
                final int a = i;
                if (Arrays.stream(genome).noneMatch(o -> o == a)) {
                    genome[i] = a;
                }
            }
            Arrays.sort(genome);
            return genome;
        }

        private int[] generateGenome() {
            int[] genome = new int[32];
            for (int i = 0; i < 8; i++) {
                genome[i] = i;
            }
            for (int i = 8; i < genome.length; i++) {
                genome[i] = ThreadLocalRandom.current().nextInt(8);
            }
            Arrays.sort(genome);
            return genome;
        }

        public Animal build() {
            Animal animal = new Animal();
            animal.energy = energy;
            animal.position = position;
            animal.direction = direction;
            if (genome == null) {
                genome = generateGenome();
            }
            animal.genome = genome;
            return animal;
        }
    }

}
