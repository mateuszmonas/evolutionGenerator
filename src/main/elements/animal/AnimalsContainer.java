package elements.animal;

import util.Rectangle;
import util.Vector;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AnimalsContainer implements Iterable<Animal> {
    protected List<Animal> animals = new ArrayList<>();
    protected Map<Vector, Set<Animal>> occupiedPositions = new HashMap<>();

    Rectangle rectangle;

    public AnimalsContainer(Rectangle rectangle) {
    }

    public boolean isEmpty() {
        return animals.isEmpty() && occupiedPositions.isEmpty();
    }

    public int size() {
        return animals.size();
    }

    public List<Animal> filter(Predicate<Animal> predicate) {
        return animals.stream().filter(predicate).collect(Collectors.toList());
    }

    public Set<Animal> get(Vector position) {
        position = rectangle.normalisePosition(position);
        return occupiedPositions.getOrDefault(position, null);
    }

    public void remove(Animal animal) {
        Vector position = rectangle.normalisePosition(animal.getPosition());
        if (!occupiedPositions.containsKey(position)) {
            throw new IllegalArgumentException("No animal at position " + position.toString());
        }
        occupiedPositions.get(position).remove(animal);
        if (occupiedPositions.get(position).isEmpty())
            occupiedPositions.remove(position);
        animals.remove(animal);
    }

    public void changePosition(Animal animal, Vector oldPosition) {
        oldPosition = rectangle.normalisePosition(oldPosition);
        occupiedPositions.get(oldPosition).remove(animal);
        if (occupiedPositions.get(oldPosition).isEmpty()) {
            occupiedPositions.remove(oldPosition);
        }
        addToOccupiedPositions(animal);
    }

    public Set<Animal> getStrongestAt(Vector position) {
        position = rectangle.normalisePosition(position);
        int highestEnergy = occupiedPositions.get(position).stream().max(Comparator.comparingInt(Animal::getEnergy)).get().getEnergy();
        return occupiedPositions.get(position).stream().filter(animal -> animal.getEnergy() == highestEnergy).collect(Collectors.toSet());
    }

    private void addToOccupiedPositions(Animal animal) {
        Vector position = rectangle.normalisePosition(animal.getPosition());
        if (!occupiedPositions.containsKey(position)) {
            occupiedPositions.put(position, new HashSet<>());
        }
        occupiedPositions.get(position).add(animal);
    }

    public void add(Animal animal) {
        addToOccupiedPositions(animal);
        animals.add(animal);
    }

    public boolean contains(Animal animal) {
        return animals.contains(animal);
    }

    public boolean containsKey(Vector position) {
        position = rectangle.normalisePosition(position);
        return occupiedPositions.containsKey(position);
    }

    public Set<Vector> getOccupiedPositions() {
        return occupiedPositions.keySet();
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
}
