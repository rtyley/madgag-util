package com.madgag.interval;

import java.util.Comparator;

import static com.madgag.interval.BeforeOrAfter.BEFORE;

public class OverlapIsEqualityComparator<T extends Comparable<T>> implements Comparator<Interval<T>> {

    @SuppressWarnings("unchecked")
    private static OverlapIsEqualityComparator INSTANCE = new OverlapIsEqualityComparator();

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> Comparator<Interval<T>> instance() {
        return (Comparator<Interval<T>>) INSTANCE;
    }

    @Override
    public int compare(Interval<T> o1, Interval<T> o2) {
        if (o1.overlaps(o2)) {
            return 0;
        }
        return o1.is(BEFORE, o2)?-1:1;
    }
}
