package com.madgag.interval.collections;

import com.madgag.interval.Closure;
import com.madgag.interval.Interval;
import com.madgag.interval.IntervalClosure;
import com.madgag.interval.SimpleInterval;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.joda.time.Instant;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static com.madgag.interval.Closure.CLOSED;
import static com.madgag.interval.Closure.OPEN;
import static com.madgag.interval.IntervalClosure.CLOSED_OPEN;
import static com.madgag.interval.SimpleInterval.interval;
import static com.madgag.interval.collections.IntervalMap.newIntervalMap;
import static com.madgag.interval.collections.IntervalSet.newIntervalSet;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.joda.time.Duration.standardSeconds;
import static org.junit.Assert.assertThat;

public class IntervalMapTest {

    @Test
	public void shouldGetLatestSignificantIntervalStartingAtOrBefore() {
        IntervalMap<Integer,String> intervalMap = newIntervalMap();
        intervalMap.put(interval(2,5,CLOSED_OPEN),"A");

		assertThat(intervalMap.getLatestEventStartingAtOrBefore(1), nullValue());
		assertThat(intervalMap.getLatestEventStartingAtOrBefore(2), equalTo("A"));
		assertThat(intervalMap.getLatestEventStartingAtOrBefore(3), equalTo("A"));
		assertThat(intervalMap.getLatestEventStartingAtOrBefore(6), equalTo("A"));
	}


    @Test
    public void shouldGetEventsDuringInterval() throws Exception {
        IntervalMap<Integer,String> intervalMap = newIntervalMap();
        intervalMap.put(interval(10, 20, CLOSED_OPEN),"A");
        intervalMap.put(interval(50, 60, CLOSED_OPEN),"B");
        assertThat(intervalMap.getEventsDuring(interval(10, 60, CLOSED_OPEN)),hasItems("A","B"));
    }

    @Test
    public void shouldOnlyReportBoundingClosedIntervalsIfRequestedIntervalIsClosedToo() {
        IntervalMap<Integer,String> mapWithClosedIntervals = newIntervalMap();
        mapWithClosedIntervals.put(CLOSED.interval(10, 20),"A");
        mapWithClosedIntervals.put(CLOSED.interval(50, 60),"B");
        assertThat(mapWithClosedIntervals.getEventsDuring(OPEN.interval(20, 50)).isEmpty(),is(true));
        assertThat(mapWithClosedIntervals.getEventsDuring(CLOSED.interval(20, 50)),hasItems("A","B"));
    }

    @Test
    public void shouldNotReportBoundingOpenIntervals() {
        IntervalMap<Integer,String> mapWithOpenIntervals = newIntervalMap();
        mapWithOpenIntervals.put(OPEN.interval(10, 20),"A");
        mapWithOpenIntervals.put(OPEN.interval(50, 60),"B");
        assertThat(mapWithOpenIntervals.getEventsDuring(OPEN.interval(20, 50)).isEmpty(),is(true));
        assertThat(mapWithOpenIntervals.getEventsDuring(CLOSED.interval(20, 50)).isEmpty(),is(true));
    }


    @Test
    public void shouldGetEventAtRequestedPoint() {
        IntervalMap<Integer,String> map = newIntervalMap();
        map.put(OPEN.interval(10, 20),"A");
        map.put(CLOSED.interval(50, 60),"B");
        
        assertThat(map.getEventAt(10),nullValue());
        assertThat(map.getEventAt(11),equalTo("A"));
        assertThat(map.getEventAt(19),equalTo("A"));
        assertThat(map.getEventAt(20),nullValue());
        assertThat(map.getEventAt(30),nullValue());
        assertThat(map.getEventAt(50),equalTo("B"));
        assertThat(map.getEventAt(51),equalTo("B"));
        assertThat(map.getEventAt(59),equalTo("B"));
        assertThat(map.getEventAt(60),equalTo("B"));
    }
}
