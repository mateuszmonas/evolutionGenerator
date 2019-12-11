package elements.animal;

import data.MapDirection;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Genotype {
    protected int[] genome = new int[32];

    Genotype(){
        for (int i = 0; i < 8; i++) {
            genome[i] = i;
        }
        for (int i = 8; i < genome.length; i++) {
            genome[i] = ThreadLocalRandom.current().nextInt(8);
        }
        Arrays.sort(genome);
    }

    Genotype(Genotype genotypeA, Genotype genotypeB){
        int div1 = ThreadLocalRandom.current().nextInt(1, 30);
        int div2 = ThreadLocalRandom.current().nextInt(div1, 31);
        System.arraycopy(genotypeA.genome, 0, genome, 0, div1 + 1);
        System.arraycopy(genotypeB.genome, div1 + 1, genome, div1 + 1, div2 - div1);
        System.arraycopy(genotypeA.genome, div2 + 1, genome, div2 + 1, genome.length - div2 - 1);
        Arrays.sort(genome);
        for (int i = 0; i < 7; i++) {
            final int a = i;
            if (Arrays.stream(genome).noneMatch(o -> o == a)) {
                genome[i] = a;
            }
        }
        Arrays.sort(genome);
    }

    MapDirection getNewDirection(MapDirection direction) {
        return MapDirection.values()[(direction.ordinal() + genome[ThreadLocalRandom.current().nextInt(genome.length)]) % MapDirection.values().length];
    }
}
