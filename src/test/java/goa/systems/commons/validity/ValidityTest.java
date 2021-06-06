package goa.systems.commons.validity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Validity tests
 * 
 * @author ago
 * @since 2021-06-06
 *
 */
class ValidityTest {

	@Test
	void test1() {
		assertTrue(Validity.isNullOrEmpty(null));
		assertTrue(Validity.isNullOrEmpty(""));
		assertFalse(Validity.isNullOrEmpty("test"));
	}

	@Test
	void test2() {
		assertTrue(Validity.isOnlyOneSet(null, "test"));
		assertTrue(Validity.isOnlyOneSet("test", null));
		assertTrue(Validity.isOnlyOneSet("", "test"));
		assertTrue(Validity.isOnlyOneSet("test", ""));
		assertFalse(Validity.isOnlyOneSet("test", "test"));
		assertFalse(Validity.isOnlyOneSet("test", "test"));
	}
}
