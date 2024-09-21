package goa.systems.commons.time;

public class TimeCalc {

	/**
	 * Calculates the time difference in seconds between start and end
	 * 
	 * @param start Start time stamp. Has to be lower than the start time stamp.
	 * @param end   End time stamp. Has to be higher than the start time stamp.
	 * @return The time difference.
	 * @throws TimeCalculationException End time stamp is lower than start time
	 *                                  stamp.
	 */
	long calcTimeDifference(long start, long end) throws TimeCalculationException {

		if (end < start) {
			throw new TimeCalculationException("End time stamp is lower than start time stamp.");
		}

		return end - start;
	}
}
