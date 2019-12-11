package data;

import java.util.concurrent.ThreadLocalRandom;

public class Rectangle {

    Vector2d lowerLeft;
    Vector2d upperRight;

    public Rectangle(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public Vector2d getRandomInBound() {
        int x = ThreadLocalRandom.current().nextInt(lowerLeft.x, upperRight.x + 1);
        int y = ThreadLocalRandom.current().nextInt(lowerLeft.y, upperRight.y + 1);
        return new Vector2d(x, y);
    }

    public int getWidth() {
        return this.upperRight.x - this.lowerLeft.x + 1;
    }

    public int getHeight() {
        return this.upperRight.y - this.lowerLeft.y + 1;
    }

    public Vector2d normalisePosition(Vector2d position) {
        int newX, newY;
        if (position.x >= 0) {
            newX = position.x % (upperRight.x + 1);
        } else {
            newX = (upperRight.x + (1 + position.x) % (upperRight.x + 1));
        }
        if (position.y >= 0) {
            newY = position.y % (upperRight.y + 1);
        } else {
            newY = (upperRight.y + (1 + position.y) % (upperRight.y + 1));
        }
        return new Vector2d(newX, newY);
    }

    public boolean contains(Vector2d vector2d) {
        return lowerLeft.precedes(vector2d) && upperRight.follows(vector2d);
    }

}
