package com.madgag.interval;

import java.util.Comparator;

public class StartThenEndComparator<T extends Comparable<T>> implements Comparator<Interval<T>> {

	@SuppressWarnings("unchecked")
	private static StartThenEndComparator INSTANCE = new StartThenEndComparator();
	
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> Comparator<Interval<T>> instance() {
        return (Comparator<Interval<T>>) INSTANCE;
    }
    
	@Override
	public int compare(Interval<T> o1, Interval<T> o2) {
		int startComparision=o1.getStart().compareTo(o2.getStart());
		if (startComparision!=0) {
			return startComparision;
		}
		int endComparision=o1.getEnd().compareTo(o2.getEnd());
		if (endComparision!=0) {
			return endComparision;
		}
		return 0;
	}

}
