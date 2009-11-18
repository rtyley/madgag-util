package com.madgag.interval;

public interface Interval<T extends Comparable<T>> {

    T get(BoundType boundType);

    boolean is(BeforeOrAfter beforeOrAfter, T point);

    boolean is(BeforeOrAfter beforeOrAfter, Interval<T> otherInterval);
    
    boolean contains(T point);

    boolean overlaps(Interval<T> other);
    
    IntervalClosure getClosure();
}
