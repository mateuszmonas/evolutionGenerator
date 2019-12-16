package view;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import simulation.SimulationStatus;

public class SettingsPane extends VBox {

    public SettingsPane(SimulationStatus simulationStatus) {
        Button pauseButton = new Button();
        pauseButton.setText("pause");
        pauseButton.setOnAction(event -> simulationStatus.running = !simulationStatus.running);
        getChildren().add(pauseButton);

        Slider intervalSlider = new Slider(1, 1000, 100);
        intervalSlider.setBlockIncrement(1);
        intervalSlider.valueProperty().addListener((observableValue, number, t1) -> simulationStatus.interval = t1.longValue());
        getChildren().add(intervalSlider);
    }
}
