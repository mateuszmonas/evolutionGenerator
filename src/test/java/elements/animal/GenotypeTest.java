package elements.animal;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenotypeTest {

    @RepeatedTest(100)
    void testConstructor() {
        Genotype genotype = new Genotype(new Genotype(), new Genotype());
        for (int i = 0; i < genotype.geneTypeCount; i++) {
            final int j = i;
            assertEquals(genotype.genomeSize, genotype.genes.size());
            assertTrue(genotype.genes.stream().anyMatch(g -> g == j));
        }
    }

}