package goa.systems.commons.linux;

public class Partition {

	private String device;
	private long size;
	private String wipe;
	private String flag;
	private int number;
	private boolean preserve;
	private boolean grubDevice;
	private long offset;
	private boolean resize;
	private String type;
	private String id;

	public Partition() {
		this.device = "disk-sda";
		this.wipe = "superblock";
		this.preserve = false;
		this.type = "partition";
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getWipe() {
		return wipe;
	}

	public void setWipe(String wipe) {
		this.wipe = wipe;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
		this.id = String.format("partition-%d", this.number - 1);
	}

	public boolean isPreserve() {
		return preserve;
	}

	public void setPreserve(boolean preserve) {
		this.preserve = preserve;
	}

	public boolean isGrubDevice() {
		return grubDevice;
	}

	public void setGrubDevice(boolean grubDevice) {
		this.grubDevice = grubDevice;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public boolean isResize() {
		return resize;
	}

	public void setResize(boolean resize) {
		this.resize = resize;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
