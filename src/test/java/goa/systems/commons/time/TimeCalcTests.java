package goa.systems.commons.time;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;

class TimeCalcTests {

	@Test
	void testvalidCases() {

		TimeCalc tc = new TimeCalc();

		assertDoesNotThrow(() -> {
			assertEquals(10, tc.calcTimeDifference(0, 10));
			assertEquals(10, tc.calcTimeDifference(100, 110));
		});

		assertDoesNotThrow(() -> {
			var st = new GregorianCalendar();
			var et = new GregorianCalendar();
			et.setTimeInMillis(st.getTimeInMillis() + 600);
			tc.calcTimeDifference(st.getTimeInMillis(), et.getTimeInMillis());
		});
	}

	@Test
	void testInvalidCases() {

		TimeCalc tc = new TimeCalc();

		assertThrows(TimeCalculationException.class, () -> {
			tc.calcTimeDifference(10, 5);
		});

		assertThrows(TimeCalculationException.class, () -> {
			var st = new GregorianCalendar();
			var et = new GregorianCalendar();
			et.setTimeInMillis(st.getTimeInMillis() - 600);
			tc.calcTimeDifference(st.getTimeInMillis(), et.getTimeInMillis());
		});
	}
}
