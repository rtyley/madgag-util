package com.madgag.interval;

import static com.madgag.interval.BoundTypeWithClosure.MAX_CLOSED;
import static com.madgag.interval.BoundTypeWithClosure.MAX_OPEN;
import static com.madgag.interval.BoundTypeWithClosure.MIN_CLOSED;
import static com.madgag.interval.BoundTypeWithClosure.MIN_OPEN;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;


public class BoundTypeWithClosureTest {

	@Test
	public void shouldBeGoodDammit() {
		assertThat(MIN_OPEN.isOnside(0), equalTo(false));
		assertThat(MAX_OPEN.isOnside(0), equalTo(false));
		assertThat(MIN_CLOSED.isOnside(0), equalTo(true));
		assertThat(MAX_CLOSED.isOnside(0), equalTo(true));

		assertThat(MIN_OPEN.isOnside(1), equalTo(false));
		assertThat(MAX_OPEN.isOnside(1), equalTo(true));
		assertThat(MIN_OPEN.isOnside(-1), equalTo(true));
		assertThat(MAX_OPEN.isOnside(-1), equalTo(false));

		assertThat(MIN_CLOSED.isOnside(1), equalTo(false));
		assertThat(MAX_CLOSED.isOnside(1), equalTo(true));
		assertThat(MIN_CLOSED.isOnside(-1), equalTo(true));
		assertThat(MAX_CLOSED.isOnside(-1), equalTo(false));
	}
}
