package view;

import data.Config;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import simulation.Simulation;
import simulation.SimulationStatus;
import view.mapStatus.MapStatusViewPane;


public class SimulationViewPane extends VBox implements SimulationView {

    HBox maps = new HBox(10);

    public SimulationViewPane(SimulationStatus simulationStatus) {
        SettingsPane settings = new SettingsPane(simulationStatus);

        maps.prefWidthProperty().bind(this.widthProperty());
        maps.prefHeightProperty().bind(this.heightProperty().divide(10).multiply(9));
        maps.minWidthProperty().bind(this.widthProperty());
        maps.minHeightProperty().bind(this.heightProperty().divide(10).multiply(9));
        maps.maxWidthProperty().bind(this.widthProperty());
        maps.maxHeightProperty().bind(this.heightProperty().divide(10).multiply(9));

        settings.prefWidthProperty().bind(this.widthProperty());
        settings.prefHeightProperty().bind(this.heightProperty().divide(10));
        settings.minWidthProperty().bind(this.widthProperty());
        settings.minHeightProperty().bind(this.heightProperty().divide(10));
        settings.maxWidthProperty().bind(this.widthProperty());
        settings.maxHeightProperty().bind(this.heightProperty().divide(10));

        getChildren().add(maps);
        getChildren().add(settings);
    }


    @Override
    public void addSimulation(Simulation simulation) {
        MapStatusViewPane map = new MapStatusViewPane();


        map.prefWidthProperty().bind(maps.widthProperty().subtract(maps.getSpacing()).divide(Config.getInstance().getSimulationCount()));
        map.prefHeightProperty().bind(maps.heightProperty());
        map.minWidthProperty().bind(maps.widthProperty().subtract(maps.getSpacing()).divide(Config.getInstance().getSimulationCount()));
        map.minHeightProperty().bind(maps.heightProperty());
        map.maxWidthProperty().bind(maps.widthProperty().subtract(maps.getSpacing()).divide(Config.getInstance().getSimulationCount()));
        map.maxHeightProperty().bind(maps.heightProperty());

        simulation.getMapStatus().setView(map);
        maps.getChildren().add(map);
    }
}
