package csc.colleguescheduller.Views;

/*
Ahmed Mokhtar Hassanin
 */

import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import csc.colleguescheduller.Models.Globals.Globals;
import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.SignIn.SignIn_Activity;
import csc.colleguescheduller.Views.Splash.Splash_Activity;

public abstract class MyActivity extends AppCompatActivity implements MyInterface,Message_interface {


    protected Boolean Screen_Locked = false;

    @Override
    protected void onStart() {
        super.onStart();
        if(!(this instanceof Splash_Activity)){
            if(Globals.session.Exiting) {
                onBackPressed();
            }else if(this instanceof SignIn_Activity){
                Globals.session.LogingOut = false;
            }else if(Globals.session.LogingOut){
                onBackPressed();
            }
        }

    }

    protected ViewGroup get_main_view() {
        return (ViewGroup) getWindow().getDecorView().getRootView();
    }


    protected void set_animation() {
        set_animation(get_main_view());
    }

    public void set_animation(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);
            if (v instanceof ViewGroup) {
                set_animation((ViewGroup) v);
            } else {
                if (v.isClickable()) {
                    v.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            switch (motionEvent.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    view.animate().scaleX(1.15f).scaleY(1.15f).setDuration(50);
                                    break;
                                case MotionEvent.ACTION_UP:
                                    view.animate().scaleX(1).scaleY(1).setDuration(50);
                                    break;
                            }
                            return false;
                        }
                    });
                }
            }

        }
    }


    protected void set_font() {
        set_font(get_main_view());
    }

    public void set_font(ViewGroup main_view){
        for(int i=0;i<main_view.getChildCount();i++){
            View v = main_view.getChildAt(i);
            if(v instanceof TextView){
                set_font(v);
            }else if(v instanceof ViewGroup){
                set_font((ViewGroup) v);
            }
        }
    }

    public void set_font(View v){
        ((TextView) v).setTextSize(TypedValue.COMPLEX_UNIT_SP,((TextView) v).getTextSize() / getResources().getDisplayMetrics().scaledDensity + Globals.font_difference);

    }

    protected void set_controls_enability(Boolean value) {
        set_controls_enability(value, get_main_view());
    }

    public void set_controls_enability(Boolean value, ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view.isClickable()) {
                view.setEnabled(value);
            }
            if (view instanceof ViewGroup) {
                set_controls_enability(value, (ViewGroup) view);
            }
        }
    }

    private View get_message() {
        View result = getLayoutInflater().inflate(R.layout.message_layout, null, false);
        set_font((ViewGroup) result);
        set_animation((ViewGroup) result);
        return result;
    }

    public void show_message(String message_text,@DrawableRes int icon) {
        if(!Screen_Locked){
            View message = get_message();
            ((TextView) message.findViewById(R.id.message_txt_message)).setText(message_text);
            ((ImageView) message.findViewById(R.id.message_img_icon)).setImageResource(icon);
            message.setAlpha(0);
            get_main_view().addView(message);
            message.animate().alpha(1).setDuration(100);
            Screen_Locked = true;
        }
    }

    public void hide_message() {
        get_main_view().removeView(findViewById(R.id.message));
        Screen_Locked = false;
    }

    public void message_back_click(View v) {
        hide_message();
    }

    public void message_retry(View v) {
        hide_message();
    }

    public void abort_connection(View v){
        get_main_view().removeView(findViewById(R.id.connecting_layout));
        set_controls_enability(true);
        Screen_Locked = false;
    }

    public void on_connecting() {
        View v = getLayoutInflater().inflate(R.layout.connecting_layout,null,false);
        v.findViewById(R.id.connecting_img).animate().rotation(3600).setDuration(10000);
        set_controls_enability(false);
        get_main_view().addView(v);
        Screen_Locked = true;
    }

    @Override
    public void on_connection_error() {
        abort_connection(null);
        show_message("Make sure you have internet connection", R.drawable.ic_wifi);
    }

    @Override
    public void on_database_error(String message) {

    }


    @Override
    public void on_authentication_error(String message) {
        abort_connection(new View(this));
        show_message(message, R.drawable.ic_password);
    }

    @Override
    public void on_logout() {
        Globals.session.LogingOut = true;
        Toast.makeText(this,"The Account has been loged out",Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }
}