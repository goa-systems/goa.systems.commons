package goa.systems.commons.linux;

public class Partition {

	private String device;
	private int size;
	private String wipe;
	private int number;
	private boolean preserve;
	private boolean grubDevice;
	private int offset;
	private boolean resize;
	private String type;
	private String id;

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getWipe() {
		return wipe;
	}

	public void setWipe(String wipe) {
		this.wipe = wipe;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
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

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
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
