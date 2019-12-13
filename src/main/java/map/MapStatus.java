package map;

import data.Vector2d;
import elements.MapElement;
import elements.animal.Animal;
import elements.grass.Grass;
import view.MapView;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapStatus {

    MapView view;

    long plantCount;
    long animalCount;
    Map<Vector2d, Set<MapElement>> elements = new HashMap<>();
    Set<Animal> allAnimals = new HashSet<>();
    int[] averageGeneCount;
    long averageEnergy;
    long averageLifeSpan;
    long averageChildCount;

    public MapStatus() {
    }

    public MapStatus(MapView view) {
        this.view = view;
    }



    void update(Map<Vector2d, Set<MapElement>> elements, int currentDay) {
        this.elements = elements;
        Set<Animal> animals =                 elements.values().stream()
                .flatMap(Set::stream)
                .filter(element -> element instanceof Animal)
                .map(element -> (Animal) element)
                .collect(Collectors.toSet());
        allAnimals.addAll(animals);
        plantCount = elements.values().stream()
                .flatMap(Set::stream)
                .filter(element -> element instanceof Grass)
                .count();
        animalCount = animals.size();
        averageEnergy = animals.stream()
                .mapToInt(Animal::getEnergy)
                .sum() / animalCount > 0 ? animalCount : 1;
        averageGeneCount = animals.stream()
                .map(element -> element.getGenotype().getGeneCount())
                .reduce((acc, e) -> {
                    for (int i = 0; i < e.length; i++) {
                        acc[i] += e[i];
                    }
                    return acc;
                }).orElse(new int[]{});
        averageLifeSpan = animals.stream()
                .mapToInt(element -> element.getLifeSpan(currentDay))
                .sum() / animalCount > 0 ? animalCount : 1;
        averageChildCount = animals.stream()
                .mapToInt(Animal::getChildCount)
                .sum() / animalCount > 0 ? animalCount : 1;
    }
}
