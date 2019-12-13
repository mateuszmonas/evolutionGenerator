package map;

import data.Config;
import data.Rectangle;
import data.Vector2d;
import elements.MapElement;
import elements.animal.Animal;
import elements.grass.Grass;
import view.MapView;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Simulation {

    WorldMap map;
    Rectangle jungleArea;
    MapView view;
    MapHistory statistics = new MapHistory();

    public Simulation() {
        initialize();
    }

    public Simulation(MapView view) {
        this.view = view;
        initialize();
    }

    private void initialize() {
        Config config = Config.getInstance();
        Rectangle mapArea = new Rectangle(new Vector2d(0, 0), new Vector2d(config.getWidth() - 1, config.getHeight() - 1));
        Vector2d jungleLowerLeft = new Vector2d((int) Math.floor((0.5 - config.getJungleRatio() * 0.5) * config.getWidth()),
                (int) Math.floor((0.5 - config.getJungleRatio() * 0.5) * config.getHeight()));
        Vector2d jungleUpperRight = new Vector2d((int) Math.ceil((0.5 + config.getJungleRatio() * 0.5) * config.getWidth()),
                (int) Math.ceil((0.5 + config.getJungleRatio() * 0.5) * config.getHeight()));
        jungleArea = new Rectangle(jungleLowerLeft, jungleUpperRight);
        map = new JungleMap(mapArea);
        generateAnimals(config.getInitialAnimalCount());
    }

    public void simulate() {
        moveAnimals();
        feedAnimals();
        reproduceAnimals();
        generateGrasses();
        statistics.updateHistory(
                map.getElements().values().stream().flatMap(Set::stream).filter(element -> element instanceof Animal).count(),
                map.getElements().values().stream().flatMap(Set::stream).filter(element -> element instanceof Animal).count()
        );
        updateView();
    }

    void updateView() {
        if (view != null) {
            view.updateMap(map.getElements().values().stream()
                    .map(elements -> elements.stream()
                            .reduce((acc, e) -> acc instanceof Animal ? acc : e))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toMap(
                            MapElement::getPosition, e -> e
                    )));
            view.updateStatistics(statistics);
        }
    }

    void moveAnimals() {
        map.getElements().values().stream()
                .flatMap(Set::stream)
                .filter(element -> element instanceof Animal)
                .map(element -> (Animal) element)
                .forEach(Animal::move);
    }

    void feedAnimals() {
        map.getElements().values().forEach(this::feedAnimalsAt);
    }

    void feedAnimalsAt(Set<MapElement> elements) {
        List<Animal> strongestAt = getStrongestAt(elements);
        elements.stream().filter(element -> element instanceof Grass).map(element -> (Grass) element).findAny().ifPresent(grass -> {
            strongestAt
                    .forEach(animal -> animal.increaseEnergy(grass.getNutritionValue() / strongestAt.size()));
            grass.notifyRemove();
        });
    }

    List<Animal> getStrongestAt(Set<MapElement> elements) {
        List<Animal> strongestAt = getAnimalsAt(elements);
        int maxEnergy = strongestAt.stream().map(Animal::getEnergy).max(Integer::compare).orElse(0);
        return strongestAt.stream().filter(animal -> animal.getEnergy() == maxEnergy).collect(Collectors.toList());
    }

    void reproduceAnimals() {
        map.getElements().values().stream().map(this::getAnimalsAt)
                .filter(animals -> animals.size() > 1)
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
