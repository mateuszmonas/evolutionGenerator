import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simulation.Simulation;
import simulation.SimulationStatus;
import view.SimulationView;
import view.SimulationViewPane;
import view.map.MapPane;

import static view.ViewConfig.WINDOW_HEIGHT;
import static view.ViewConfig.WINDOW_WIDTH;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello World!");

        SimulationStatus simulationStatus = new SimulationStatus();
        SimulationViewPane root = new SimulationViewPane(simulationStatus);
        Simulation simulation = new Simulation(root);

        Thread thread = new Thread(() -> {
            Runnable runnable = simulation::simulate;
            while (true) {
                try {
                    Thread.sleep(simulationStatus.interval);
                } catch (InterruptedException ignore) {}
                if (simulationStatus.running) {
                    Platform.runLater(runnable);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

}
