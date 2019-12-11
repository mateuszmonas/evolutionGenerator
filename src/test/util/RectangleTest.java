package util;

import elements.animal.AnimalsContainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    void testNormalisePosition() {
        Rectangle rectangle = new Rectangle(new Vector(0, 0), new Vector(9, 9));
        Vector position = new Vector(-1, 8);
        assertEquals(new Vector(9, 8), rectangle.normalisePosition(position));
        position = new Vector(-11, 8);
        assertEquals(new Vector(9, 8), rectangle.normalisePosition(position));
        position = new Vector(-10, 8);
        assertEquals(new Vector(0, 8), rectangle.normalisePosition(position));

    }

    @Test
    void testContains() {
        Rectangle rectangle = new Rectangle(new Vector(0, 0), new Vector(9, 9));
        assertFalse(rectangle.contains(new Vector(10, 9)));
        assertFalse(rectangle.contains(new Vector(0, 10)));
        assertTrue(rectangle.contains(new Vector(9, 9)));
        assertTrue(rectangle.contains(new Vector(0, 9)));
    }
}