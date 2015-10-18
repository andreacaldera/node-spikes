package com.acal.spike.abtesting.randomizer;

import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ThrottleRange {

    private final Variant value;

    private final double min;
    private final double max;

    public ThrottleRange(Variant variant, double min, double max) {
        Preconditions.checkArgument(min >= 0, "Invalid throttle min " + min);
        Preconditions.checkArgument(max <= 1, "Invalid throttle max " + max);
        this.value = variant;
        this.min = setDouble(min);
        this.max= setDouble(max);
    }

    private double setDouble(double d) {
        return new BigDecimal(d).round(new MathContext(2, RoundingMode.HALF_UP)).doubleValue();
    }

    public double min() {
        return min;
    }

    public double max() {
        return max;
    }

    public Variant value() {
        return value;
    }

}