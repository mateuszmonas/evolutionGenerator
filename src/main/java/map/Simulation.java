package map;

import data.Rectangle;
import elements.MapElement;
import elements.animal.Animal;
import elements.grass.Grass;

import java.util.*;
import java.util.stream.Collectors;

public class Simulation {

    WorldMap map;
    Rectangle jungleArea;



    void feedAnimals() {
        map.getElements().values().forEach(this::feedAnimalsAt);
    }

    void feedAnimalsAt(Set<MapElement> elements) {
        List<Animal> strongestAt = getStrongestAt(elements);
        if(strongestAt.isEmpty()) return;
        elements.stream().filter(element -> element instanceof Grass).map(element -> (Grass) element).findAny().ifPresent(grass -> {
            strongestAt
                    .forEach(animal -> animal.increaseEnergy(grass.getNutritionValue() / strongestAt.size()));
            grass.notifyRemove();
        });
    }

    void reproduceAnimals() {
        map.getElements().values().stream().map(this::getAnimalsAt)
                .filter(animals -> animals.size()>1)
                .map(animals -> Animal.reproduce(animals.get(0), animals.get(1)))
                .filter(Optional::isPresent)
                .forEach(animal -> map.addElement(animal.get()));
    }

    List<Animal> getStrongestAt(Set<MapElement> elements) {
        List<Animal> strongestAt = getAnimalsAt(elements);
        int maxEnergy = strongestAt.stream().map(Animal::getEnergy).max(Integer::compare).orElse(0);
        return strongestAt.stream().filter(animal -> animal.getEnergy() == maxEnergy).collect(Collectors.toList());
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
