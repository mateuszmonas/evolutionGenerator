package map;

import data.Rectangle;
import data.Vector2d;
import elements.MapElement;
import elements.animal.Animal;
import elements.animal.Genotype;
import elements.plant.Plant;
import view.mapStatus.MapStatusView;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapStatus {

    MapStatusView view;

    ElementsPositions elementsPositions = new ElementsPositions();
    StatusDetails details = new StatusDetails();

    Rectangle area;

    public MapStatus(Rectangle area, Map<Vector2d, Set<MapElement>> elements) {
        this.area = area;
        update(elements, 0);
    }

    public Rectangle getArea() {
        return area;
    }

    public void setView(MapStatusView view) {
        this.view = view;
        view.initialize(this);
    }

    public void update(Map<Vector2d, Set<MapElement>> elements, int day) {
        details.day = day;
        elementsPositions.changedPositions = new HashSet<>(elements.keySet());
        elementsPositions.changedPositions.addAll(elementsPositions.elementsToDisplay.keySet());
        elementsPositions.elements = elements;


        elementsPositions.elementsToDisplay = elements.entrySet().stream().collect(Collectors.toMap(
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
        details.plantCount = elements.values().stream()
                .flatMap(Set::stream)
                .filter(element -> element instanceof Plant)
                .count();
        details.animalCount = animals.size();
        details.averageEnergy = animals.stream()
                .mapToInt(Animal::getEnergy)
                .sum() / (double) (details.animalCount > 0 ? details.animalCount : 1);

        details.dominantGenome = animals.stream()
                .collect(Collectors.groupingBy(Animal::getGenotype, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(new AbstractMap.SimpleEntry<>(Genotype.EMPTY, 0L)).getKey().getGeneCount();

        elementsPositions.dominatingGenomeElementsPositions = animals.stream()
                .filter(animal -> Arrays.equals(animal.getGenotype()
                        .getGeneCount(), details.dominantGenome))
                .map(animal -> area.normalisePosition(animal.getPosition()))
                .collect(Collectors.toSet());

        details.averageChildCount = animals.stream()
                .mapToInt(Animal::getChildCount)
                .sum() / (double) (details.animalCount > 0 ? details.animalCount : 1);

        if (elementsPositions.trackedElement != null)
            elementsPositions.trackedElementPosition = area.normalisePosition(elementsPositions.trackedElement.getPosition());

        if (view != null)
            view.updateMap(this);

    }

    public ElementsPositions getElementsPositions() {
        return elementsPositions;
    }

    public void setTrackedElement(MapElement element) {
        elementsPositions.trackedElement = elementsPositions.trackedElement != element ? element : null;
        if (elementsPositions.trackedElement != null)
            elementsPositions.trackedElementPosition = area.normalisePosition(element.getPosition());
        else elementsPositions.trackedElementPosition = null;
        view.trackedElementChange(elementsPositions.trackedElement, elementsPositions.trackedElementPosition);
    }

    public void updateAverageLifeSpan(int lifeSpan) {
        details.lifeSpanSum += lifeSpan;
        details.averageLifeSpan = (double) details.lifeSpanSum / ++details.deadAnimalCount;
    }

    public StatusDetails getDetails() {
        return details;
    }

    public static class ElementsPositions {
        public Map<Vector2d, Set<MapElement>> elements = new HashMap<>();
        public Map<Vector2d, MapElement> elementsToDisplay = new HashMap<>();
        public Set<Vector2d> dominatingGenomeElementsPositions = new HashSet<>();
        public MapElement trackedElement = null;
        public Vector2d trackedElementPosition = null;
        public Set<Vector2d> changedPositions = new HashSet<>();
    }

    public static class StatusDetails {
        public int day;
        public int deadAnimalCount = 0;
        public long lifeSpanSum;
        public long plantCount;
        public long animalCount;
        public int[] dominantGenome;
        public double averageEnergy;
        public double averageLifeSpan;
        public double averageChildCount;

        @Override
        public String toString() {
            return "day=" + day +
                    "\nplantCount=" + plantCount +
                    "\nanimalCount=" + animalCount +
                    "\ndominantGenome=" + Arrays.toString(dominantGenome) +
                    "\naverageEnergy=" + averageEnergy +
                    "\naverageLifeSpan=" + averageLifeSpan +
                    "\naverageChildCount=" + averageChildCount;
        }
    }

}
