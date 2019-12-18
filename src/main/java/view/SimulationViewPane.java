package view;

import data.Config;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import simulation.Simulation;
import simulation.SimulationStatus;
import view.mapStatus.MapStatusViewPane;


public class SimulationViewPane extends VBox implements SimulationView {

    HBox maps = new HBox();

    public SimulationViewPane(SimulationStatus simulationStatus, double prefWidth, double prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);

        SettingsPane settings = new SettingsPane(simulationStatus, prefWidth, prefHeight / 10);

        maps.setPrefWidth(prefWidth);
        maps.setPrefHeight(prefHeight/10*9);

        this.getChildren().add(maps);
        this.getChildren().add(settings);


    }

    @Override
    public void addSimulation(Simulation simulation) {
        MapStatusViewPane map = new MapStatusViewPane(maps.getPrefWidth() / Config.getInstance().getSimulationCount(), maps.getPrefHeight());

        simulation.getMapStatus().setView(map);
        maps.getChildren().add(map);
    }
}
