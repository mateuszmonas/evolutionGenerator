package view;

import data.Config;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import simulation.Simulation;
import simulation.SimulationStatus;
import view.mapStatus.MapStatusViewPane;

import java.awt.*;


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
        map.setPrefWidth((double) ViewConfig.WINDOW_WIDTH / Config.getInstance().getSimulationCount());
        map.setPrefHeight((double) ViewConfig.WINDOW_HEIGHT * 9 / 10);
        simulation.getMapStatus().setView(map);
        maps.getChildren().add(map);
    }
}
