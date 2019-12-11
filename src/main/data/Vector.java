package data;

import java.util.Objects;

public class Vector {
    public final int x, y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean precedes(Vector vector) {
        return vector.x >= this.x && vector.y >= this.y;
    }

    public boolean follows(Vector vector) {
        return vector.x <= this.x && vector.y <= this.y;
    }

    public Vector upperRight(Vector vector) {
        return new Vector(Math.max(this.x, vector.x), Math.max(this.y, vector.y));
    }

    public Vector lowerLeft(Vector vector) {
        return new Vector(Math.min(this.x, vector.x), Math.min(this.y, vector.y));
    }

    public Vector add(Vector vector) {
        return new Vector(this.x + vector.x, this.y + vector.y);
    }

    public Vector subtract(Vector vector) {
        return new Vector(this.x - vector.x, this.y - vector.y);
    }

    public Vector opposite() {
        return new Vector(-this.x, -this.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Vector))
            return false;
        Vector that = (Vector) obj;
        return that.x == this.x && that.y == this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
