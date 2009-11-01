package com.madgag.interval.collections;

import org.junit.Test;

import com.madgag.interval.SimpleInterval;

/**
 * Created by IntelliJ IDEA.
 * User: roberto
 * Date: 20-Sep-2009
 * Time: 21:23:40
 * To change this template use File | Settings | File Templates.
 */
public class IntervalMapTest {
    @Test
    public void shouldBeAllNice() {
        IntervalSet<Integer> intervalSet = new IntervalSet<Integer>();
        intervalSet.add(new SimpleInterval<Integer>(123, 345));
    }
}
