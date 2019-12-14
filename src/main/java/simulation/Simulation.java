package simulation;

import data.Config;
import data.Rectangle;
import data.Vector2d;
import elements.MapElement;
import elements.animal.Animal;
import elements.grass.Grass;
import map.JungleWorldMap;
import map.MapStatus;
import map.WorldMap;
import view.SimulationView;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Simulation {

    WorldMap map;
    Rectangle jungleArea;
    MapStatus mapStatus;
    int day;

    public Simulation(SimulationView view) {
        day = 0;
        Config config = Config.getInstance();
        Rectangle mapArea = new Rectangle(new Vector2d(0, 0), new Vector2d(config.getWidth() - 1, config.getHeight() - 1));
        Vector2d jungleLowerLeft = new Vector2d((int) Math.floor((0.5 - config.getJungleRatio() * 0.5) * config.getWidth()),
                (int) Math.floor((0.5 - config.getJungleRatio() * 0.5) * config.getHeight()));
        Vector2d jungleUpperRight = new Vector2d((int) Math.ceil((0.5 + config.getJungleRatio() * 0.5) * config.getWidth()),
                (int) Math.ceil((0.5 + config.getJungleRatio() * 0.5) * config.getHeight()));
        jungleArea = new Rectangle(jungleLowerLeft, jungleUpperRight);
        map = new JungleWorldMap(mapArea);
        generateAnimals(config.getInitialAnimalCount());
        this.mapStatus = new MapStatus(mapArea, view, map.getElements());
    }

    public void simulate() {
        removeDeadAnimals();
        moveAnimals();
        feedAnimals();
        reproduceAnimals();
        generateGrasses();
        mapStatus.update(map.getElements(), day);
        day++;
    }

    void removeDeadAnimals() {
        map.getElements().values().stream()
                .flatMap(Set::stream)
                .filter(element -> element instanceof Animal)
                .map(element -> (Animal) element)
                .filter(animal -> animal.isDead(day))
                .forEach(Animal::notifyRemove);
    }

    void moveAnimals() {
        map.getElements().values().stream()
                .flatMap(Set::stream)
                .filter(element -> element instanceof Animal)
                .map(element -> (Animal) element)
                .collect(Collectors.toList())
                .forEach(Animal::move);
    }

    void feedAnimals() {
        map.getElements().values().forEach(this::feedAnimalsAt);
    }

    void feedAnimalsAt(Set<MapElement> elements) {
        List<Animal> strongestAt = getStrongestAt(elements);
        if(strongestAt.isEmpty()) return;
        elements.stream()
                .filter(element -> element instanceof Grass)
                .map(element -> (Grass) element).findAny()
                .ifPresent(g -> {
                    g.notifyRemove();
                    strongestAt.forEach(animal -> animal.increaseEnergy(g.getNutritionValue() / strongestAt.size()));
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
                .map(animals -> Animal.reproduce(animals.get(0), animals.get(1), day))
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
            map.getUnoccupiedPosition().ifPresent(position -> map.addElement(Animal.newAnimalBuilder().withBirthDay(day).atPosition(position).build()));
        }
    }

}
