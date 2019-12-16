package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import simulation.Simulation;
import simulation.SimulationStatus;
import view.mapStatus.MapStatusViewPane;


public class SimulationViewPane extends BorderPane implements SimulationView {

    HBox maps = new HBox();

    public SimulationViewPane(SimulationStatus simulationStatus) {
        setTop(maps);
        SettingsPane settings = new SettingsPane(simulationStatus);
        setBottom(settings);
    }


    @Override
    public void addSimulation(Simulation simulation) {
        MapStatusViewPane map = new MapStatusViewPane();
        simulation.getMapStatus().setView(map);
        maps.getChildren().add(map);
    }
}
