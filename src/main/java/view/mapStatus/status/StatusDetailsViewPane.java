package view.mapStatus.status;

import elements.MapElement;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import map.MapStatus;
import util.Config;
import util.FileUtil;

import java.io.IOException;

public class StatusDetailsViewPane extends StackPane {

    HBox details = new HBox();
    Text mapDetailsText;
    Text trackedElementDetailsText;

    public StatusDetailsViewPane(double prefWidth, double prefHeight) {
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);

        setAlignment(Pos.BOTTOM_CENTER);

        getChildren().add(details);

        mapDetailsText = new Text();
        mapDetailsText.setWrappingWidth(getPrefWidth()/2);
        details.getChildren().add(mapDetailsText);

        trackedElementDetailsText = new Text();
        trackedElementDetailsText.setWrappingWidth(getPrefWidth()/2);
        details.getChildren().add(trackedElementDetailsText);
    }

    public void printStatusToFile() {
        try {
            FileUtil.writeToFile(Config.getInstance().getStatusDetailsFilePath(), mapDetailsText.getText());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateTrackedElementDetails(MapElement element) {
        if (element != null) {
            trackedElementDetailsText.setText(element.toDetails());
        }
    }

    public void update(MapStatus.StatusDetails details, MapElement trackedElement) {
        mapDetailsText.setText(details.toString());
        updateTrackedElementDetails(trackedElement);
    }
}
