package simulation;

import map.MapStatus;
import view.SimulationView;

public interface Simulation {

    void setView(SimulationView view);

    void simulate();

    MapStatus getMapStatus();

}
