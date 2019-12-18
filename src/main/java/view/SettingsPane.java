package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import simulation.SimulationStatus;
import view.mapStatus.ShowDominantGenomeClickListener;

public class SettingsPane extends HBox {

    boolean showDominantGenome = false;

    ShowDominantGenomeClickListener listener = value -> {};

    public void setOnShowDominantGenomeClick(ShowDominantGenomeClickListener listener) {
        this.listener = listener;
    }

    public SettingsPane(SimulationStatus simulationStatus, double prefWidth, double prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);

        this.setAlignment(Pos.CENTER);
        Button pauseButton = new Button();
        pauseButton.setText(simulationStatus.running ? "pause" : "start");
        pauseButton.setOnAction(event -> {
            simulationStatus.running = !simulationStatus.running;
            pauseButton.setText(simulationStatus.running ? "pause" : "start");
        });

        Button dominantGenomeButton = new Button();
        dominantGenomeButton.setText(!showDominantGenome ? "show" : "hide");
        dominantGenomeButton.setOnAction(event -> {
            showDominantGenome = !showDominantGenome;
            listener.showDominantGenomeClicked(showDominantGenome);
            dominantGenomeButton.setText(!showDominantGenome ? "show" : "hide");
        });


        Slider intervalSlider = new Slider(100, 1000, 1100 - simulationStatus.interval);
        intervalSlider.setPrefWidth(200);
        intervalSlider.setShowTickLabels(true);
        intervalSlider.setBlockIncrement(1);
        intervalSlider.valueProperty().addListener((observableValue, number, t1) -> simulationStatus.interval = 1100 - t1.longValue());


        getChildren().add(dominantGenomeButton);
        getChildren().add(pauseButton);
        getChildren().add(intervalSlider);
    }
}
