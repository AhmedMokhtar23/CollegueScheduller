package csc.colleguescheduller.Views.Entry_Views;

/*
Ahmed Mokhtar Hassanin
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.MyActivity;

public class DatePicker_Activity extends MyActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.datepicker_activity);
        set_font();
        set_animation();
    }

    public void select(View v){
        Intent result = new Intent();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        DatePicker datePicker = findViewById(R.id.datepicker_cv);
        c.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
        String date = simpleDateFormat.format(c.getTime());
        result.putExtra("date",date);
        Log.d(Globals.LOG_TAG,date);
        setResult(0,result);
        finish();
    }

    @Override
    public void on_database_error(String message) {

    }

    @Override
    public void on_logout() {

    }
}
