package csc.colleguescheduller.Models.Schedule;


import org.threeten.bp.DayOfWeek;

public class Schedule {

    private int workDays;
    private DayOfWeek startDay;

    public Schedule() {
        this.workDays = 5;
        this.startDay = DayOfWeek.SUNDAY;
    }

    public Schedule(int workDays, DayOfWeek startDay) {
        this.workDays = workDays;
        this.startDay = startDay;
    }

	public int getWorkDays() {
		return this.workDays;
	}

	public void setWorkDays(int workDays) {
		this.workDays = workDays;
	}

	public DayOfWeek getStartDay() {
		return this.startDay;
	}

	public void setStartDay(DayOfWeek startDay) {
		this.startDay = startDay;
	}

}
