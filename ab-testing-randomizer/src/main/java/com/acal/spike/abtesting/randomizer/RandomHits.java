package com.acal.spike.abtesting.randomizer;

import java.util.ArrayList;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

public class RandomHits {

    private static final int TOTAL = 10000000;
    private static final ArrayList<Variant> VARIANTS = newArrayList(new Variant("variant a", 0.15), new Variant("variant a", 0.45));
    private static final RandomVariant RANDOM_VARIANT = new RandomVariant(VARIANTS);

    public static final void main(String[] args) {
        System.out.println("Random hits");
        Map<Variant, Integer> randomBuckets = randomHits();
        for (Variant variant : randomBuckets.keySet()) {
            System.out.println("Variant " + variant + " got served " + (double)randomBuckets.get(variant)/TOTAL + "%");
        }
    }

    private static Map<Variant, Integer> randomHits() {
        Map<Variant, Integer> result = newHashMap();
        for (int i = 0; i < TOTAL;  i++) {
            Variant randomVariant = RANDOM_VARIANT.random();
            result.put(randomVariant, result.get(randomVariant) != null ? result.get(randomVariant) + 1 : 0);
        }
        return result;
    }

}
