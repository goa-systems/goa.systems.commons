package goa.systems.commons.linux;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class PartitionCalculatorTests {

	@Test
	void testPartitionList() {
		PartitionCalculator pc = new PartitionCalculator();
		assertDoesNotThrow(() -> {
			List<Partition> lp = pc.calculatePartitions(32768, 32000, 4096, DiskSizeUnit.MEGABYTE);
			assertEquals(3, lp.size());
		});
	}
}
