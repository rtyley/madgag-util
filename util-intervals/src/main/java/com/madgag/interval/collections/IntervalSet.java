package com.madgag.interval.collections;

import static com.madgag.interval.collections.IntervalMap.newIntervalMap;
import static java.lang.Boolean.TRUE;

import java.util.*;
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

    public void addAll(Collection<? extends Interval<InstantType>> intervals) {
        for (Interval<InstantType> interval : intervals) {
            add(interval);
        }
    }

	public void add(Interval<InstantType> interval) {
		map.put(interval, TRUE);
	}

	public void overrideWith(Interval<InstantType> interval) {
		map.overrideWith(interval, TRUE);
	}

	public Set<Interval<InstantType>> subSet(Interval<InstantType> interval) {
		return map.keysFor(interval);
	}

    public Interval<InstantType> getLatestEventStartingAtOrBefore(InstantType instant) {
		Map.Entry<Interval<InstantType>, Boolean> entry = map.entryForEventStartingAtOrBefore(instant);
		return entry==null?null:entry.getKey();
	}
}
