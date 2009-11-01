package com.madgag.interval;

import static com.madgag.interval.Closure.CLOSED;
import static com.madgag.interval.Closure.OPEN;

public enum IntervalClosure {
	OPEN_OPEN(OPEN, OPEN),
	OPEN_CLOSED(OPEN,CLOSED),
	CLOSED_OPEN(CLOSED,OPEN),
	CLOSED_CLOSED(CLOSED,CLOSED);
	
	private final Closure min, max;

	private IntervalClosure(Closure min, Closure max) {
		this.min = min;
		this.max = max;
	}

	public Closure getMax() {
		return max;
	}
	
	public Closure getMin() {
		return min;
	}

	public static IntervalClosure of(Closure startClosure, Closure endClosure) {
		return values()[(startClosure.ordinal()<<1) + endClosure.ordinal()];
	}

	public boolean isRight(Closure closure) {
		return max==closure;
	}

	public boolean isLeft(Closure closure) {
		return min==closure;
	}
	
}
