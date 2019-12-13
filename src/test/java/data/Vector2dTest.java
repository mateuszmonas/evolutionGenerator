package data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void testEquals() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(2, 3);
        Vector2d v3 = new Vector2d(2, -3);
        assertEquals(v1, v2);
        assertNotEquals(v1, v3);
    }

    @Test
    void testToString() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(2, -3);
        assertEquals(v1.toString(), "(2,3)");
        assertNotEquals(v1.toString(), "(2,4)");
        assertEquals(v2.toString(), "(2,-3)");
    }

    @Test
    void testSmaller() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(2, -3);
        assertFalse(v1.precedes(v2));
        assertTrue(v2.precedes(v1));
    }

    @Test
    void testLarger() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(-2, 3);
        assertTrue(v1.follows(v2));
        assertFalse(v2.follows(v1));
    }

    @Test
    void testUpperRight() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(5, -3);
        assertEquals(v1.upperRight(v2), new Vector2d(5, 3));
        assertEquals(v2.upperRight(v1), new Vector2d(5, 3));
        assertNotEquals(v2.upperRight(v1), new Vector2d(2, -3));
    }

    @Test
    void testLowerLeft() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(5, -3);
        assertNotEquals(v1.lowerLeft(v2), new Vector2d(5, 3));
        assertNotEquals(v2.lowerLeft(v1), new Vector2d(5, 3));
        assertEquals(v2.lowerLeft(v1), new Vector2d(2, -3));
    }

    @Test
    void testOpposite() {
        Vector2d v1 = new Vector2d(2, 3);
        assertEquals(v1.opposite(), new Vector2d(-v1.x, -v1.y));
        assertNotEquals(v1.opposite(), new Vector2d(v1.x, v1.y));
    }

    @Test
    void testAdd() {
        Vector2d v1 = new Vector2d(2, 3);
        Vector2d v2 = new Vector2d(5, -3);
        assertEquals(v1.add(v2), new Vector2d(7, 0));
        assertEquals(v1.add(v2), v2.add(v1));
        assertNotEquals(v1.add(v2), v2);
    }


}