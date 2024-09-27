package goa.systems.commons.time;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

class TimeCalcTests {

	@Test
	void testvalidCases() {

		TimeCalc tc = new TimeCalc();

		TimeUnit start = new TimeUnit(0, 0, 0);
		TimeUnit end = new TimeUnit(0, 0, 50);

		assertDoesNotThrow(() -> {
			assertEquals(10, tc.calcTimeDifference(0, 10));
			assertEquals(10, tc.calcTimeDifference(100, 110));

			assertEquals(50, tc.calcTimeDifference(start, end));

		});

		assertDoesNotThrow(() -> {
			var st = new GregorianCalendar();
			var et = new GregorianCalendar();
			et.setTimeInMillis(st.getTimeInMillis() + 600);
			tc.calcTimeDifference(st.getTimeInMillis(), et.getTimeInMillis());
			var sod = tc.getStartOfWorkDay(st);
			assertEquals(8, sod.get(Calendar.HOUR_OF_DAY));
			assertEquals(0, sod.get(Calendar.MINUTE));
			assertEquals(0, sod.get(Calendar.SECOND));
		});
	}

	@Test
	void testInvalidCases() {

		TimeCalc tc = new TimeCalc();

		TimeUnit start = new TimeUnit(0, 0, 2);
		TimeUnit end = new TimeUnit(0, 0, 1);

		assertThrows(TimeCalculationException.class, () -> {
			tc.calcTimeDifference(10, 5);
		});

		assertThrows(TimeCalculationException.class, () -> {
			var st = new GregorianCalendar();
			var et = new GregorianCalendar();
			et.setTimeInMillis(st.getTimeInMillis() - 600);
			tc.calcTimeDifference(st.getTimeInMillis(), et.getTimeInMillis());
		});

		assertThrows(TimeCalculationException.class, () -> {
			tc.calcTimeDifference(start, end);
		});

		assertThrows(TimeCalculationException.class, () -> {
			var sd = new GregorianCalendar();
			sd.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			assertEquals(Calendar.SUNDAY, sd.get(Calendar.DAY_OF_WEEK));
			tc.getStartOfWorkDay(sd);
		});
	}
}
