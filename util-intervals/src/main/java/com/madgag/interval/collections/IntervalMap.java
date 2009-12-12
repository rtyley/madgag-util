package com.madgag.interval.collections;

import static com.madgag.interval.Closure.CLOSED;
import static com.madgag.interval.Closure.OPEN;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

import com.madgag.interval.*;

import static com.madgag.interval.Bound.*;
import static com.madgag.interval.SimpleInterval.instantInterval;
import static com.madgag.interval.SimpleInterval.union;

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
		return events.floorEntry(instantInterval(instant, CLOSED));
	}
	
	public Collection<EventType> getEventsDuring(Interval<InstantType> interval) {
		return internal_subMapFor(interval).values();
	}

	public Set<Interval<InstantType>> keysFor(Interval<InstantType> interval) {
        return events.navigableKeySet().subSet(
                instantIntervalFrom(interval,MIN), true,
                instantIntervalFrom(interval,MAX), true);
	}
	
	private NavigableMap<Interval<InstantType>, EventType> internal_subMapFor(Interval<InstantType> interval) {
        return events.subMap(
                instantIntervalFrom(interval,MIN), true,
                instantIntervalFrom(interval,MAX), true);
	}

    private Interval<InstantType> instantIntervalFrom(Interval<InstantType> interval, Bound bound) {
        return instantInterval(interval.get(bound), interval.getClosure().forBound(bound));
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

	public Collection<EventType> values() {
		return events.values();
	}

    public Interval<InstantType> getSpannedInterval() {
		return events.isEmpty()?null:union(events.firstKey(),events.lastKey());
	}


	@Override
	public String toString() {
		return events.toString();
	}
}
