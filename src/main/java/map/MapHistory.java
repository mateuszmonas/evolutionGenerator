package map;

import java.util.ArrayList;
import java.util.List;

public class MapHistory {
    List<Long> animalCountHistory = new ArrayList<>();
    List<Long> plantCountHistory = new ArrayList<>();


    void updateHistory(long animalCount, long plantCount) {
        animalCountHistory.add(animalCount);
        plantCountHistory.add(plantCount);
    }

}
