package com.madgag.interval.collections;

import static com.madgag.interval.collections.IntervalMap.newIntervalMap;
import static java.lang.Boolean.TRUE;

import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

import com.madgag.interval.Interval;
import com.madgag.interval.OverlapIsEqualityComparator;
import com.madgag.interval.SimpleInterval;

public class IntervalSet<InstantType extends Comparable<InstantType>> {

    public static <I extends Comparable<I>> IntervalSet<I> newIntervalSet() {
        return new IntervalSet<I>(IntervalMap.<I, Boolean>newIntervalMap());
    }

	private final IntervalMap<InstantType, Boolean> map;

    private IntervalSet(IntervalMap<InstantType, Boolean> map) {
        this.map = map;
    }

	public void add(SimpleInterval<InstantType> interval) {
		map.put(interval, TRUE);
	}

	public Set<Interval<InstantType>> subSet(Interval<InstantType> simpleInterval) {
		return map.subMapForEventsDuring(simpleInterval).keySet();
	}

}
