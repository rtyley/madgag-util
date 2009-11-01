package com.madgag.interval;

public interface Interval<T extends Comparable<T>> {
    T getStart();

    T getEnd();

    boolean contains(T point);

    boolean isAfter(T point);

    boolean isBefore(T point);

    boolean isAfter(Interval<T> other);

    boolean isBefore(Interval<T> other);

    boolean overlaps(Interval<T> other);
    
    IntervalClosure getClosure();
}
