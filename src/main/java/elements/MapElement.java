package elements;

import data.Vector2d;
import javafx.scene.image.Image;
import map.MapElementObserver;

public interface MapElement {
    Vector2d getPosition();

    void notifyRemove();

    void notifyPositionChange(Vector2d oldPosition);

    void attachObserver(MapElementObserver observer);

    void removeObserver(MapElementObserver observer);

    String toString();

    Icon getIcon();

    enum Icon {

        GROUND("ground.jpg"),
        ANIMAL("animal.jpg"),
        PLANT("plant.jpg");

        public Image image;

        Icon(String path) {
            image = new Image(path);
        }
    }

}
