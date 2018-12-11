package csc.colleguescheduller.Views.AchademicSchedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;

import csc.colleguescheduller.R;
import csc.colleguescheduller.Controllers.AcademicSchedule.AcademicSchedule_Controller;
import csc.colleguescheduller.Models.Schedule.MemberSchedule.MemberSchedule;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyFragment;

public class AcademicSchedule_Fragment extends MyFragment implements AcandemicSchedule_Interface {

    AcademicSchedule_Controller controller;
    MemberSchedule schedule;

    public AcademicSchedule_Fragment(ActivitytoFragment_interface activity) {
        super(activity, Main_Activity.ACADEMIC_SCHEDULE_TITLE);
        controller = new AcademicSchedule_Controller(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.achademic_schedule_layout, container, false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.on_connecting();
        controller.get_schedule();
    }

    private ArrayList<DayOfWeek> get_days() {
        ArrayList<DayOfWeek> result = new ArrayList<>();
        for (int i = schedule.getStartDay().ordinal(); i < (schedule.getStartDay().ordinal() + schedule.getWorkDays()); i++) {
            result.add(DayOfWeek.values()[i]);
        }
        return result;
    }

    private void load_cells(DayOfWeek day) {
        View v = getView();
        ((ListView)v.findViewById(R.id.academic_shedule_listv_subjects)).setAdapter(new SchedullCell_Adapter(activity,schedule.getRows().get(day)));
    }

    @Override
    public void on_schedule_loaded(MemberSchedule memberSchedule) {
        activity.abort_connection(null);
        this.schedule = memberSchedule;
        View v = getView();
        ListView listView = v.findViewById(R.id.achademicschedule_lstv_days);
        listView.setAdapter(new DayOfWeek_Adapter(activity,get_days()));
        listView = getView().findViewById(R.id.achademicschedule_lstv_days);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                load_cells((DayOfWeek) parent.getItemAtPosition(position));
            }
        });
    }
}
