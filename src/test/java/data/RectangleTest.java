package data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    void testNormalisePosition() {
        Rectangle rectangle = new Rectangle(new Vector2d(0, 0), new Vector2d(9, 9));
        Vector2d position = new Vector2d(-1, 8);
        assertEquals(new Vector2d(9, 8), rectangle.normalisePosition(position));
        position = new Vector2d(-11, 8);
        assertEquals(new Vector2d(9, 8), rectangle.normalisePosition(position));
        position = new Vector2d(-10, 8);
        assertEquals(new Vector2d(0, 8), rectangle.normalisePosition(position));

    }

    @Test
    void testContains() {
        Rectangle rectangle = new Rectangle(new Vector2d(0, 0), new Vector2d(9, 9));
        assertFalse(rectangle.contains(new Vector2d(10, 9)));
        assertFalse(rectangle.contains(new Vector2d(0, 10)));
        assertTrue(rectangle.contains(new Vector2d(9, 9)));
        assertTrue(rectangle.contains(new Vector2d(0, 9)));
    }
}