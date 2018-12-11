package csc.colleguescheduller.Views.AchademicSchedule;

import csc.colleguescheduller.Models.Schedule.MemberSchedule.MemberSchedule;
import csc.colleguescheduller.Views.MyInterface;

public interface AcandemicSchedule_Interface extends MyInterface {
    void on_schedule_loaded(MemberSchedule memberSchedule);
}
