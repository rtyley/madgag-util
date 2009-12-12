package com.madgag.interval;

public interface Interval<T extends Comparable<T>> {

    T get(Bound bound);

    boolean is(BeforeOrAfter beforeOrAfter, T point);

    boolean is(BeforeOrAfter beforeOrAfter, Interval<T> otherInterval);

    boolean contains(T point);

    boolean contains(Interval<T> otherInterval);

    boolean overlaps(Interval<T> other);
    
    IntervalClosure getClosure();
}
