package com.acal.spike.abtesting.randomizer;

public class BaseVariant extends Variant {

    public BaseVariant(double throttle) {
        super("Base", throttle);
    }

    @Override
    public boolean isBase() {
        return true;
    }
}
