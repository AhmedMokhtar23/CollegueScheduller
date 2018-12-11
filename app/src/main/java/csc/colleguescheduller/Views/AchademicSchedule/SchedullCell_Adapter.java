package csc.colleguescheduller.Views.AchademicSchedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import csc.colleguescheduller.Models.Schedule.MemberSchedule.MemberCell;
import csc.colleguescheduller.Models.Schedule.Specialization;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;

public class SchedullCell_Adapter extends ArrayAdapter<MemberCell> {

    ActivitytoFragment_interface activity;

    public SchedullCell_Adapter(ActivitytoFragment_interface activity, ArrayList<MemberCell> s) {
        super((Context) activity, R.layout.academicschedule_subject_lstitm,s);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from((Context) activity).inflate(R.layout.academicschedule_subject_lstitm, parent, false);
        MemberCell cell = getItem(position);
        ((TextView) v.findViewById(R.id.achademicschedule_subject_txtv_course)).setText(cell.getCourse().getName());
        ((TextView) v.findViewById(R.id.academic_shedule_time)).setText(calculate_time(cell.getStart()));
        ((TextView) v.findViewById(R.id.academic_shedule_hall)).setText(cell.getRoom().getRoomid());
        ((TextView) v.findViewById(R.id.academic_shedule_year)).setText(cell.getYear().ordinal() + "." + cell.getSection().ordinal());
        ((TextView) v.findViewById(R.id.academic_shedule_tag)).setText(get_specialization(cell.getGroup()));
        return v;

    }

    private String calculate_time(int start) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 9);
        c.set(Calendar.MINUTE, 10);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.MINUTE, start * 45);
        return new SimpleDateFormat("hh:mm").format(c.getTime());
    }

    private String get_specialization(Specialization specialization) {
        switch (specialization) {
            case General:
                return "GENERAL";
            case Computer_Science:
                return "CS";
            case Information_Systems:
                return "IS";
            default:
                return "";
        }
    }
}
