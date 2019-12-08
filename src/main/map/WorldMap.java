package map;

import elements.Animal;
import elements.AnimalsContainer;
import elements.Grass;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class WorldMap implements IWorldMap {

    private final int SURVIVAL_ENERGY_COST = 1;
    protected AnimalsContainer animals;
    MapVisualizer mapVisualizer = new MapVisualizer(this);
    private Vector2d lowerLeft;
    private Vector2d upperRight;
    private Vector2d jungleLowerLeft;
    private Vector2d jungleUpperRight;
    protected Map<Vector2d, Grass> grasses = new HashMap<>();
    private int day = 0;

    public WorldMap(int width, int height) {
        width = width - 1;
        height = height - 1;
        this.lowerLeft = new Vector2d(0, 0);
        this.upperRight = new Vector2d(width, height);
        this.jungleLowerLeft = new Vector2d((int) Math.floor(0.25 * width), (int) Math.floor(0.25 * height));
        this.jungleUpperRight = new Vector2d((int) Math.ceil(0.25 * width), (int) Math.ceil(0.25 * height));
        if (!lowerLeft.precedes(jungleLowerLeft)) {
            throw new IllegalArgumentException("Jungle lower left corner can't precede map lower left corner");
        }
        if (!upperRight.follows(jungleUpperRight)) {
            throw new IllegalArgumentException("Jungle upper right corner can't follow map upper right corner");
        }
        animals = new AnimalsContainer(upperRight);
    }

    @Override
    public void run(MoveDirection[] directions) {
        int i = 0;
        if (animals.isEmpty()) return;
        for (MoveDirection direction : directions) {
            Animal animal = animals.get(i % animals.size());
            animal.move(direction);
            i++;
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
        MoveDirection[] directions = new MoveDirection[animals.size()];

        if (visualize) visualize(100);

        Arrays.fill(directions, MoveDirection.TURN);
        run(directions);

        if (visualize) visualize(100);

        Arrays.fill(directions, MoveDirection.MOVE);
        run(directions);

        if (visualize) visualize(100);

        feedAnimals();

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
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition) {
        if (!animals.containsKey(oldPosition)) {
            throw new IllegalArgumentException("no animal at position " + oldPosition.toString());
        }
        animals.changePosition(animal, oldPosition);
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object obj = animals.get(position);
        if (obj instanceof TreeSet) {
            obj = ((TreeSet) obj).first();
        }
        if (obj == null) {
            obj = grasses.getOrDefault(position, null);
        }
        return obj;
    }

    public void feedAnimals(){
        List<Grass> grassesToRemove = new ArrayList<>();
        for (Grass grass : grasses.values()) {
            if (animals.containsKey(grass.getPosition())) {
                Set<Animal> strongestAt = animals.getStrongestAt(grass.getPosition());
                for (Animal animal : strongestAt) {
                    animal.increaseEnergy(grass.getNutritionValue()/strongestAt.size());
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
        Vector2d position;
        do {
            x = ThreadLocalRandom.current().nextInt(0, upperRight.x + 1);
            y = ThreadLocalRandom.current().nextInt(0, upperRight.y + 1);
            position = new Vector2d(x, y);
        } while ((jungleLowerLeft.precedes(position) && jungleUpperRight.follows(position)));
        grasses.put(position, new Grass(position));

        x = ThreadLocalRandom.current().nextInt(jungleLowerLeft.x, jungleUpperRight.x + 1);
        y = ThreadLocalRandom.current().nextInt(jungleLowerLeft.y, jungleUpperRight.y + 1);
        position = new Vector2d(x, y);

        grasses.put(position, new Grass(position));
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
    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    @Override
    public Vector2d getUpperRight() {
        return upperRight;
    }

    @Override
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
        animal.removeObserver(this);
    }

    @Override
    public String toString() {
        return mapVisualizer.draw(getLowerLeft(), getUpperRight());
    }
}
