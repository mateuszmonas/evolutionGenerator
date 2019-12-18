package simulation;

import data.Config;
import data.Rectangle;
import data.Vector2d;
import elements.MapElement;
import elements.animal.Animal;
import elements.plant.Plant;
import map.JungleWorldMap;
import map.MapStatus;
import map.WorldMap;
import view.SimulationView;

import java.util.*;
import java.util.stream.Collectors;

public class WorldSimulation implements Simulation {

    WorldMap map;
    Rectangle jungleArea;
    MapStatus mapStatus;
    int day;

    public WorldSimulation() {
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
        this.mapStatus = new MapStatus(mapArea, map.getElements());
    }

    @Override
    public MapStatus getMapStatus() {
        return mapStatus;
    }

    public void simulate() {
        Map<Vector2d, Set<Animal>> animals = getAnimals();
        Map<Vector2d, Plant> plants = map.getElements().entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        entry.getKey(),
                        entry.getValue().stream()
                                .filter(element -> element instanceof Plant)
                                .map(element -> (Plant) element)
                                .findAny()
                                .orElse(null)))
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue
                ));


        removeDeadAnimals(animals);
        moveAnimals(animals);
        animals = getAnimals();
        feedAnimals(animals, plants);
        reproduceAnimals(animals);
        generateGrasses();
        mapStatus.update(map.getElements());
        day++;
    }

    private Map<Vector2d, Set<Animal>> getAnimals() {
        return map.getElements().entrySet().stream().map(
                entry -> new AbstractMap.SimpleEntry<>(
                        entry.getKey(),
                        entry.getValue().stream()
                                .filter(element -> element instanceof Animal)
                                .map(element -> (Animal) element)
                                .collect(Collectors.toSet())
                ))
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue
                ));
    }

    @Override
    public void setView(SimulationView view) {
        view.addSimulation(this);
    }

    void removeDeadAnimals(Map<Vector2d, Set<Animal>> animals) {
        animals.values().stream()
                .flatMap(Set::stream)
                .filter(animal -> animal.isDead(day))
                .collect(Collectors.toList())
                .forEach(animal -> {
                    mapStatus.updateAverageLifeSpan(animal.getLifeSpan(day));
                    animal.notifyRemove();
                });
    }

    void moveAnimals(Map<Vector2d, Set<Animal>> animals) {
        animals.values().stream()
                .flatMap(Set::stream)
                .forEach(animal -> {
                    animal.move();
                    animal.reduceEnergy(1);
                });
    }

    void feedAnimals(Map<Vector2d, Set<Animal>> animals, Map<Vector2d, Plant> plants) {
        animals.entrySet().stream()
                .filter(entry -> plants.containsKey(entry.getKey()))
                .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), getStrongestAnimals(entry.getValue())))
                .forEach(entry -> {
                    entry.getValue().forEach(animal -> animal.increaseEnergy(plants.get(entry.getKey()).getNutritionValue()/entry.getValue().size()));
                    plants.get(entry.getKey()).notifyRemove();
                });
    }

    List<Animal> getStrongestAnimals(Set<Animal> animals) {
        int maxEnergy = animals.stream().map(Animal::getEnergy).max(Integer::compare).orElse(0);
        return animals.stream().filter(animal -> animal.getEnergy() == maxEnergy).collect(Collectors.toList());
    }

    // TODO: 2019-12-16 animal should spawn on adjacent position
    void reproduceAnimals(Map<Vector2d, Set<Animal>> animals) {
        animals.values().stream()
                .filter(animalsSet -> animalsSet.size() > 1)
                .map(animalsSet -> animalsSet.stream().sorted((a1, a2) -> -Integer.compare(a1.getEnergy(), a2.getEnergy())).iterator())
                .map(animalsSet -> Animal.reproduce(animalsSet.next(), animalsSet.next(), day))
                .filter(Optional::isPresent)
                .forEach(animal -> map.addElement(animal.get()));
    }

    void generateGrasses() {
        map.getUnoccupiedPositionInArea(jungleArea).ifPresent(position -> map.addElement(new Plant(position)));
        map.getUnoccupiedPositionNotInArea(jungleArea).ifPresent(position -> map.addElement(new Plant(position)));
    }

    void generateAnimals(int amount) {
        for (int i = 0; i < amount; i++) {
            map.getUnoccupiedPosition().ifPresent(position -> map.addElement(Animal.newAnimalBuilder().withBirthDay(day).atPosition(position).build()));
        }
    }

}
