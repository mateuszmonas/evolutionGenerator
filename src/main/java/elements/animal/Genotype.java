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
        for (int i = 0; i < genomeSize; i++) {
            geneCount[genes.get(i)]++;
        }
    }

    Genotype(Genotype genotypeA, Genotype genotypeB) {
        int div1 = ThreadLocalRandom.current().nextInt(1, 30);
        int div2 = ThreadLocalRandom.current().nextInt(div1 + 1, 31);
        int[] tempGeneCount = new int[geneTypeCount];
        Arrays.fill(tempGeneCount, 0);
        for (int i = 0; i < genomeSize; i++) {
            if (i <= div1 || div2 < i) tempGeneCount[genotypeA.genes.get(i)]++;
            else tempGeneCount[genotypeB.genes.get(i)]++;
        }
        for (int i = 0; i < geneTypeCount; i++) {
            if (tempGeneCount[i] == 0) {
                List<Integer> greaterThan1 = new ArrayList<>();
                for (int j = 0; j < geneTypeCount; j++) {
                    if (tempGeneCount[j] > 1) greaterThan1.add(j);
                }
                int randomIndex = greaterThan1.get(ThreadLocalRandom.current().nextInt(greaterThan1.size()));
                tempGeneCount[randomIndex]--;
                tempGeneCount[i]++;
            }
        }
        geneCount = Arrays.copyOf(tempGeneCount, geneTypeCount);
        for (int i = 0; i < geneTypeCount; i++) {
            while (tempGeneCount[i]-- > 0) {
                genes.add(i);
            }
        }
    }

    public int[] getTempGeneCount() {
        return geneCount;
    }

    MapDirection getNewDirection(MapDirection direction) {
        return MapDirection.values()[(direction.ordinal() + genes.get(ThreadLocalRandom.current().nextInt(genomeSize))) % MapDirection.values().length];
    }
}
