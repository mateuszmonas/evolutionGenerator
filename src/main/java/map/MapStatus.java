package map;

import data.Rectangle;
import data.Vector2d;
import elements.MapElement;
import elements.animal.Animal;
import elements.grass.Plant;
import view.mapStatus.MapStatusView;

import java.util.*;
import java.util.stream.Collectors;

public class MapStatus {

    MapStatusView view;

    Map<Vector2d, Set<MapElement>> elements = new HashMap<>();
    Map<Vector2d, MapElement> elementsToDisplay = new HashMap<>();
    Set<Animal> allAnimals = new HashSet<>();
    StatusDetails details = new StatusDetails();

    Rectangle area;

    public MapStatus(Rectangle area, Map<Vector2d, Set<MapElement>> elements) {
        this.area = area;
        update(elements, 0);
    }

    public void setView(MapStatusView view) {
        this.view = view;
        view.initialize(area);
    }

    public void update(Map<Vector2d, Set<MapElement>> elements, int currentDay) {
        this.elements = elements;

        elementsToDisplay = elements.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().stream().reduce(
                        (acc, element) -> {
                            if (acc instanceof Plant) return element;
                            if (element instanceof Plant) return acc;
                            return ((Animal) acc).getEnergy() > ((Animal) element).getEnergy() ? acc : element;
                        }
                ).orElse(Animal.newAnimalBuilder().build())
        ));


        Set<Animal> animals = elements.values().stream()
                .flatMap(Set::stream)
                .filter(element -> element instanceof Animal)
                .map(element -> (Animal) element)
                .collect(Collectors.toSet());
        allAnimals.addAll(animals);
        details.plantCount = elements.values().stream()
                .flatMap(Set::stream)
                .filter(element -> element instanceof Plant)
                .count();
        details.animalCount = animals.size();
        details.averageEnergy = animals.stream()
                .mapToInt(Animal::getEnergy)
                .sum() / (details.animalCount > 0 ? details.animalCount : 1);
        details.averageGeneCount = animals.stream()
                .map(element -> element.getGenotype().getTempGeneCount())
                .reduce((acc, e) -> {
                    int[] result = new int[acc.length];
                    for (int i = 0; i < e.length; i++) {
                        result[i] = acc[i] + e[i];
                    }
                    return result;
                }).orElse(new int[]{});
        details.averageLifeSpan = animals.stream()
                .mapToInt(element -> element.getLifeSpan(currentDay))
                .sum() / (details.animalCount > 0 ? details.animalCount : 1);
        details.averageChildCount = animals.stream()
                .mapToInt(Animal::getChildCount)
                .sum() / (details.animalCount > 0 ? details.animalCount : 1);
        if (view != null) {
            view.updateMap(this);
        }
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

    public StatusDetails getDetails() {
        return details;
    }

    public static class StatusDetails{
        public long plantCount;
        public long animalCount;
        public int[] averageGeneCount;
        public long averageEnergy;
        public long averageLifeSpan;
        public long averageChildCount;

        @Override
        public String toString() {
            return "plantCount=" + plantCount +
                    ", animalCount=" + animalCount +
                    ", averageGeneCount=" + Arrays.toString(averageGeneCount) +
                    ", averageEnergy=" + averageEnergy +
                    ", averageLifeSpan=" + averageLifeSpan +
                    ", averageChildCount=" + averageChildCount;
        }
    }

}
