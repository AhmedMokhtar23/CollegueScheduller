package csc.colleguescheduller.Views.ScheduleManagment;

import csc.colleguescheduller.Views.MyInterface;

public interface ScheduleManagment_Interface extends MyInterface {
    void on_generate_success();
    void on_generate_failed(String message);
}
