package goa.systems.commons.linux;

import java.util.ArrayList;
import java.util.List;

public class PartitionCalculator {

	public List<Partition> calculatePartitions(int disksize, int bootpartitionsize, int swapsize, DiskSizeUnit unit) {

		int factor = 0;
		int mbroffset = 1024 * 1024;

		if (unit == DiskSizeUnit.BYTE) {
			/* Don't change values as they are already correct. */
			factor = 1;
		} else if (unit == DiskSizeUnit.KILOBYTE) {
			factor = 1024;
		} else if (unit == DiskSizeUnit.MEGABYTE) {
			factor = 1024 * 1024;
		} else if (unit == DiskSizeUnit.MEGABYTE) {
			factor = 1024 * 1024 * 1024;
		}

		List<Partition> partitions = new ArrayList<>();

		return partitions;
	}
}
