package goa.systems.commons.validity;

/**
 * This class implements often required validity checks.
 * 
 * @author ago
 * @since 2021-06-06
 *
 */
public class Validity {

	private Validity() {
	}

	/**
	 * Checks if the input string is either null or empty.
	 * 
	 * @param input
	 * @return true if input string is either null or empty.
	 */
	public static boolean isNullOrEmpty(String input) {
		return input == null || input.isEmpty();
	}

	/**
	 * Checks if only one string is set with a value. null is considered a empty
	 * string.
	 * 
	 * @param str1
	 * @param str2
	 * @return true if only one string contains text
	 */
	public static boolean isOnlyOneSet(String str1, String str2) {
		return isNullOrEmpty(str1) ^ isNullOrEmpty(str2);
	}
}
