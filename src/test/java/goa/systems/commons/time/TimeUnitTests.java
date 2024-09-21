package goa.systems.commons.time;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TimeUnitTests {

	@Test
	void testvalidCases() {

		TimeUnit tu = new TimeUnit(0, 11, 47);
		assertEquals("00:11:47", tu.toString());
		assertDoesNotThrow(() -> {
			TimeUnit pu = new TimeUnit();
			pu.parse(tu.toString());
			assertEquals(0, pu.getHours());
			assertEquals(11, pu.getMinutes());
			assertEquals(47, pu.getSeconds());
			pu.fromSeconds(3600);
			assertEquals(1, pu.getHours());
			assertEquals(0, pu.getMinutes());
			assertEquals(0, pu.getSeconds());
			assertEquals("01:00:00", pu.toString());

		});
	}

	@Test
	void testInvalidCases() {

		TimeUnit tu = new TimeUnit(0, 12, 48);
		assertNotEquals("00:11:47", tu.toString());
		assertThrows(TimeCalculationException.class, () -> {
			TimeUnit pu = new TimeUnit();
			pu.parse(null);
		});
		assertThrows(TimeCalculationException.class, () -> {
			TimeUnit pu = new TimeUnit();
			pu.parse(tu.toString() + ".0123");
		});
		assertThrows(TimeCalculationException.class, () -> {
			TimeUnit pu = new TimeUnit();
			pu.parse("00:01");
		});
	}
}
