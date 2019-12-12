package map;

import data.Rectangle;
import elements.MapElement;
import elements.animal.Animal;
import elements.grass.Grass;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Simulation {

    WorldMap map;
    Rectangle jungleArea;

    void reproduceAnimals() {
        map.getElements().values().stream().map(this::getAnimalsAt)
                .filter(animals -> animals.size()>1)
                .map(animals -> Animal.reproduce(animals.get(0), animals.get(1)))
                .filter(Optional::isPresent)
                .forEach(animal -> map.addElement(animal.get()));
    }

    List<Animal> getAnimalsAt(Set<MapElement> elements) {
        return elements.stream().filter(element -> element instanceof Animal)
                .map(element -> (Animal) element)
                .sorted((a1, a2) -> -Integer.compare(a1.getEnergy(), a2.getEnergy()))
                .collect(Collectors.toList());
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
