package com.madgag.interval.collections;

import org.junit.Test;

import com.madgag.interval.SimpleInterval;

import static com.madgag.interval.collections.IntervalSet.newIntervalSet;

public class IntervalSetTest {
    @Test
    public void shouldBeAllNice() {
        IntervalSet<Integer> intervalSet = newIntervalSet();
        intervalSet.add(new SimpleInterval<Integer>(123, 345));
    }
}
