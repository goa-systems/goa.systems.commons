package goa.systems.commons.linux;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class PartitionCalculatorTests {

	@Test
	void testMegaByte() {
		PartitionCalculator pc = new PartitionCalculator();
		assertDoesNotThrow(() -> {

			List<Partition> lp = pc.calculatePartitions(32768, 512, 4096, DiskSizeUnit.MEGABYTE);
			assertEquals(3, lp.size());

			assertEquals(1048576, lp.get(0).getOffset());
			assertEquals(536870912, lp.get(0).getSize());

			assertEquals(537919488, lp.get(1).getOffset());
			assertEquals(29525803008L, lp.get(1).getSize());

			assertEquals(30063722496L, lp.get(2).getOffset());
			assertEquals(4294967296L, lp.get(2).getSize());

		});
	}

	@Test
	void testKiloByte() {
		PartitionCalculator pc = new PartitionCalculator();
		assertDoesNotThrow(() -> {

			List<Partition> lp = pc.calculatePartitions(33554432, 524288, 4194304, DiskSizeUnit.KILOBYTE);
			assertEquals(3, lp.size());

			assertEquals(1048576, lp.get(0).getOffset());
			assertEquals(536870912, lp.get(0).getSize());

			assertEquals(537919488, lp.get(1).getOffset());
			assertEquals(29525803008L, lp.get(1).getSize());

			assertEquals(30063722496L, lp.get(2).getOffset());
			assertEquals(4294967296L, lp.get(2).getSize());

		});
	}

	@Test
	void testByte() {

		PartitionCalculator pc = new PartitionCalculator();
		assertDoesNotThrow(() -> {

			List<Partition> lp = pc.calculatePartitions(34359738368L, 536870912L, 4294967296L, DiskSizeUnit.BYTE);
			assertEquals(3, lp.size());

			assertEquals(1048576, lp.get(0).getOffset());
			assertEquals(536870912, lp.get(0).getSize());

			assertEquals(537919488, lp.get(1).getOffset());
			assertEquals(29525803008L, lp.get(1).getSize());

			assertEquals(30063722496L, lp.get(2).getOffset());
			assertEquals(4294967296L, lp.get(2).getSize());

		});
	}

	@Test
	void testException() {

		PartitionCalculator pc = new PartitionCalculator();

		assertThrows(DiskInvalidException.class, () -> {
			pc.calculatePartitions(32768, 32768, 4096, DiskSizeUnit.MEGABYTE);
		});

		assertThrows(DiskInvalidException.class, () -> {
			pc.calculatePartitions(32768, 512, 32768, DiskSizeUnit.MEGABYTE);
		});
	}
}
