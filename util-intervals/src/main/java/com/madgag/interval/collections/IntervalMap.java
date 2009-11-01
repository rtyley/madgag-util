package com.madgag.interval.collections;

import static com.madgag.interval.Closure.CLOSED;
import static com.madgag.interval.Closure.OPEN;

import java.util.Collection;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.madgag.interval.AbstractInterval;
import com.madgag.interval.Interval;
import com.madgag.interval.SimpleInterval;

public class IntervalMap<InstantType extends Comparable<InstantType>, EventType> {

	private NavigableMap<Interval<InstantType>, EventType> events = new TreeMap<Interval<InstantType>, EventType>(AbstractInterval.OverlapIsEqualityComparator.<InstantType>instance());

	public EventType getEventAt(InstantType instant) {
		Map.Entry<Interval<InstantType>, EventType> floorEntry = entryForEventStartingAtOrBefore(instant);
		if (floorEntry==null) {
			return null;
		}
		return floorEntry.getKey().contains(instant) ? floorEntry.getValue() : null;
	}
	
	public EventType getLatestEventStartingAtOrBefore(InstantType instant) {
		Map.Entry<Interval<InstantType>, EventType> entry = entryForEventStartingAtOrBefore(instant);
		return entry==null?null:entry.getValue();
	}

	private Map.Entry<Interval<InstantType>, EventType> entryForEventStartingAtOrBefore(InstantType instant) {
		return events.floorEntry(SimpleInterval.instantInterval(instant, CLOSED));
	}
	
	public Collection<EventType> getEventsDuring(InstantType start, InstantType end) {
		return getEventsDuring(new SimpleInterval<InstantType>(start, end));
	}
	
	public Collection<EventType> getEventsDuring(Interval<InstantType> interval) {
		return internal_subMapFor(interval).values();
	}
	
	public NavigableMap<Interval<InstantType>, EventType> subMapForEventsDuring(InstantType fromKey, InstantType toKey) {
		TreeMap<Interval<InstantType>, EventType> copyMap = new TreeMap<Interval<InstantType>, EventType>(AbstractInterval.OverlapIsEqualityComparator.<InstantType>instance());
		copyMap.putAll(internal_subMap(fromKey, toKey));
		return copyMap;
	}
	
	public NavigableMap<Interval<InstantType>, EventType> subMapForEventsDuring(Interval<InstantType> simpleInterval) {
		return subMapForEventsDuring(simpleInterval.getStart(), simpleInterval.getEnd());
	}
	
	private NavigableMap<Interval<InstantType>, EventType> internal_subMap(InstantType fromKey, InstantType toKey) {
		return events.subMap(SimpleInterval.instantInterval(fromKey, CLOSED), true, SimpleInterval.instantInterval(toKey, OPEN), true);
	}
	
	private NavigableMap<Interval<InstantType>, EventType> internal_subMapFor(Interval<InstantType> interval) {
		return internal_subMap(interval.getStart(), interval.getEnd());
	}

	public void put(Interval<InstantType> interval, EventType event) {
		checkCanAddEventTo(interval);
		addWithoutChecking(interval, event);
	}
	
	public void overrideWith(SimpleInterval<InstantType> interval, EventType event) {
		internal_subMapFor(interval).clear();
		addWithoutChecking(interval, event);
	}

	private void checkCanAddEventTo(Interval<InstantType> interval) {
		if (events.containsKey(interval)) {
			throw new IllegalArgumentException();
		}
	}

	private void addWithoutChecking(Interval<InstantType> interval, EventType event) {
		events.put(interval, event);
	}
	
	public Interval<InstantType> getInterval() {
		return events.isEmpty()?null:new SimpleInterval<InstantType>(events.firstKey().getStart(),events.lastKey().getEnd());
	}

	public Collection<EventType> values() {
		return events.values();
	}


	@Override
	public String toString() {
		return events.toString();
	}
}
