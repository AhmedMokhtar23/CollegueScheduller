package csc.colleguescheduller.Controllers.Splash;

/*
Ahmed Mokhtar Hassanin
 */

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import csc.colleguescheduller.Controllers.Controller;
import csc.colleguescheduller.Views.Splash.Splash_Interface;

public class Splash_Controller extends Controller {

    private Splash_Interface UI;
    private Activity activity;

    public Splash_Controller(Splash_Interface UI){
        super();
        this.UI = UI;
        activity = (Activity) UI;
    }

    public void can_start(){
        if(!internet_available()){
            UI.on_connection_error();
        }else{
            UI.on_success();
        }
    }

    public float get_font_difference(){
        float width = Math.round((float) activity.getResources().getDisplayMetrics().widthPixels / activity.getResources().getDisplayMetrics().xdpi);
        float height = Math.round((float) activity.getResources().getDisplayMetrics().heightPixels / activity.getResources().getDisplayMetrics().ydpi);
        int screen_size = Math.round((float) Math.sqrt(Math.pow(width,2) + Math.pow(height,2)));
        switch (screen_size){
            case 5 : return 3f;
            case 6 : return 4f;
            case 7 : return 5f;
            case 8 : return 6f;
            case 9 : return 7f;
            case 10 : return 8f;
            default: return 0;
        }
    }

    private boolean internet_available() {
        NetworkInfo networkInfo = ((ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null) return false;
        else return networkInfo.isAvailable() && networkInfo.isConnected();
    }

}
