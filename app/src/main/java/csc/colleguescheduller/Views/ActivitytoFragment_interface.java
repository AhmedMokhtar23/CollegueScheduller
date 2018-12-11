package csc.colleguescheduller.Views;

/*
Ahmed Mokhtar Hassanin
 */

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public interface ActivitytoFragment_interface extends Message_interface {
    void set_animation(ViewGroup viewGroup);
    void set_font(ViewGroup main_view);
    void set_font(View main_view);
    void move_to_fragment(MyFragment fragment);
    void onBackPressed();
    void set_buttons(ImageButton btn2, ImageView btn1);
    void show_askmessage(MyFragment target);
    void show_confirm_message(String message_1,String message_2);
    void on_connecting();
    void abort_connection(View v);
    void on_connection_error();
    void on_authentication_error(String message);
}