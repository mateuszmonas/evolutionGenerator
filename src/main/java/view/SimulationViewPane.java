package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import simulation.Simulation;
import simulation.SimulationStatus;
import view.map.MapPane;


public class SimulationViewPane extends BorderPane implements SimulationView {

    HBox maps = new HBox();

    public SimulationViewPane(SimulationStatus simulationStatus) {
        setTop(maps);
        setBottom(new SettingsPane(simulationStatus));
    }


    @Override
    public void addSimulation(Simulation simulation) {
        MapPane map = new MapPane();
        simulation.getMapStatus().setView(map);
        maps.getChildren().add(map);
    }
}
