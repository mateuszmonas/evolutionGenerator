package view.mapStatus.status;

import data.Config;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import map.MapStatus;
import view.ViewConfig;

public class StatusDetailsViewPane extends Pane {

    Text detailsText;

    public void update(MapStatus.StatusDetails details) {
//        detailsText.setText(details.toString());
    }

    public StatusDetailsViewPane() {
        detailsText = new Text();
//        detailsText.prefWidth((double) ViewConfig.WINDOW_WIDTH / Config.getInstance().getSimulationCount());
        getChildren().add(detailsText);
    }
}
