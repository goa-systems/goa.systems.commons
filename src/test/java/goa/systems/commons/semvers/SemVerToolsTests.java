package goa.systems.commons.semvers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class SemVerToolsTests {

	@Test
	void test1() {

		SemVerTools semvertools = new SemVerTools();
		List<Integer[]> versions = new ArrayList<>();

		versions.add(new Integer[] { 0, 0, 1 });
		versions.add(new Integer[] { 0, 0, 2 });
		versions.add(new Integer[] { 0, 0, 3 });
		versions.add(new Integer[] { 0, 1, 0 });
		versions.add(new Integer[] { 0, 1, 1 });
		versions.add(new Integer[] { 0, 2, 0 });
		versions.add(new Integer[] { 1, 0, 0 });

		assertDoesNotThrow(() -> {
			assertEquals(versions.get(versions.size() - 1), semvertools.getLatestVersion(versions));
			assertEquals("1.0.0", semvertools.getLatestVersionAsString(versions));
		});
	}

	@Test
	void test2() {

		SemVerTools semvertools = new SemVerTools();
		List<Integer[]> versions = new ArrayList<>();

		versions.add(new Integer[] { 24, 1, 1 });
		versions.add(new Integer[] { 23, 2, 7 });

		assertDoesNotThrow(() -> {
			assertEquals(versions.get(0), semvertools.getLatestVersion(versions));
			assertEquals("24.1.1", semvertools.getLatestVersionAsString(versions));
		});
	}
}
