package com.madgag.interval.collections;

import static java.lang.Boolean.TRUE;

import java.util.Set;

import com.madgag.interval.Interval;
import com.madgag.interval.SimpleInterval;

public class IntervalSet<InstantType extends Comparable<InstantType>> {

	private final IntervalMap<InstantType, Boolean> map = new IntervalMap<InstantType, Boolean>();
	
	public void add(SimpleInterval<InstantType> interval) {
		map.put(interval, TRUE);
	}

	public Set<Interval<InstantType>> subSet(Interval<InstantType> simpleInterval) {
		return map.subMapForEventsDuring(simpleInterval).keySet();
	}

}
