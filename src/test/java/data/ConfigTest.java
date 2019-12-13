package data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    void testGetInstance() {
        Config config = Config.getInstance();
        assertEquals(config, Config.getInstance());

    }

}