package map;

import org.junit.jupiter.api.Test;
import data.MapDirection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapDirectionTest {

    @Test
    void testToString() {

    }

    @Test
    void next() {
        assertEquals(MapDirection.NORTH, MapDirection.NORTHWEST.next());
        assertEquals(MapDirection.NORTHEAST, MapDirection.NORTH.next());
        assertEquals(MapDirection.EAST, MapDirection.NORTHEAST.next());
        assertEquals(MapDirection.SOUTHEAST, MapDirection.EAST.next());
        assertEquals(MapDirection.SOUTH, MapDirection.SOUTHEAST.next());
        assertEquals(MapDirection.SOUTHWEST, MapDirection.SOUTH.next());
        assertEquals(MapDirection.WEST, MapDirection.SOUTHWEST.next());
        assertEquals(MapDirection.NORTHWEST, MapDirection.WEST.next());

    }

    @Test
    void previous() {
        assertEquals(MapDirection.NORTH, MapDirection.NORTHEAST.previous());
        assertEquals(MapDirection.NORTHEAST, MapDirection.EAST.previous());
        assertEquals(MapDirection.EAST, MapDirection.SOUTHEAST.previous());
        assertEquals(MapDirection.SOUTHEAST, MapDirection.SOUTH.previous());
        assertEquals(MapDirection.SOUTH, MapDirection.SOUTHWEST.previous());
        assertEquals(MapDirection.SOUTHWEST, MapDirection.WEST.previous());
        assertEquals(MapDirection.WEST, MapDirection.NORTHWEST.previous());
        assertEquals(MapDirection.NORTHWEST, MapDirection.NORTH.previous());
    }

    @Test
    void toUnitVector() {
        assertEquals(MapDirection.NORTH, MapDirection.NORTHEAST.previous());
        assertEquals(MapDirection.NORTHEAST, MapDirection.EAST.previous());
        assertEquals(MapDirection.EAST, MapDirection.SOUTHEAST.previous());
        assertEquals(MapDirection.SOUTHEAST, MapDirection.SOUTH.previous());
        assertEquals(MapDirection.SOUTH, MapDirection.SOUTHWEST.previous());
        assertEquals(MapDirection.SOUTHWEST, MapDirection.WEST.previous());
        assertEquals(MapDirection.WEST, MapDirection.NORTHWEST.previous());
        assertEquals(MapDirection.NORTHWEST, MapDirection.NORTH.previous());
    }
}