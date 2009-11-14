package com.madgag.interval;

import static com.madgag.interval.Closure.CLOSED;
import static com.madgag.interval.Closure.OPEN;

import java.util.Comparator;

public abstract class AbstractInterval<T extends Comparable<T>> implements Interval<T> {

	public boolean isAfter(T point) {
		int comparison = getStart().compareTo(point);
		return comparison>0 || (comparison==0 && getClosure().isLeft(OPEN));
	}
	
	public boolean isBefore(T point) {
		int comparison = getEnd().compareTo(point);
		return comparison<0 || (comparison==0 && getClosure().isRight(OPEN));
	}
	
	@Override
	public boolean isAfter(Interval<T> other) {
		return other.isBefore(this);
	}

	@Override
	public boolean isBefore(Interval<T> other) {
		int comparison = getEnd().compareTo(other.getStart());
		return comparison<0 || (comparison==0 && (getClosure().isRight(OPEN) || other.getClosure().isLeft(OPEN)));
	}
	
	public boolean contains(T point) {
		return !isBefore(point) && !isAfter(point);
	}

    public boolean contains(Interval<T> other) {
    	int startComparison = getStart().compareTo(other.getStart());
    	if (startComparison>0) {
    		return false;
    	}
    	if (startComparison==0 && getClosure().isLeft(OPEN) && other.getClosure().isLeft(CLOSED)) {
    		return false;
    	}
    	int endComparison = getEnd().compareTo(other.getEnd());
    	if (endComparison<0) {
    		return false;
    	}
    	if (endComparison==0 && getClosure().isRight(OPEN) && other.getClosure().isRight(CLOSED)) {
    		return false;
    	}
    	return true;
    }

	@Override
	public boolean overlaps(Interval<T> other) {
		return !isBefore(other) && !other.isBefore(this);
	}

}