import data.Config;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simulation.Simulation;
import simulation.SimulationStatus;
import simulation.WorldSimulation;
import view.SimulationView;
import view.SimulationViewPane;
import view.ViewConfig;

import java.util.ArrayList;
import java.util.List;

import static view.ViewConfig.WINDOW_HEIGHT;
import static view.ViewConfig.WINDOW_WIDTH;

public class Main extends Application {
    List<Simulation> simulations = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello World!");

        SimulationStatus simulationStatus = new SimulationStatus();
        SimulationViewPane root = new SimulationViewPane(simulationStatus);
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


        stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
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
