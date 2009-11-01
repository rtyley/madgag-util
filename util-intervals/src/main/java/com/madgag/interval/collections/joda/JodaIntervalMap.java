package com.madgag.interval.collections.joda;

import static com.madgag.interval.SimpleInterval.interval;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;

import org.joda.time.Interval;
import org.joda.time.ReadableInstant;

import com.madgag.interval.collections.IntervalMap;


@SuppressWarnings("unchecked")
public class JodaIntervalMap<V> {

	private IntervalMap intervalMap = new IntervalMap();

    public void put(Interval interval, V value) {
		intervalMap.put(simpleInterval(interval), value);
	}

	public Map<Interval, V> subMapForEventsDuring(Interval interval) {
		NavigableMap<com.madgag.interval.Interval, V> subMap = intervalMap.subMapForEventsDuring(simpleInterval(interval));
		Map<Interval, V> jodaMap = new HashMap<Interval, V>();
		for (Map.Entry<com.madgag.interval.Interval, V> entry : subMap.entrySet()) {
			com.madgag.interval.Interval simpleInterval = entry.getKey();
			jodaMap.put(jodaInterval(simpleInterval), entry.getValue());
		}
		return jodaMap;
	}

	private Interval jodaInterval(com.madgag.interval.Interval simpleInterval) {
		return new Interval((ReadableInstant) simpleInterval.getStart(),(ReadableInstant) simpleInterval.getEnd());
	}
	
	private com.madgag.interval.Interval simpleInterval(Interval interval) {
		return interval(interval.getStart(), interval.getEnd());
	}
	
	public Interval getInterval() {
		return jodaInterval(intervalMap.getInterval());
	}
	
	@Override
	public String toString() {
		return intervalMap.toString();
	}

}
