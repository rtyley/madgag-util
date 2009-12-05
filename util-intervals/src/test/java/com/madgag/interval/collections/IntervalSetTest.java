package com.madgag.interval.collections;

import com.madgag.interval.Closure;
import com.madgag.interval.Interval;
import org.hamcrest.Matchers;
import org.junit.Test;

import com.madgag.interval.SimpleInterval;

import java.util.Arrays;

import static com.madgag.interval.Closure.CLOSED;
import static com.madgag.interval.Closure.OPEN;
import static com.madgag.interval.collections.IntervalSet.newIntervalSet;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class IntervalSetTest {
    @Test
    public void shouldBeAllNice() {
        IntervalSet<Integer> intervalSet = newIntervalSet();
        Interval<Integer> interval10to20 = CLOSED.interval(10, 20), interval50to60 = CLOSED.interval(50, 60);
        intervalSet.addAll(asList(interval10to20, interval50to60));

        assertThat(intervalSet.subSet(CLOSED.interval(10,20)), hasItems(interval10to20));
        assertThat(intervalSet.subSet(OPEN.interval(10,20)), hasItems(interval10to20));

        assertThat(intervalSet.subSet(CLOSED.interval(20,50)), hasItems(interval10to20,interval50to60));
        assertThat(intervalSet.subSet(OPEN.interval(20,50)).isEmpty(), is(true));
    }
}
