package elements.animal;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GenotypeTest {

    @Test
    void testConstructor() {
        Genotype genotype = new Genotype(new Genotype(), new Genotype());
        for (int i = 0; i < 8; i++) {
            final int j = i;
            assertTrue(Arrays.stream(genotype.genome).anyMatch(g -> g == j));
        }
    }

}