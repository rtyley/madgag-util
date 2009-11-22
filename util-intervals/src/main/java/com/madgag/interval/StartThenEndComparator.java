package com.madgag.interval;

import java.util.Comparator;

import static com.madgag.interval.Bound.*;

public class StartThenEndComparator<T extends Comparable<T>> implements Comparator<Interval<T>> {

	@SuppressWarnings("unchecked")
	private static StartThenEndComparator INSTANCE = new StartThenEndComparator();
	
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> Comparator<Interval<T>> instance() {
        return (Comparator<Interval<T>>) INSTANCE;
    }
    
	@Override
	public int compare(Interval<T> o1, Interval<T> o2) {
		int startComparison=o1.get(MIN).compareTo(o2.get(MIN));
		if (startComparison!=0) {
			return startComparison;
		}
		int endComparison=o1.get(MAX).compareTo(o2.get(MAX));
		if (endComparison!=0) {
			return endComparison;
		}
		return 0;
	}

}
