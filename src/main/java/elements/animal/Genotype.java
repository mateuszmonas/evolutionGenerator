package elements.animal;

import data.MapDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Genotype {
    protected List<Integer> genes = new ArrayList<>(32);
    int genomeSize = 32;
    int geneTypeCount = 8;
    int[] geneCount = new int[geneTypeCount];

    Genotype() {
        for (int i = 0; i < geneTypeCount; i++) {
            genes.add(i);
        }
        for (int i = geneTypeCount; i < genomeSize; i++) {
            genes.add(ThreadLocalRandom.current().nextInt(8));
        }
        genes.sort(Integer::compareTo);
    }

    Genotype(Genotype genotypeA, Genotype genotypeB) {
        int div1 = ThreadLocalRandom.current().nextInt(1, 30);
        int div2 = ThreadLocalRandom.current().nextInt(div1 + 1, 31);
        Arrays.fill(geneCount, 0);
        for (int i = 0; i < genomeSize; i++) {
            if (i <= div1 || div2 < i) geneCount[genotypeA.genes.get(i)]++;
            else geneCount[genotypeB.genes.get(i)]++;
        }
        for (int i = 0; i < geneTypeCount; i++) {
            if (geneCount[i] == 0) {
                List<Integer> greaterThan1 = new ArrayList<>();
                for (int j = 0; j < geneTypeCount; j++) {
                    if (geneCount[j] > 1) greaterThan1.add(j);
                }
                int randomIndex = greaterThan1.get(ThreadLocalRandom.current().nextInt(greaterThan1.size()));
                geneCount[randomIndex]--;
                geneCount[i]++;
            }
        }
        for (int i = 0; i < geneTypeCount; i++) {
            while (geneCount[i]-- > 0) {
                genes.add(i);
            }
        }
    }

    public int[] getGeneCount() {
        return geneCount;
    }

    MapDirection getNewDirection(MapDirection direction) {
        return MapDirection.values()[(direction.ordinal() + genes.get(ThreadLocalRandom.current().nextInt(genomeSize))) % MapDirection.values().length];
    }
}
