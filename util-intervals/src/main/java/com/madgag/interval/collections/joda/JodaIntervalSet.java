package com.madgag.interval.collections.joda;

import static com.madgag.interval.SimpleInterval.interval;

import java.util.Set;

import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.ReadableInstant;

import com.madgag.interval.SimpleInterval;
import com.madgag.interval.collections.IntervalSet;


@SuppressWarnings("unchecked")
public class JodaIntervalSet {

	private IntervalSet intervalSet = new IntervalSet();

    public boolean add(Interval interval) {
		intervalSet.add(simpleInterval(interval));
		return false;
	}
	
	private Interval jodaInterval(com.madgag.interval.Interval simpleInterval) {
		return new Interval((ReadableInstant) simpleInterval.getStart(),(ReadableInstant) simpleInterval.getEnd());
	}
	
	private SimpleInterval simpleInterval(Interval interval) {
		return interval(interval.getStart(), interval.getEnd());
	}


	public Duration totalSubSetDurationFor(Interval interval) {
		Set<com.madgag.interval.Interval> simpleIntervals = intervalSet.subSet(simpleInterval(interval));
		long total = 0;
		for (com.madgag.interval.Interval simpleInterval : simpleIntervals) {
			total += jodaInterval(simpleInterval).overlap(interval).toDurationMillis();
		}
		return new Duration(total);
	}

}
