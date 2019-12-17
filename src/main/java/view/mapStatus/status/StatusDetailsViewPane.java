package view.mapStatus.status;

import data.Config;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import map.MapStatus;
import view.ViewConfig;

public class StatusDetailsViewPane extends VBox {

    Text detailsText;

    public StatusDetailsViewPane() {
        detailsText = new Text();
        detailsText.setWrappingWidth((double) ViewConfig.WINDOW_WIDTH / Config.getInstance().getSimulationCount());
        getChildren().add(detailsText);
    }

    public void update(MapStatus.StatusDetails details) {
        detailsText.setText(details.toString());
    }
}
