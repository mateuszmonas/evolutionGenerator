package elements.animal;

import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenotypeTest {

    @RepeatedTest(100)
    void testConstructor() {
        Genotype genotype = new Genotype(new Genotype(), new Genotype());
        for (int i = 0; i < Genotype.geneTypeCount; i++) {
            final int j = i;
            assertEquals(Genotype.genomeSize, genotype.genes.size());
            assertTrue(genotype.genes.stream().anyMatch(g -> g == j));
        }
    }

}