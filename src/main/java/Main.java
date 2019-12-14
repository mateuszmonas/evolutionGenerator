import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import map.Simulation;
import view.MapPane;

import static view.ViewConfig.WINDOW_HEIGHT;
import static view.ViewConfig.WINDOW_WIDTH;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello World!");

        MapPane root = new MapPane();
        Simulation simulation = new Simulation(root);

        Thread thread = new Thread(() -> {
            Runnable runnable = simulation::simulate;
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignore) {}
                Platform.runLater(runnable);
            }
        });
        thread.setDaemon(true);
        thread.start();

        stage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

}
