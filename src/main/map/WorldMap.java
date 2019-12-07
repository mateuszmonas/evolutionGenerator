package map;

import elements.Animal;
import elements.Grass;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class WorldMap implements IWorldMap {

    private final int SURVIVAL_ENERGY_COST = 1;
    private Vector2d lowerLeft;
    private Vector2d upperRight;
    private Vector2d jungleLowerLeft;
    private Vector2d jungleUpperRight;
    private Map<Vector2d, Grass> grasses = new HashMap<>();
    private int day = 0;
    protected List<Animal> animals = new ArrayList<>();
    protected Map<Vector2d, Set<Animal>> occupiedPositions = new HashMap<>();

    MapVisualizer mapVisualizer = new MapVisualizer(this);

    @Override
    public void run(MoveDirection[] directions) {

    }

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
    }

    void removeDeadAnimals() {

    }

    public void simulate(boolean visualize) throws InterruptedException {
        removeDeadAnimals();
        if(visualize) visualise(100);

        MoveDirection[] directions = new MoveDirection[animals.size()];
        Arrays.fill(directions, MoveDirection.TURN);
        run(directions);

        if(visualize) visualise(100);

        Arrays.fill(directions, MoveDirection.MOVE);
        run(directions);

        if(visualize) visualise(100);

        generateGrasses();

        if(visualize) visualise(100);
    }

    void visualise(int timeout) throws InterruptedException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(day++);
        System.out.println(this);
        Thread.sleep(timeout);
    }

    @Override
    public void placeAnimal(Animal animal) throws IllegalArgumentException {
        if (!(lowerLeft.precedes(animal.getPosition()) && upperRight.follows(animal.getPosition()))) {
            throw new IllegalArgumentException(animal.getPosition().toString() + " is out of map bounds");
        }
        animals.add(animal);
        addAnimalToPosition(animal, normalisePosition(animal.getPosition()));
    }

    private void addAnimalToPosition(Animal animal, Vector2d position){
        if(!occupiedPositions.containsKey(position)){
            occupiedPositions.put(position, new TreeSet<>((animal1, animal2) -> {
                if(animal1.getEnergy()<animal2.getEnergy())
                    return 1;
                if (animal1 == animal2) {
                    return 0;
                }
                return -1;
            }));
        }
        occupiedPositions.get(position).add(animal);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        position = normalisePosition(position);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        Vector2d normalisedPosition = normalisePosition(position);
        return occupiedPositions.containsKey(normalisedPosition);
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition) {
        oldPosition = normalisePosition(oldPosition);
        Vector2d newPosition = normalisePosition(animal.getPosition());
        if (!occupiedPositions.containsKey(oldPosition)) {
            throw new IllegalArgumentException("no animal at position " + oldPosition.toString());
        }
        occupiedPositions.get(oldPosition).remove(animal);
        if(occupiedPositions.get(oldPosition).isEmpty())
            occupiedPositions.remove(oldPosition);
        addAnimalToPosition(animal, newPosition);
    }

    @Override
    public Object objectAt(Vector2d position) {
        Vector2d normalisedPosition = normalisePosition(position);
        Object obj = occupiedPositions.getOrDefault(normalisedPosition, null);
        if (obj == null) {
            obj = grasses.getOrDefault(normalisedPosition, null);
        }
        return obj;
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

    Vector2d normalisePosition(Vector2d position) {
        int newX, newY;
        if (position.x >= 0) {
            newX = position.x % (getUpperRight().x + 1);
        } else {
            newX = (getUpperRight().x + (1 + position.x) % (getUpperRight().x + 1));
        }
        if (position.y >= 0) {
            newY = position.y % (getUpperRight().y + 1);
        } else {
            newY = (getUpperRight().y + (1 + position.y) % (getUpperRight().y + 1));
        }
        return new Vector2d(newX, newY);
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
        Vector2d position = normalisePosition(animal.getPosition());
        if (!occupiedPositions.containsKey(position)) {
            throw new IllegalArgumentException("No animal at position " + position.toString());
        }
        animals.remove(animal);
        occupiedPositions.get(position).remove(animal);
        if(occupiedPositions.get(position).isEmpty())
            occupiedPositions.remove(position);
    }
}
