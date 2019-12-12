package map;

import data.Rectangle;
import elements.MapElement;
import elements.animal.Animal;
import elements.grass.Grass;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Simulation {

    WorldMap map;
    Rectangle jungleArea;

    void reproduceAnimals() {
        map.getElements().values().stream().map(this::getAnimalsAt)
                .filter(animals -> animals.size()>1)
                .map(animals -> Animal.reproduce(animals.last(), animals.lower(animals.last())))
                .filter(Optional::isPresent)
                .forEach(animal -> map.addElement(animal.get()));
    }

    TreeSet<Animal> getAnimalsAt(Set<MapElement> elements) {
        TreeSet<Animal> result = new TreeSet<>((animal1, animal2) -> {
            if (animal1.getEnergy() < animal2.getEnergy())
                return 1;
            else if (animal1.getEnergy() == animal2.getEnergy()) {
                return Integer.compare(animal1.hashCode(), animal2.hashCode());
            }
            return -1;
        });
        result.addAll(elements.stream().filter(element -> element instanceof Animal)
                .map(element -> (Animal) element)
                .collect(Collectors.toSet()));
        return result;
    }

    void generateGrasses() {
        map.getUnoccupiedPositionInArea(jungleArea).ifPresent(position -> map.addElement(new Grass(position)));
        map.getUnoccupiedPositionNotInArea(jungleArea).ifPresent(position -> map.addElement(new Grass(position)));
    }

    void generateAnimals(int amount) {
        for (int i = 0; i < amount; i++) {
            map.getUnoccupiedPosition().ifPresent(position -> map.addElement(Animal.newAnimalBuilder().atPosition(position).build()));
        }
    }

}
