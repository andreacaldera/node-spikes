package com.acal.spike.abtesting.randomizer;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.google.common.collect.Maps.newHashMap;

public class RandomVariant {

    private Random random = new Random();
    private List<Variant> variants;
    private Map<Variant, ThrottleRange>  throttleRanges;

    public RandomVariant(List<Variant> variants) {
        Preconditions.checkArgument(variants != null && variants.size() > 0, "No variants available for the experiment");
        this.variants = variants;
        this.variants.get(0).setFirst(true);
        double sum = variants.stream().mapToDouble(v -> v.getThrottle()).sum();
        variants.add(new BaseVariant(new BigDecimal(1 - sum).round(new MathContext(2, RoundingMode.HALF_UP)).doubleValue()));
        throttleRanges = throttleRanges();
    }

    public Variant random() {
        Double r = random.nextDouble();
        return throttleRanges.values().stream().filter(throttleRange -> r >= throttleRange.min() && r < throttleRange.max()).findFirst().get().value();
    }

    private Map<Variant, ThrottleRange> throttleRanges() {
        Map<Variant, ThrottleRange> result = newHashMap();
        for (int i = 0; i <= variants.size() -1;  i++) {
            final Variant variant = variants.get(i);

            double start = variant.isFirst() ? 0 : result.get(variants.get(i-1)).max();
            double end = variant.isFirst() ? variant.getThrottle() : (variant.isBase() ? 1 : start + variant.getThrottle());

            result.put(variant, new ThrottleRange(variant, start, end));
        }
        return result;
    }

}