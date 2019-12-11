package map;

import elements.IMapElement;
import data.Rectangle;
import data.Vector;

import java.util.*;

public class JungleMap implements WorldMap {

    protected Map<Vector, Set<IMapElement>> elements = new HashMap<>();
    int WIDTH;
    int HEIGHT;
    int MOVE_ENERGY;
    int PLANT_ENERGY;
    int INITIAL_ANIMAL_ENERGY;
    int INITIAL_ANIMAL_COUNT;
    double JUNGLE_RATIO;
    private Rectangle mapArea;
    private Rectangle jungleArea;

    public JungleMap(Rectangle mapArea) {
        this.mapArea = mapArea;
        int width = mapArea.getWidth();
        int height = mapArea.getHeight();
        Vector jungleLowerLeft = new data.Vector((int) Math.floor((0.5 - JUNGLE_RATIO * 0.5) * width), (int) Math.floor((0.5 - JUNGLE_RATIO * 0.5) * height));
        Vector jungleUpperRight = new data.Vector((int) Math.ceil((0.5 + JUNGLE_RATIO * 0.5) * width), (int) Math.ceil((0.5 + JUNGLE_RATIO * 0.5) * height));
        this.jungleArea = new Rectangle(jungleLowerLeft, jungleUpperRight);
    }

    public static MapBuilder newMapBuilder() {
        return new MapBuilder();
    }

    void initialize() {
    }

    public void moveAnimals() {
    }

    public void addElement(IMapElement element) throws IllegalArgumentException {
        if (!mapArea.contains(element.getPosition())) {
            throw new IllegalArgumentException(element.getPosition().toString() + " is out of map bounds");
        }
        if (!elements.containsKey(element.getPosition())) {
            elements.put(element.getPosition(), new HashSet<>());
        }
        elements.get(element.getPosition()).add(element);
    }

    @Override
    public void onPositionChange(IMapElement element, Vector oldPosition) {
        if (!elements.containsKey(oldPosition)) {
            throw new IllegalArgumentException("no element at position " + oldPosition.toString());
        }

        oldPosition = mapArea.normalisePosition(oldPosition);
        elements.get(oldPosition).remove(element);
        if (elements.get(oldPosition).isEmpty()) {
            elements.remove(oldPosition);
        }
        addElement(element);
    }

    public Set<IMapElement> objectsAt(Vector position) {
        position = mapArea.normalisePosition(position);
        return elements.getOrDefault(position, null);
    }

    public void removeElement(IMapElement element) {
        Vector position = mapArea.normalisePosition(element.getPosition());
        if (!elements.containsKey(position)) {
            throw new IllegalArgumentException("No animal at position " + position.toString());
        }
        elements.get(position).remove(element);
        if (elements.get(position).isEmpty())
            elements.remove(position);

    }

    @Override
    public void onRemoval(IMapElement element) {
        removeElement(element);
    }

    public static class MapBuilder {
        int WIDTH = 100;
        int HEIGHT = 100;
        int MOVE_ENERGY = 1;
        int PLANT_ENERGY = 2;
        int INITIAL_ANIMAL_ENERGY = 10;
        int INITIAL_ANIMAL_COUNT = 0;
        double JUNGLE_RATIO = 0.5;

        public MapBuilder withWidth(int width) {
            this.WIDTH = width;
            return this;
        }

        public MapBuilder withHeight(int height) {
            this.HEIGHT = height;
            return this;
        }

        public MapBuilder withMoveEnergy(int moveEnergy) {
            this.MOVE_ENERGY = moveEnergy;
            return this;
        }

        public MapBuilder withPlantEnergy(int plantEnergy) {
            this.PLANT_ENERGY = plantEnergy;
            return this;
        }

        public MapBuilder withAnimalEnergy(int animalEnergy) {
            this.INITIAL_ANIMAL_ENERGY = animalEnergy;
            return this;
        }

        public MapBuilder withInitialAnimalCount(int initialAnimalCount) {
            this.INITIAL_ANIMAL_COUNT = initialAnimalCount;
            return this;
        }

        public MapBuilder withJungleRatio(double jungleRatio) {
            this.JUNGLE_RATIO = jungleRatio;
            return this;
        }

        public JungleMap build() {
            JungleMap map = new JungleMap(new Rectangle(new Vector(0,0), new Vector(0,0)));
            map.WIDTH = WIDTH - 1;
            map.HEIGHT = HEIGHT - 1;
            map.JUNGLE_RATIO = JUNGLE_RATIO;
            map.PLANT_ENERGY = PLANT_ENERGY;
            map.MOVE_ENERGY = MOVE_ENERGY;
            map.INITIAL_ANIMAL_ENERGY = INITIAL_ANIMAL_ENERGY;
            map.INITIAL_ANIMAL_COUNT = INITIAL_ANIMAL_COUNT;
            map.initialize();
            return map;
        }

    }
}
