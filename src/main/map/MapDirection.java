package map;

public enum MapDirection {
    NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;

    @Override
    public String toString() {
        switch (this) {
            case EAST:
                return "\u21d2";
            case NORTHEAST:
                return "\u21d7";
            case WEST:
                return "\u21d0";
            case SOUTHEAST:
                return "\u21d8";
            case NORTH:
                return "\u21d1";
            case NORTHWEST:
                return "\u21d6";
            case SOUTH:
                return "\u21d3";
            case SOUTHWEST:
                return "\u21d9";
        }
        return null;
    }

    public MapDirection next() {
        return MapDirection.values()[(this.ordinal() + 1) % MapDirection.values().length];
    }

    public MapDirection previous() {
        return MapDirection.values()[(this.ordinal() + MapDirection.values().length - 1) % MapDirection.values().length];
    }

    public Vector2d toUnitVector() {
        switch (this) {
            case NORTH:
                return new Vector2d(0, 1);
            case NORTHEAST:
                return new Vector2d(1, 1);
            case EAST:
                return new Vector2d(1, 0);
            case SOUTHEAST:
                return new Vector2d(1, -1);
            case SOUTH:
                return new Vector2d(0, -1);
            case SOUTHWEST:
                return new Vector2d(-1, -1);
            case WEST:
                return new Vector2d(-1, 0);
            case NORTHWEST:
                return new Vector2d(-1, 1);
        }
        return new Vector2d(0, 0);

    }

}
