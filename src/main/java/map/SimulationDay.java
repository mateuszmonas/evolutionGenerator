package map;

import data.Vector2d;
import elements.MapElement;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimulationDay {
    int plantCount;
    int animalCount;
    Map<Vector2d, Set<MapElement>> elements = new HashMap<>();
    int averageEnergy;
    int averageLifeSpan;
    int averageChildCount;


}
