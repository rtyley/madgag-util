package com.madgag.interval;

import static com.madgag.interval.Closure.CLOSED;
import static com.madgag.interval.Closure.OPEN;

import java.util.Comparator;

public abstract class AbstractInterval<T extends Comparable<T>> implements Interval<T> {

	public boolean isAfter(T point) {
		int comparision = getStart().compareTo(point);
		return comparision>0 || (comparision==0 && getClosure().isLeft(OPEN));
	}
	
	public boolean isBefore(T point) {
		int comparision = getEnd().compareTo(point);
		return comparision<0 || (comparision==0 && getClosure().isRight(OPEN));
	}
	
	@Override
	public boolean isAfter(Interval<T> other) {
		return other.isBefore(this);
	}

	@Override
	public boolean isBefore(Interval<T> other) {
		int comparision = getEnd().compareTo(other.getStart());
		return comparision<0 || (comparision==0 && (getClosure().isRight(OPEN) || other.getClosure().isLeft(OPEN)));
	}
	
	public boolean contains(T point) {
		return !isBefore(point) && !isAfter(point);
	}

    public boolean contains(Interval<T> other) {
    	int startComparision = getStart().compareTo(other.getStart());
    	if (startComparision>0) {
    		return false;
    	}
    	if (startComparision==0 && other.getClosure().isLeft(CLOSED) && getClosure().isLeft(OPEN)) {
    		return false;
    	}
    	int endComparision = getEnd().compareTo(other.getEnd());
    	if (endComparision<0) {
    		return false;
    	}
    	if (endComparision==0 && other.getClosure().isRight(CLOSED) && getClosure().isRight(OPEN)) {
    		return false;
    	}
    	return true;
    }

	@Override
	public boolean overlaps(Interval<T> other) {
		return !isBefore(other) && !other.isBefore(this);
	}
	
	public static class OverlapIsEqualityComparator<T extends Comparable<T>> implements Comparator<Interval<T>> {
		
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
			return o1.isBefore(o2)?-1:1;
		}
	}
}