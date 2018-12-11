package csc.colleguescheduller.Views;

/*
Ahmed Mokhtar Hassanin
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import csc.colleguescheduller.R;

public abstract class MyFragment extends Fragment implements Message_interface,MyInterface {

    protected ActivitytoFragment_interface activity;
    public String title;
    public MyFragment previous;

    public MyFragment() {

    }

    public MyFragment(ActivitytoFragment_interface activity) {
        this.activity = activity;
    }

    public MyFragment(ActivitytoFragment_interface activity, String title){
        this(activity);
        this.title = title;
    }

    public MyFragment(ActivitytoFragment_interface activity, String title,MyFragment previous) {
        this(activity,title);
        this.previous = previous;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setAlpha(0);
        view.animate().alpha(1).setDuration(300);
        activity.set_font((ViewGroup) view);
        activity.set_animation((ViewGroup) view);
    }

    @Override
    public void show_message(String message_text,@DrawableRes int icon) {
        activity.show_message(message_text,icon);
    }

    @Override
    public void message_back_click(View v) {
        activity.message_back_click(v);
    }

    @Override
    public void message_retry(View v) {
        activity.message_retry(v);
    }

    public void on_connection_error() {
        activity.on_connection_error();
    }

    public void on_database_error(String message) {
        activity.abort_connection(null);
        show_message(message, R.drawable.ic_robot_unready);
    }


    @Override
    public void on_logout() {

    }

    @Override
    public void on_authentication_error(String message) {
        activity.on_authentication_error(message);
    }

    public void on_confirm_authentication(Boolean yes,String password){

    }
}
