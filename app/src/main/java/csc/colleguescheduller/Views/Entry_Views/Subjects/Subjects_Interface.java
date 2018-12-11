package csc.colleguescheduller.Views.Entry_Views.Subjects;

/*
Ahmed Mokhtar Hassanin
 */

import java.util.ArrayList;

import csc.colleguescheduller.Models.Subjects.Subject;
import csc.colleguescheduller.Views.MyInterface;

public interface Subjects_Interface extends MyInterface {
    void on_subjects_loaded(ArrayList<Subject> subjects);
    void on_subject_removed();
    void on_save_success();
}
