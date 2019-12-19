package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import simulation.SimulationStatus;
import view.mapStatus.PrintMapStatusToFileListener;
import view.mapStatus.ShowDominantGenomeClickListener;

public class SettingsPane extends HBox {

    boolean showDominantGenome = false;

    ShowDominantGenomeClickListener showDominantGenomeClickListener = value -> {};
    PrintMapStatusToFileListener printMapStatusToFileListener = () -> {};

    public void setOnShowDominantGenomeClick(ShowDominantGenomeClickListener listener) {
        this.showDominantGenomeClickListener = listener;
    }

    public void setPrintMapStatusToFileListener(PrintMapStatusToFileListener printMapStatusToFileListener) {
        this.printMapStatusToFileListener = printMapStatusToFileListener;
    }

    public SettingsPane(SimulationStatus simulationStatus, double prefWidth, double prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);

        this.setAlignment(Pos.CENTER);
        Button pauseButton = new Button();
        Button dominantGenomeButton = new Button();
        Button printMapStatusToFileButton = new Button();

        pauseButton.setText(simulationStatus.running ? "pause" : "start");
        pauseButton.setOnAction(event -> {
            simulationStatus.running = !simulationStatus.running;
            pauseButton.setText(simulationStatus.running ? "pause" : "start");
        });

        dominantGenomeButton.setText(!showDominantGenome ? "mark dominant genome" : "unmark dominant genome");
        dominantGenomeButton.setOnAction(event -> {
            showDominantGenome = !showDominantGenome;
            showDominantGenomeClickListener.showDominantGenomeClicked(showDominantGenome);
            dominantGenomeButton.setText(!showDominantGenome ? "mark dominant genome" : "unmark dominant genome");
        });

        printMapStatusToFileButton.setText("print map status to file");
        printMapStatusToFileButton.setOnAction(event -> printMapStatusToFileListener.printMapStatusToFileClicked());


        Slider intervalSlider = new Slider(100, 1000, 1100 - simulationStatus.interval);
        intervalSlider.setPrefWidth(200);
        intervalSlider.setShowTickLabels(true);
        intervalSlider.setBlockIncrement(1);
        intervalSlider.valueProperty().addListener((observableValue, number, t1) -> simulationStatus.interval = 1100 - t1.longValue());


        getChildren().add(pauseButton);
        getChildren().add(dominantGenomeButton);
        getChildren().add(printMapStatusToFileButton);
        getChildren().add(intervalSlider);
    }
}
