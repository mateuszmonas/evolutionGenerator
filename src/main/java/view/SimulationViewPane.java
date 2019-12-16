package view;

import data.Rectangle;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import map.MapStatus;
import simulation.Simulation;
import simulation.SimulationStatus;
import view.map.MapPane;

import java.util.HashMap;
import java.util.Map;


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
