package goa.systems.commons.linux;

import java.util.ArrayList;
import java.util.List;

public class PartitionCalculator {

	public List<Partition> calculatePartitions(long disksize, long bootpartitionsize, long swapsize, DiskSizeUnit unit)
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
		}

		List<Partition> partitions = new ArrayList<>();

		Partition boot = new Partition();
		boot.setNumber(1);
		boot.setGrubDevice(true);
		boot.setFlag("boot");
		boot.setOffset(mbroffset);
		boot.setResize(true);
		boot.setSize(bootpartitionsize * factor);

		Partition swap = new Partition();
		swap.setNumber(3);
		swap.setFlag("swap");
		swap.setResize(true);
		swap.setSize(swapsize * factor);

		Partition root = new Partition();
		root.setNumber(2);
		root.setResize(true);
		root.setOffset(mbroffset + boot.getSize());
		root.setSize((disksize * factor) - boot.getSize() - swap.getSize() - (2 * mbroffset));

		swap.setOffset(mbroffset + boot.getSize() + root.getSize());

		partitions.add(boot);
		partitions.add(root);
		partitions.add(swap);

		return partitions;
	}
}
