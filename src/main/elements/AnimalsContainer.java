package elements;

import map.Vector2d;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AnimalsContainer implements Iterable<Animal> {
    protected List<Animal> animals = new ArrayList<>();
    protected Map<Vector2d, TreeSet<Animal>> occupiedPositions = new HashMap<>();

    private Vector2d upperRight;

    public AnimalsContainer(Vector2d upperRight) {
        this.upperRight = upperRight;
    }

    public boolean isEmpty() {
        return animals.isEmpty() && occupiedPositions.isEmpty();
    }

    public int size() {
        return animals.size();
    }

    public Animal get(int index) {
        return animals.get(index);
    }

    public List<Animal> filter(Predicate<Animal> predicate) {
        return animals.stream().filter(predicate).collect(Collectors.toList());
    }

    public Set<Animal> get(Vector2d position) {
        position = normalisePosition(position);
        return occupiedPositions.getOrDefault(position, null);
    }

    public void remove(Animal animal) {
        Vector2d position = normalisePosition(animal.getPosition());
        if (!occupiedPositions.containsKey(position)) {
            throw new IllegalArgumentException("No animal at position " + position.toString());
        }
        occupiedPositions.get(position).remove(animal);
        if (occupiedPositions.get(position).isEmpty())
            occupiedPositions.remove(position);
        animals.remove(animal);
    }

    public void changePosition(Animal animal, Vector2d oldPosition) {
        oldPosition = normalisePosition(oldPosition);
        occupiedPositions.get(oldPosition).remove(animal);
        if (occupiedPositions.get(oldPosition).isEmpty()) {
            occupiedPositions.remove(oldPosition);
        }
        addToOccupiedPositions(animal);
    }

    public Set<Animal> getStrongestAt(Vector2d position) {
        position = normalisePosition(position);
        int highestEnergy = occupiedPositions.get(position).first().getEnergy();
        return occupiedPositions.get(position).stream().filter(animal -> animal.getEnergy()==highestEnergy).collect(Collectors.toSet());
    }

    private void addToOccupiedPositions(Animal animal) {
        Vector2d position = normalisePosition(animal.getPosition());
        if (!occupiedPositions.containsKey(position)) {
            occupiedPositions.put(position, new TreeSet<>((animal1, animal2) -> {
                if (animal1.getEnergy() < animal2.getEnergy())
                    return 1;
                else if (animal1 == animal2) {
                    return 0;
                }
                return -1;
            }));
        }
        occupiedPositions.get(position).add(animal);
    }

    public void add(Animal animal) {
        addToOccupiedPositions(animal);
        animals.add(animal);
    }

    public boolean containsKey(Vector2d position) {
        position = normalisePosition(position);
        return occupiedPositions.containsKey(position);
    }

    @Override
    public Iterator<Animal> iterator() {
        return animals.iterator();
    }

    @Override
    public void forEach(Consumer<? super Animal> action) {
        for (Animal animal : animals) {
            action.accept(animal);
        }
    }

    @Override
    public Spliterator<Animal> spliterator() {
        return null;
    }

    Vector2d normalisePosition(Vector2d position) {
        int newX, newY;
        if (position.x >= 0) {
            newX = position.x % (upperRight.x + 1);
        } else {
            newX = (upperRight.x + (1 + position.x) % (upperRight.x + 1));
        }
        if (position.y >= 0) {
            newY = position.y % (upperRight.y + 1);
        } else {
            newY = (upperRight.y + (1 + position.y) % (upperRight.y + 1));
        }
        return new Vector2d(newX, newY);
    }

}
