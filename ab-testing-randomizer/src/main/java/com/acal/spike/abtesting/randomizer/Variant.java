package com.acal.spike.abtesting.randomizer;

public class Variant {

    private final String name;
    private final double throttle;

    private boolean first;

    public Variant(String name, double throttle) {
        this.name = name;
        this.throttle = throttle;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public String getName() {
        return name;
    }
    public boolean isBase() {
        return false;
    }

    public double getThrottle() {
        return throttle;
    }

    @Override
    public String toString() {
        return "Variant{" + "name='" + name + '\'' + (throttle == 1 ? "" : ", throttle=" + throttle) + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variant variant = (Variant) o;

        if (Double.compare(variant.throttle, throttle) != 0) return false;
        if (!name.equals(variant.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(throttle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
