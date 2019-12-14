package map;

import data.Rectangle;
import data.Vector2d;
import elements.MapElement;
import elements.animal.Animal;
import elements.grass.Grass;
import view.MapView;

import java.util.*;
import java.util.stream.Collectors;

public class MapStatus {

    MapView view;

    long plantCount;
    long animalCount;
    Map<Vector2d, Set<MapElement>> elements = new HashMap<>();
    Map<Vector2d, MapElement> elementsToDisplay = new HashMap<>();
    Set<Animal> allAnimals = new HashSet<>();
    int[] averageGeneCount;
    long averageEnergy;
    long averageLifeSpan;
    long averageChildCount;

    public MapStatus(Rectangle area, MapView view, Map<Vector2d, Set<MapElement>> elements) {
        this.view = view;
        view.initialize(area);
        update(elements, 0);
    }

    void update(Map<Vector2d, Set<MapElement>> elements, int currentDay) {
        this.elements = elements;
        elementsToDisplay = elements.values().stream().map(ArrayList::new).map(e -> e.stream().sorted((o1, o2) -> {
            if (o1 instanceof Grass) return 1;
            if (o2 instanceof Grass) return -1;
            return -Integer.compare(((Animal) o1).getEnergy(), ((Animal) o2).getEnergy());
        }).collect(Collectors.toList())).map(e -> e.get(0)).collect(Collectors.toMap(
                MapElement::getPosition,
                e -> e
        ));
        Set<Animal> animals = elements.values().stream()
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
        if (view != null) {
            view.updateMap(this);
        }
    }

    public MapView getView() {
        return view;
    }

    public long getPlantCount() {
        return plantCount;
    }

    public long getAnimalCount() {
        return animalCount;
    }

    public Map<Vector2d, Set<MapElement>> getElements() {
        return elements;
    }

    public Map<Vector2d, MapElement> getElementsToDisplay() {
        return elementsToDisplay;
    }

    public Set<Animal> getAllAnimals() {
        return allAnimals;
    }

    public int[] getAverageGeneCount() {
        return averageGeneCount;
    }

    public long getAverageEnergy() {
        return averageEnergy;
    }

    public long getAverageLifeSpan() {
        return averageLifeSpan;
    }

    public long getAverageChildCount() {
        return averageChildCount;
    }
}
