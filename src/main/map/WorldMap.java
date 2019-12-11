package map;

import elements.animal.Animal;
import elements.animal.AnimalsContainer;
import elements.Grass;
import util.MoveDirection;
import util.Rectangle;
import util.Vector;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class WorldMap implements IWorldMap {

    protected AnimalsContainer animals;
    protected Map<util.Vector, Grass> grasses = new HashMap<>();
    int WIDTH;
    int HEIGHT;
    int MOVE_ENERGY;
    int PLANT_ENERGY;
    int INITIAL_ANIMAL_ENERGY;
    int INITIAL_ANIMAL_COUNT;
    double JUNGLE_RATIO;
    MapVisualizer mapVisualizer = new MapVisualizer(this);
    private util.Vector lowerLeft;
    private util.Vector upperRight;
    private util.Vector jungleLowerLeft;
    private util.Vector jungleUpperRight;
    private int day = 0;

    private WorldMap() {

    }

    public static MapBuilder newMapBuilder() {
        return new MapBuilder();
    }

    void initialize() {
        this.lowerLeft = new util.Vector(0, 0);
        this.upperRight = new util.Vector(this.WIDTH, this.HEIGHT);
        this.jungleLowerLeft = new util.Vector((int) Math.floor((0.5 - JUNGLE_RATIO * 0.5) * this.WIDTH), (int) Math.floor((0.5 - JUNGLE_RATIO * 0.5) * this.HEIGHT));
        this.jungleUpperRight = new util.Vector((int) Math.ceil((0.5 + JUNGLE_RATIO * 0.5) * this.WIDTH), (int) Math.ceil((0.5 + JUNGLE_RATIO * 0.5) * this.HEIGHT));
        if (!lowerLeft.precedes(jungleLowerLeft)) {
            throw new IllegalArgumentException("Jungle lower left corner can't precede map lower left corner");
        }
        if (!upperRight.follows(jungleUpperRight)) {
            throw new IllegalArgumentException("Jungle upper right corner can't follow map upper right corner");
        }
        animals = new AnimalsContainer(new Rectangle(lowerLeft, upperRight));
        generateAnimals();
    }

    void generateAnimals() {
        for (int i = 0; i < INITIAL_ANIMAL_COUNT; i++) {
            int x = ThreadLocalRandom.current().nextInt(lowerLeft.x, upperRight.x + 1);
            int y = ThreadLocalRandom.current().nextInt(lowerLeft.y, upperRight.y + 1);
            util.Vector position = new util.Vector(x, y);
            if (!animals.containsKey(position)) {
                placeAnimal(
                        Animal.newAnimalBuilder().withEnergy(INITIAL_ANIMAL_ENERGY).atPosition(position).build()
                );
            } else {
                i--;
            }
        }
    }

    public void moveAnimals() {
        for (Animal animal : animals) {
            animal.move();
        }
    }

    public void turnAnimals() {
        for (Animal animal : animals) {
            animal.turn();
        }
    }

    void removeDeadAnimals() {
        List<Animal> animalsToRemove = animals.filter(Animal::isDead);
        for (Animal animal : animalsToRemove) {
            removeAnimal(animal);
        }
    }

    public void simulate(boolean visualize) throws InterruptedException {
        removeDeadAnimals();

        if (visualize) visualize(100);

        turnAnimals();

        if (visualize) visualize(100);

        moveAnimals();

        if (visualize) visualize(100);

        feedAnimals();

        if (visualize) visualize(100);

        reproduceAnimals();

        if (visualize) visualize(100);

        generateGrasses();

        if (visualize) visualize(100);

        day++;
    }

    void visualize(int timeout) throws InterruptedException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(day);
        System.out.println(this);
        Thread.sleep(timeout);
    }

    @Override
    public void placeAnimal(Animal animal) throws IllegalArgumentException {
        if (!(lowerLeft.precedes(animal.getPosition()) && upperRight.follows(animal.getPosition()))) {
            throw new IllegalArgumentException(animal.getPosition().toString() + " is out of map bounds");
        }
        animals.add(animal);
        animal.setMap(this);
    }

    @Override
    public boolean canMoveTo(util.Vector position) {
        return true;
    }

    @Override
    public boolean isOccupied(util.Vector position) {
        return animals.containsKey(position);
    }

    @Override
    public void positionChanged(Animal animal, util.Vector oldPosition) {
        if (!animals.containsKey(oldPosition)) {
            throw new IllegalArgumentException("no animal at position " + oldPosition.toString());
        }
        animals.changePosition(animal, oldPosition);
    }

    @Override
    public Object objectAt(util.Vector position) {
        Object obj = animals.get(position);
        if (obj != null) {
            obj = ((Collection<Animal>) obj).stream().max(Comparator.comparingInt(Animal::getEnergy)).get();
        }
        if (obj == null) {
            obj = grasses.getOrDefault(position, null);
        }
        return obj;
    }

    public void feedAnimals() {
        List<Grass> grassesToRemove = new ArrayList<>();
        for (Grass grass : grasses.values()) {
            if (animals.containsKey(grass.getPosition())) {
                Set<Animal> strongestAt = animals.getStrongestAt(grass.getPosition());
                for (Animal animal : strongestAt) {
                    animal.increaseEnergy(grass.getNutritionValue() / strongestAt.size());
                }
                grassesToRemove.add(grass);
            }
        }
        for (Grass grass : grassesToRemove) {
            removeGrass(grass);
        }
    }

    private void generateGrasses() {
        int x, y;
        util.Vector position;
        do {
            x = ThreadLocalRandom.current().nextInt(lowerLeft.x, upperRight.x + 1);
            y = ThreadLocalRandom.current().nextInt(lowerLeft.y, upperRight.y + 1);
            position = new util.Vector(x, y);
        } while ((jungleLowerLeft.precedes(position) && jungleUpperRight.follows(position)));
        grasses.put(position, new Grass(position, PLANT_ENERGY));

        x = ThreadLocalRandom.current().nextInt(jungleLowerLeft.x, jungleUpperRight.x + 1);
        y = ThreadLocalRandom.current().nextInt(jungleLowerLeft.y, jungleUpperRight.y + 1);
        position = new util.Vector(x, y);

        grasses.put(position, new Grass(position, PLANT_ENERGY));
    }

    public void placeGrass(Grass grass) {
        if (grasses.containsKey(grass.getPosition())) {
            throw new IllegalArgumentException(grass.getPosition().toString() + " is already occupied");
        }
        grasses.put(grass.getPosition(), grass);
    }

    public void removeGrass(Grass grass) {
        if (!grasses.containsKey(grass.getPosition())) {
            throw new IllegalArgumentException("No grass at position " + grass.getPosition().toString());
        }
        grasses.remove(grass.getPosition());
    }

    @Override
    public util.Vector getLowerLeft() {
        return lowerLeft;
    }

    @Override
    public util.Vector getUpperRight() {
        return upperRight;
    }

    @Override
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
        animal.removeObserver(this);
    }

    void reproduceAnimals() {
        for (Vector position : animals.getOccupiedPositions()) {
            Set<Animal> animalsAt = animals.get(position);
            if (2 <= animalsAt.size()) {
                Iterator<Animal> a = animalsAt.stream().sorted((animal1, animal2) -> {
                    if (animal1.getEnergy() < animal2.getEnergy()) return 1;
                    else if (animal1.getEnergy() == animal2.getEnergy()) return 0;
                    return -1;
                }).iterator();
                Animal animal1 = a.next();
                Animal animal2 = a.next();
                if (animal1.getEnergy() >= INITIAL_ANIMAL_ENERGY / 2 && animal2.getEnergy() >= INITIAL_ANIMAL_ENERGY / 2) {
                    Animal child = Animal.newAnimalBuilder().atPosition(position).withEnergy(animal1.getEnergy() / 4 + animal2.getEnergy() / 4)
                            .fromParents(animal1, animal2).build();
                    placeAnimal(child);
                    animal1.reduceEnergy(animal1.getEnergy() / 4);
                    animal2.reduceEnergy(animal2.getEnergy() / 4);
                }

            }
        }
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(getLowerLeft(), getUpperRight());
    }

    public static class MapBuilder {
        int WIDTH = 100;
        int HEIGHT = 100;
        int MOVE_ENERGY = 1;
        int PLANT_ENERGY = 2;
        int INITIAL_ANIMAL_ENERGY = 10;
        int INITIAL_ANIMAL_COUNT = 0;
        double JUNGLE_RATIO = 0.5;

        public MapBuilder withWidth(int width) {
            this.WIDTH = width;
            return this;
        }

        public MapBuilder withHeight(int height) {
            this.HEIGHT = height;
            return this;
        }

        public MapBuilder withMoveEnergy(int moveEnergy) {
            this.MOVE_ENERGY = moveEnergy;
            return this;
        }

        public MapBuilder withPlantEnergy(int plantEnergy) {
            this.PLANT_ENERGY = plantEnergy;
            return this;
        }

        public MapBuilder withAnimalEnergy(int animalEnergy) {
            this.INITIAL_ANIMAL_ENERGY = animalEnergy;
            return this;
        }

        public MapBuilder withInitialAnimalCount(int initialAnimalCount) {
            this.INITIAL_ANIMAL_COUNT = initialAnimalCount;
            return this;
        }

        public MapBuilder withJungleRatio(double jungleRatio) {
            this.JUNGLE_RATIO = jungleRatio;
            return this;
        }

        public WorldMap build() {
            WorldMap map = new WorldMap();
            map.WIDTH = WIDTH - 1;
            map.HEIGHT = HEIGHT - 1;
            map.JUNGLE_RATIO = JUNGLE_RATIO;
            map.PLANT_ENERGY = PLANT_ENERGY;
            map.MOVE_ENERGY = MOVE_ENERGY;
            map.INITIAL_ANIMAL_ENERGY = INITIAL_ANIMAL_ENERGY;
            map.INITIAL_ANIMAL_COUNT = INITIAL_ANIMAL_COUNT;
            map.initialize();
            return map;
        }

    }
}
