package map;

import elements.animal.Animal;

public class Simulation {

    WorldMap map;

    void generateGrasses() {

    }

    void generateAnimals(int amount) {
        for (int i = 0; i < amount; i++) {
            map.getUnoccupiedPosition().ifPresent(position -> map.addElement(Animal.newAnimalBuilder().atPosition(position).build()));
        }
    }

}
