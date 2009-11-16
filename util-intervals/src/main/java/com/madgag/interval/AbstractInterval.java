package com.madgag.interval;

import static com.madgag.interval.BeforeOrAfter.AFTER;
import static com.madgag.interval.BeforeOrAfter.BEFORE;
import static com.madgag.interval.Closure.CLOSED;
import static com.madgag.interval.Closure.OPEN;

public abstract class AbstractInterval<T extends Comparable<T>> implements Interval<T> {

    public boolean is(BeforeOrAfter beforeOrAfter, T point) {
        T bound = boundBeyondWhichPointsAre(beforeOrAfter);
        int comparison = bound.compareTo(point);
        return beforeOrAfter.isTrueFor(comparison) || (comparison==0 && getClosure().get(beforeOrAfter) ==OPEN);
    }

    public boolean is(BeforeOrAfter beforeOrAfter, Interval<T> otherInterval) {
        boolean before = beforeOrAfter == BEFORE;
        int comparison = boundBeyondWhichPointsAre(beforeOrAfter).compareTo(before?otherInterval.getStart():otherInterval.getEnd());
        return beforeOrAfter.isTrueFor(comparison) || (comparison==0 && (before?check(this,otherInterval):check(otherInterval,this)));
    }

    private boolean check(Interval<T> intervalBefore, Interval<T> intervalAfter) {
        return intervalBefore.getClosure().isRight(OPEN) || intervalAfter.getClosure().isLeft(OPEN);
    }

    private T boundBeyondWhichPointsAre(BeforeOrAfter beforeOrAfter) {
        return beforeOrAfter==BEFORE?getEnd():getStart();
    }

    public boolean contains(T point) {
        return !is(BEFORE, point) && !is(AFTER, point);
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
        return !is(BEFORE, other) && !other.is(BEFORE, this);
	}

}