package goa.systems.commons.time;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeCalc {

	private String dailyworkstart;
	private int[] freedays;

	public TimeCalc() {
		this.dailyworkstart = "08:00:00";
		this.freedays = new int[] { Calendar.SATURDAY, Calendar.SUNDAY };
	}

	/**
	 * Calculates the time difference in seconds between start and end
	 * 
	 * @param start Start time stamp. Has to be lower than the start time stamp.
	 * @param end   End time stamp. Has to be higher than the start time stamp.
	 * @return The time difference.
	 * @throws TimeCalculationException End time stamp is lower than start time
	 *                                  stamp.
	 */
	public long calcTimeDifference(long start, long end) throws TimeCalculationException {

		if (end < start) {
			throw new TimeCalculationException("End time stamp is lower than start time stamp.");
		}

		return end - start;
	}

	/**
	 * Calculates the time difference in seconds between start and end
	 * 
	 * @param start Start time stamp. Has to be lower than the start time stamp.
	 * @param end   End time stamp. Has to be higher than the start time stamp.
	 * @return The time difference.
	 * @throws TimeCalculationException End time stamp is lower than start time
	 *                                  stamp.
	 */
	public long calcTimeDifference(TimeUnit start, TimeUnit end) throws TimeCalculationException {
		return calcTimeDifference(start.toSeconds(), end.toSeconds());
	}

	/**
	 * Creates a new GregorianCalendar object set to the start of the work day based
	 * on configuration.
	 * 
	 * @param timestamp to calculate start of day for.
	 * @return The start of the day for the given time stamp as new object.
	 * @throws TimeCalculationException if given day is not a work day.
	 */
	public GregorianCalendar getStartOfWorkDay(GregorianCalendar timestamp) throws TimeCalculationException {
		GregorianCalendar startofworkday = new GregorianCalendar();
		for (int freedayindex : this.freedays) {
			if (freedayindex == timestamp.get(Calendar.DAY_OF_WEEK)) {
				throw new TimeCalculationException("Given day is not a work day.");
			}
		}
		startofworkday.setTimeInMillis(timestamp.getTimeInMillis());
		String[] dwscomps = this.dailyworkstart.split(":");
		startofworkday.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dwscomps[0]));
		startofworkday.set(Calendar.MINUTE, Integer.parseInt(dwscomps[1]));
		startofworkday.set(Calendar.SECOND, Integer.parseInt(dwscomps[2]));
		return startofworkday;
	}
}
