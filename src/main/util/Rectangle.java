package util;

public class Rectangle {

    Vector lowerLeft;
    Vector upperRight;

    public Rectangle(Vector lowerLeft, Vector upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public Vector normalisePosition(Vector position) {
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
        return new Vector(newX, newY);
    }

    boolean contains(Vector vector) {
        return lowerLeft.precedes(vector) && upperRight.follows(vector);
    }

}
