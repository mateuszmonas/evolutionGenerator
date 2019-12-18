package view.mapStatus.status;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import map.MapStatus;

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

    public void update(MapStatus.StatusDetails details) {
        mapDetailsText.setText(details.toString());
        trackedElementDetailsText.setText(details.toString());
    }
}
