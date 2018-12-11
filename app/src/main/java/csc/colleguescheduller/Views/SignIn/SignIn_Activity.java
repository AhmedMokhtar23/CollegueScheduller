package csc.colleguescheduller.Views.SignIn;

/*
Ahmed Mokhtar Hassanin
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import csc.colleguescheduller.Controllers.SignIn.SignIn_Controller;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.CollectingInformation.CollectingInformation_Activity;
import csc.colleguescheduller.Views.MyActivity;

public class SignIn_Activity extends MyActivity implements SignIn_Interface {

    SignIn_Controller controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);
        controller = new SignIn_Controller(this);
        set_animation();
        set_font();
    }

    @Override
    protected void onStart() {
        super.onStart();
        on_connecting();
        controller.check_user();
    }

    public void signin_click(View v) {
        on_connecting();
        controller.SignIn((
                        (EditText) findViewById(R.id.signin_txte_username)).getText().toString(),
                ((EditText) findViewById(R.id.signin_txte_password)).getText().toString());
    }

    @Override
    public void abort_connection(View v) {
        controller.Abort_Thread();
        super.abort_connection(v);
    }

    @Override
    public void on_success() {
        abort_connection(new View(this));
        startActivity(new Intent(this, CollectingInformation_Activity.class));
    }

    public void on_database_error(String message){
        show_message(message,R.drawable.ic_robot_unready);
    }

    @Override
    public void on_logout() {

    }

    @Override
    public void on_signin() {
        abort_connection(null);
    }
}