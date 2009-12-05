package com.madgag.interval;

import static com.madgag.interval.Bound.MAX;
import static com.madgag.interval.Bound.MIN;
import static com.madgag.interval.Closure.CLOSED;
import static com.madgag.interval.Closure.OPEN;
import static com.madgag.interval.IntervalClosure.CLOSED_CLOSED;
import static com.madgag.interval.IntervalClosure.OPEN_OPEN;



public class SimpleInterval<T extends Comparable<T>> extends AbstractInterval<T> {

	private final T startBound, endBound;
	private IntervalClosure intervalClosure;

	public SimpleInterval(T start, T end) {
		this(start,CLOSED, end, OPEN);
	}
	public SimpleInterval(T start, T end, IntervalClosure intervalClosure) {
		if (start.compareTo(end) > 0) {
			throw new IllegalArgumentException();
		}
		this.intervalClosure = intervalClosure;
		this.startBound =  start;
		this.endBound = end;
	}
	public SimpleInterval(T start, Closure startClosure, T end, Closure endClosure) {
		this(start, end, IntervalClosure.of(startClosure, endClosure));
	}
	
	public static <T extends Comparable<T>> SimpleInterval<T> interval(T start, T end) {
		return new SimpleInterval<T>(start,end);
	}

    public static <T extends Comparable<T>> SimpleInterval<T> interval(T start, T end, IntervalClosure intervalClosure) {
		return new SimpleInterval<T>(start, end, intervalClosure);
	}

	public static <T extends Comparable<T>> SimpleInterval<T> instantInterval(T instant, Closure closure) {
		return new SimpleInterval<T>(instant, closure, instant, closure);
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endBound == null) ? 0 : endBound.hashCode());
		result = prime * result
				+ ((startBound == null) ? 0 : startBound.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleInterval other = (SimpleInterval) obj;
		if (endBound == null) {
			if (other.endBound != null)
				return false;
		} else if (!endBound.equals(other.endBound))
			return false;
		if (startBound == null) {
			if (other.startBound != null)
				return false;
		} else if (!startBound.equals(other.startBound))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return startBound+" - "+ endBound;
	}
	
	public Interval<T> getInterior() {
		return this.withClosure(OPEN_OPEN);
	}

	public Interval<T> getRealClosure() {
		return this.withClosure(CLOSED_CLOSED);
	}
	
	protected Interval<T> withClosure(IntervalClosure ic) {
        return new SimpleInterval<T>(get(MIN), ic.getMin(), get(MAX), ic.getMax());
	}


    @Override
    public T get(Bound bound) {
        return bound ==MIN?startBound:endBound;
    }

    @Override
	public IntervalClosure getClosure() {
		return intervalClosure;
	}

}
