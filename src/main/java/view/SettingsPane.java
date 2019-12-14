package view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import simulation.SimulationStatus;

public class SettingsPane extends VBox {

    public SettingsPane(SimulationStatus simulationStatus) {
        Button btn = new Button();
        btn.setText("pause");
        btn.setOnAction(event -> simulationStatus.running = !simulationStatus.running);
        getChildren().add(btn);

    }
}
