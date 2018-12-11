package csc.colleguescheduller.Views.CollectingInformation;

/*
Mohamed Mostafa Mohamed
 */

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

import csc.colleguescheduller.Controllers.CollectingInformation.CollectingInformation_Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.MyActivity;
import csc.colleguescheduller.Views.MyFragment;


public class CollectingInformation_Activity extends MyActivity implements CollectingInfomation_interface,ActivitytoFragment_interface {
    CollectingInformation_Controller controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collectinginformation_layout);
        move_to_fragment(new Fragment_collecting(this));
        controller = new CollectingInformation_Controller(this);
        controller.checkCollectingInformation();
    }

    @Override
    public void ready() {
        move_to_fragment(new Fragment_Ready(this));
    }

    @Override
    public void time_Waiting(Calendar date) {
        move_to_fragment(new Fragment_WatingTime(this,date));
    }

    @Override
    public void not_Ready() {
        move_to_fragment(new Fragment_not_ready(this,false));
    }

    @Override
    public void move_to_fragment(MyFragment fragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.base_fragment, fragment);
        transaction.commit();
    }

    @Override
    public void set_buttons(ImageButton btn2, ImageView btn1) {

    }

    @Override
    public void show_askmessage(MyFragment target) {

    }

    @Override
    public void on_database_error(String message) {

    }

    @Override
    public void on_logout() {
        Globals.session.LogingOut = true;
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        controller.Logout();
        super.onBackPressed();
    }

    @Override
    public void show_confirm_message(String message_1, String message_2) {

    }

    @Override
    public void on_time_expired() {
        move_to_fragment(new Fragment_not_ready(this,true));
    }

}
