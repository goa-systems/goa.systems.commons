package goa.systems.commons.dimensions;

public class DimensionsCalculator {

	/**
	 * Calculates the percentage of the new size in relation to the original size.
	 * 
	 * @param originalsize Original size. For example the width or height of a
	 *                     picture.
	 * @param newsize      New size. For example the width or height to scale to.
	 * @return Percentage lower or greater than 100% based on dimensions.
	 */
	public double calcPercentage(double originalsize, double newsize) {
		if (originalsize == 0.0) {
			return 0;
		} else {
			return (100 * newsize) / originalsize;
		}
	}

	/**
	 * Calculates the factor.
	 * 
	 * @param percentage the Percentage between 0 and 100.
	 * @return The calculation factor.
	 */
	public double calcFactor(double percentage) {
		return percentage / 100;
	}
}
