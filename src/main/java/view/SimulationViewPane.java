package view;

import data.Config;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import simulation.Simulation;
import simulation.SimulationStatus;
import view.mapStatus.MapStatusViewPane;

import java.util.HashSet;
import java.util.Set;


public class SimulationViewPane extends VBox implements SimulationView {

    HBox maps = new HBox();
    Set<MapStatusViewPane> mapStatusesViewPanes = new HashSet<>();

    public SimulationViewPane(SimulationStatus simulationStatus, double prefWidth, double prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);

        SettingsPane settings = new SettingsPane(simulationStatus, prefWidth, prefHeight / 10);
        settings.setOnShowDominantGenomeClick(value -> mapStatusesViewPanes.forEach(mapStatusViewPane -> mapStatusViewPane.showDominantGenome(value)));
        settings.setPrintMapStatusToFileListener(() -> new Thread(() -> mapStatusesViewPanes.forEach(MapStatusViewPane::printStatusToFile)).start());

        maps.setPrefWidth(prefWidth);
        maps.setPrefHeight(prefHeight/10*9);

        this.getChildren().add(maps);
        this.getChildren().add(settings);
    }

    @Override
    public void addSimulation(Simulation simulation) {
        MapStatusViewPane map = new MapStatusViewPane(maps.getPrefWidth() / Config.getInstance().getSimulationCount(), maps.getPrefHeight());
        mapStatusesViewPanes.add(map);

        simulation.getMapStatus().setView(map);
        maps.getChildren().add(map);
    }
}
