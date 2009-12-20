package com.madgag.interval;

import static com.madgag.interval.BeforeOrAfter.AFTER;
import static com.madgag.interval.BeforeOrAfter.BEFORE;
import static com.madgag.interval.Bound.*;
import static com.madgag.interval.Closure.CLOSED;
import static com.madgag.interval.Closure.OPEN;

public abstract class AbstractInterval<T extends Comparable<T>> implements Interval<T> {

    public boolean is(BeforeOrAfter beforeOrAfter, T point) {
        Bound boundBeyondWhichExternalPointsAre=getBoundBeyondWhichExternalPointsAre(beforeOrAfter);
        T bound = boundBeyondWhichExternalPointsAre(beforeOrAfter);
        int comparison = bound.compareTo(point);
        return beforeOrAfter.isTrueFor(comparison) || (comparison==0 && getClosure().is(boundBeyondWhichExternalPointsAre, OPEN));
    }

    public boolean is(BeforeOrAfter beforeOrAfter, Interval<T> otherInterval) {
        if (beforeOrAfter == AFTER) {
            return otherInterval.is(BEFORE,this);
        }
        int comparison = get(MAX).compareTo(otherInterval.get(MIN));
        return comparison<0 || (comparison==0 && (check(this,otherInterval)));
    }

    private boolean check(Interval<T> intervalBefore, Interval<T> intervalAfter) {
        return intervalBefore.getClosure().isRight(OPEN) || intervalAfter.getClosure().isLeft(OPEN);
    }

    private T boundBeyondWhichExternalPointsAre(BeforeOrAfter beforeOrAfter) {
        return get(getBoundBeyondWhichExternalPointsAre(beforeOrAfter));
    }

    private Bound getBoundBeyondWhichExternalPointsAre(BeforeOrAfter beforeOrAfter) {
        return beforeOrAfter==BEFORE?MAX:MIN;
    }

    @Override
    public boolean contains(T point) {
        return !is(BEFORE, point) && !is(AFTER, point);
	}

    @Override
    public boolean contains(Interval<T> other) {
    	int startComparison = get(MIN).compareTo(other.get(MIN));
    	if (startComparison>0) {
    		return false;
    	}
    	if (startComparison==0 && getClosure().isLeft(OPEN) && other.getClosure().isLeft(CLOSED)) {
    		return false;
    	}
    	int endComparison = get(MAX).compareTo(other.get(MAX));
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
        return !is(BEFORE, other) && !other.is(BEFORE, this);
	}

}