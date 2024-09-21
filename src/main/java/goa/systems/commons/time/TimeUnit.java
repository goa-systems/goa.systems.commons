package goa.systems.commons.time;

public class TimeUnit {

	private int hours;
	private int minutes;
	private int seconds;

	public TimeUnit() {
		this.hours = 0;
		this.minutes = 0;
		this.seconds = 0;
	}

	public TimeUnit(int hours, int minutes, int seconds) {
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public long toSeconds() {
		return (this.hours * 3600) + (this.minutes * 60) + this.seconds;
	}

	public void fromSeconds(long seconds) {
		this.seconds = Math.toIntExact(seconds % 60);
		seconds = seconds - this.seconds;
		this.minutes = Math.toIntExact(seconds / 60);
		this.hours = Math.toIntExact(seconds / 60);
		this.minutes = this.minutes % 60;
		this.hours = this.hours - this.minutes;
		this.hours = this.hours / 60;
	}

	/**
	 * Parses the given string into hours, minutes and seconds.
	 * 
	 * @param time Time as HH:mm:ss format.
	 * @throws TimeCalculationException If an error in parsing occurs.
	 */
	public void parse(String time) throws TimeCalculationException {

		if (time == null) {
			throw new TimeCalculationException("The provided time is null");
		}

		String[] te = time.split(":");

		if (te.length != 3) {
			throw new TimeCalculationException("The provided time is of the wrong format.");
		}

		try {
			this.hours = Integer.parseInt(te[0]);
			this.minutes = Integer.parseInt(te[1]);
			this.seconds = Integer.parseInt(te[2]);
		} catch (NumberFormatException e) {
			throw new TimeCalculationException("The time elements can not be parsed.");
		}
	}

	@Override
	public String toString() {
		return String.format("%02d:%02d:%02d", this.hours, this.minutes, this.seconds);
	}
}
