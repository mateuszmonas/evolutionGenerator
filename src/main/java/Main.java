import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import simulation.Simulation;
import simulation.SimulationStatus;
import simulation.WorldSimulation;
import util.Config;
import util.FileUtil;
import view.SimulationView;
import view.SimulationViewPane;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    List<Simulation> simulations = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Generator Ewolucyjny");

        FileUtil.clearFile(Config.getInstance().getStatusDetailsFilePath());
        SimulationStatus simulationStatus = new SimulationStatus();
        SimulationViewPane root = new SimulationViewPane(simulationStatus, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        createSimulations(Config.getInstance().getSimulationCount(), root);
        Thread thread = new Thread(() -> {
            Runnable runnable = this::updateSimulations;
            while (true) {
                try {
                    Thread.sleep(simulationStatus.interval);
                } catch (InterruptedException ignore) {
                }
                if (simulationStatus.running) {
                    Platform.runLater(runnable);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        stage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
        stage.setResizable(false);
        stage.show();
    }

    void updateSimulations() {
        for (Simulation simulation : simulations) {
            simulation.simulate();
        }
    }

    void createSimulations(int amount, SimulationView simulationView) {
        for (int i = 0; i < amount; i++) {
            Simulation simulation = new WorldSimulation();
            simulation.setView(simulationView);
            simulations.add(simulation);
        }
    }

}
