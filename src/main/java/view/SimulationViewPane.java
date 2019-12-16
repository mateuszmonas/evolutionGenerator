package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import simulation.Simulation;
import simulation.SimulationStatus;
import view.mapStatus.MapStatusViewPane;


public class SimulationViewPane extends BorderPane implements SimulationView {

    HBox maps = new HBox();

    public SimulationViewPane(SimulationStatus simulationStatus) {
        setTop(maps);
        setBottom(new SettingsPane(simulationStatus));
    }


    @Override
    public void addSimulation(Simulation simulation) {
        MapStatusViewPane map = new MapStatusViewPane();
        simulation.getMapStatus().setView(map);
        maps.getChildren().add(map);
    }
}
