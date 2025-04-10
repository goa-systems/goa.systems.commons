package goa.systems.commons.migration;

public class DatabaseVersion {

	private String version;

	/**
	 * Creates an initialized DatabaseVersion object.
	 */
	public DatabaseVersion() {
		this(null);
	}
	
	/**
	 * Creates an initialized DatabaseVersion object.
	 */
	public DatabaseVersion(String version) {
		this.version = version == null || version.isBlank() || version.isEmpty() ? null : version;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return true if the database is initialized
	 */
	public boolean isInitialized() {
		return this.version != null;
	}
}
