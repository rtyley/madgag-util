package com.madgag.interval;

public enum Closure {
	OPEN(false), CLOSED(true);
	
	
	private final boolean satisfiedByEquality;

	Closure(boolean satisfiedByEquality) {
		this.satisfiedByEquality = satisfiedByEquality;
	}
	
	public boolean isSatisfiedByEquality() {
		return satisfiedByEquality;
	}

    public <T extends Comparable<T>> Interval<T> interval(T start, T end) {
        return new SimpleInterval<T>(start, this, end, this);
    }
}
