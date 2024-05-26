package goa.systems.commons.linux;

import java.util.ArrayList;
import java.util.List;

public class PartitionCalculator {

	public List<Partition> calculatePartitions(int disksize, int bootpartitionsize, int swapsize, DiskSizeUnit unit)
			throws DiskInvalidException {

		if (bootpartitionsize + swapsize > disksize) {
			throw new DiskInvalidException("Size of partitions is larger than disksize.");
		}

		long factor = 0;
		long mbroffset = 1048576;

		if (unit == DiskSizeUnit.BYTE) {
			/* Don't change values as they are already correct. */
			factor = 1;
		} else if (unit == DiskSizeUnit.KILOBYTE) {
			factor = 1024;
		} else if (unit == DiskSizeUnit.MEGABYTE) {
			factor = 1048576;
		} else if (unit == DiskSizeUnit.GIGABYTE) {
			factor = 1073741824;
		}

		List<Partition> partitions = new ArrayList<>();

		Partition boot = new Partition();
		boot.setOffset(mbroffset);
		boot.setSize(bootpartitionsize * factor);

		Partition swap = new Partition();
		swap.setSize(swapsize * factor);

		Partition root = new Partition();
		root.setOffset(mbroffset + boot.getSize());
		root.setSize((disksize * factor) - boot.getSize() - swap.getSize() - (2 * mbroffset));

		swap.setOffset(mbroffset + boot.getSize() + root.getSize());

		partitions.add(boot);
		partitions.add(root);
		partitions.add(swap);

		return partitions;
	}
}
