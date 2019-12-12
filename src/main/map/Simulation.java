package map;

import data.Rectangle;
import elements.animal.Animal;
import elements.grass.Grass;

public class Simulation {

    WorldMap map;
    Rectangle jungleArea;

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
