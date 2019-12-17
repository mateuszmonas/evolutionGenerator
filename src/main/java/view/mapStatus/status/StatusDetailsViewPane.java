package view.mapStatus.status;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import map.MapStatus;

public class StatusDetailsViewPane extends StackPane {

    Text detailsText;

    public StatusDetailsViewPane() {
        detailsText = new Text();
        detailsText.wrappingWidthProperty().bind(this.widthProperty());
        getChildren().add(detailsText);
    }

    public void update(MapStatus.StatusDetails details) {
        detailsText.setText(details.toString());
    }
}
