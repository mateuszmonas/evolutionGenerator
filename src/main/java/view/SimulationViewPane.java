package view;

import data.Rectangle;
import javafx.scene.layout.BorderPane;
import map.MapStatus;
import simulation.SimulationStatus;
import view.map.MapPane;


public class SimulationViewPane extends BorderPane implements SimulationView {

    MapPane map;

    @Override
    public void updateMap(MapStatus status) {
        map.updateMap(status.getElementsToDisplay(), status.getElements());

    }

    @Override
    public void initialize(Rectangle area) {
        map = new MapPane(area);
        setLeft(map);
    }

    public SimulationViewPane(SimulationStatus simulationStatus) {
        setRight(new SettingsPane(simulationStatus));
    }
}
