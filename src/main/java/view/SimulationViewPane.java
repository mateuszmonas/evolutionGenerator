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
        this.widthProperty().addListener((observableValue, number, t1) -> {
            settings.setPrefWidth(t1.doubleValue());
            maps.setPrefWidth(t1.doubleValue());
        });
        this.heightProperty().addListener((observableValue, number, t1) -> {
            maps.setPrefHeight((t1.doubleValue()/10)*9);
            settings.setPrefHeight(t1.doubleValue() / 10);
        });
    }


    @Override
    public void addSimulation(Simulation simulation) {
        MapStatusViewPane map = new MapStatusViewPane();
        simulation.getMapStatus().setView(map);
        maps.getChildren().add(map);
    }
}
