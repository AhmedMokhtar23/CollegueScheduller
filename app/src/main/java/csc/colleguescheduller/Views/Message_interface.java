package csc.colleguescheduller.Views;

/*
Ahmed Mokhtar Hassanin
 */

import android.support.annotation.DrawableRes;
import android.view.View;

public interface Message_interface {
    void show_message(String message_text, @DrawableRes int icon);
    void message_back_click(View v);
    void message_retry(View v);
}
