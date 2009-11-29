package com.madgag.interval.collections;

import static com.madgag.interval.Closure.CLOSED;
import static com.madgag.interval.Closure.OPEN;

import java.util.Collection;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

import com.madgag.interval.Interval;
import com.madgag.interval.OverlapIsEqualityComparator;
import com.madgag.interval.SimpleInterval;
import static com.madgag.interval.Bound.*;

public class IntervalMap<InstantType extends Comparable<InstantType>, EventType> {

	private final NavigableMap<Interval<InstantType>, EventType> events;

    public static <I extends Comparable<I>, E> IntervalMap<I,E>  newIntervalMap() {
        return newIntervalMapBasedOn(new TreeMap<Interval<I>, E>(OverlapIsEqualityComparator.<I>instance()));
    }

    public static <I extends Comparable<I>, E> IntervalMap<I,E>  newConcurrentIntervalMap() {
        return newIntervalMapBasedOn(new ConcurrentSkipListMap<Interval<I>, E>(OverlapIsEqualityComparator.<I>instance()));
    }

    public static <I extends Comparable<I>, E> IntervalMap<I,E>  newIntervalMapBasedOn(NavigableMap<Interval<I>, E> emptyMap) {
        return new IntervalMap(emptyMap);
    }

    private IntervalMap(NavigableMap<Interval<InstantType>, EventType> events) {
        if (OverlapIsEqualityComparator.<InstantType>instance()!=events.comparator() || !events.isEmpty()) {
            throw new IllegalArgumentException("IntervalMaps need to be based on initially-empty NavigableMaps using the OverlapIsEqualityComparator");
        }
        this.events = events;
    }


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
		TreeMap<Interval<InstantType>, EventType> copyMap = new TreeMap<Interval<InstantType>, EventType>(OverlapIsEqualityComparator.<InstantType>instance());
		copyMap.putAll(internal_subMap(fromKey, toKey));
		return copyMap;
	}
	
	public NavigableMap<Interval<InstantType>, EventType> subMapForEventsDuring(Interval<InstantType> simpleInterval) {
		return subMapForEventsDuring(simpleInterval.get(MIN), simpleInterval.get(MAX));
	}
	
	private NavigableMap<Interval<InstantType>, EventType> internal_subMap(InstantType fromKey, InstantType toKey) {
		return events.subMap(SimpleInterval.instantInterval(fromKey, CLOSED), true, SimpleInterval.instantInterval(toKey, OPEN), true);
	}
	
	private NavigableMap<Interval<InstantType>, EventType> internal_subMapFor(Interval<InstantType> interval) {
		return internal_subMap(interval.get(MIN), interval.get(MAX));
	}

    public boolean isEmpty() {
        return events.isEmpty();
    }

	public void put(Interval<InstantType> interval, EventType event) {
		checkCanAddEventTo(interval);
		addWithoutChecking(interval, event);
	}

    public EventType remove(Interval<InstantType> interval) {
        return events.remove(interval);
    }
	
	public void overrideWith(Interval<InstantType> interval, EventType event) {
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
		return events.isEmpty()?null:new SimpleInterval<InstantType>(events.firstKey().get(MIN),events.lastKey().get(MAX));
	}

	public Collection<EventType> values() {
		return events.values();
	}


	@Override
	public String toString() {
		return events.toString();
	}
}
