package goa.systems.commons.semvers;

import java.util.List;

/**
 * Offers basic semantic version handling tools.
 */
public class SemVerTools {

	/**
	 * This functions returns the highest version out of the list of versions. There
	 * is no error handling and it can only handle three segment semantic versions.
	 * Suffixes or different formats are not supported.
	 * 
	 * @param versions List of versions.
	 * @return The highest version.
	 * @throws SemVerToolsException If a version is not three segments.
	 */
	public Integer[] getLatestVersion(List<Integer[]> versions) throws SemVerToolsException {

		if (versions == null || versions.isEmpty()) {
			return new Integer[] {};
		}

		Integer[] latestversion = versions.get(0);

		for (int i = 1; i < versions.size(); i++) {

			Integer[] currentversion = versions.get(i);

			if (currentversion.length != 3) {
				throw new SemVerToolsException("Version does not contain three segments.");
			}
			latestversion = compareVersions(currentversion, latestversion);
		}

		return latestversion;
	}

	/**
	 * Helper function. Compares the three segments.
	 * 
	 * @param currentversion The current version to compare the latest version.
	 * @param latestversion  The already defined, latest version.
	 * @return The higher of the two versions.
	 */
	private Integer[] compareVersions(Integer[] currentversion, Integer[] latestversion) {

		if (currentversion[0] > latestversion[0]) {
			return currentversion;
		} else {
			if (currentversion[0].equals(latestversion[0]) && currentversion[1] > latestversion[1]) {
				return currentversion;
			} else {
				if (currentversion[0].equals(latestversion[0]) && currentversion[0].equals(latestversion[1])
						&& currentversion[2] > latestversion[2]) {
					return currentversion;
				}
			}
		}
		return latestversion;
	}

	/**
	 * Gets the highest version but formats it as a string. The same restrictions as
	 * for getLatestVersion(List&lt;Integer[]&gt; versions) apply to this function.
	 * 
	 * @param versions List of versions.
	 * @return The highest version.
	 * @throws SemVerToolsException
	 */
	public String getLatestVersionAsString(List<Integer[]> versions) throws SemVerToolsException {
		Integer[] latestversion = getLatestVersion(versions);
		return String.format("%d.%d.%d", latestversion[0], latestversion[1], latestversion[2]);
	}
}
