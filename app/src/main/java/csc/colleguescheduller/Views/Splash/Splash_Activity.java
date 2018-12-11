package csc.colleguescheduller.Views.Splash;

/*
Ahmed Mokhtar Hassanin
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import csc.colleguescheduller.Controllers.Splash.Splash_Controller;
import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.Models.Globals.Session;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.CollectingInformation.CollectingInformation_Activity;
import csc.colleguescheduller.Views.Main.Main_Activity;
import csc.colleguescheduller.Views.MyActivity;
import csc.colleguescheduller.Views.SignIn.SignIn_Activity;

public class Splash_Activity extends MyActivity implements Splash_Interface {

    Splash_Controller controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        controller = new Splash_Controller(this);
        Globals.font_difference = controller.get_font_difference();
        set_font();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Globals.session == null){
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    (new Handler(getMainLooper())).post(new Runnable() {
                        @Override
                        public void run() {
                            controller.can_start();
                        }
                    });
                }
            }, 1000);
        }else{
            onBackPressed();
        }
    }

    @Override
    public void hide_message() {
        super.hide_message();
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Globals.session = null;
        super.onBackPressed();
    }

    @Override
    public void on_success() {
        Globals.session = new Session();
        startActivity(new Intent(this, SignIn_Activity.class));
    }

    @Override
    public void on_logout() {

    }
}
