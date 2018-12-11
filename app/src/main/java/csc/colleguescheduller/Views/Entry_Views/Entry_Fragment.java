package csc.colleguescheduller.Views.Entry_Views;


/*
Ahmed Mokhtar Hassanin
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.MyFragment;


public abstract class Entry_Fragment extends MyFragment {

    private MyFragment targetfragment_onhold;
    protected Boolean RequiredToSave = false;
    protected Boolean SaveDenied = false;

    public Entry_Fragment() {
        super();
    }

    public Entry_Fragment(ActivitytoFragment_interface activity) {
        super(activity);
    }

    public Entry_Fragment(ActivitytoFragment_interface activity, String title, MyFragment previous) {
        super(activity, title, previous);
    }

    public Entry_Fragment(ActivitytoFragment_interface activity, String title) {
        super(activity, title);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apply_model();
        setRequiredToSave(false);
        set_buttons();

    }

    protected void apply_model() {

    }

    protected void set_buttons() {

    }

    public void Save() {

    }

    public void on_logout() {

    }

    public void on_askmessage_result(Boolean result) {
        if (result) {
            Save();
        }else{
            setRequiredToSave(false);
            activity.move_to_fragment(getTargetfragment_onhold());
        }
    }

    public Boolean getRequiredToSave() {
        return RequiredToSave;
    }

    public void setRequiredToSave(Boolean requiredToSave) {
        RequiredToSave = requiredToSave;
    }

    public MyFragment getTargetfragment_onhold() {
        if (targetfragment_onhold == null)
            return previous;
        else
            return targetfragment_onhold;
    }

    public void setTargetfragment_onhold(MyFragment targetfragment_onhold) {
        this.targetfragment_onhold = targetfragment_onhold;
    }

    public void on_save_success(){
        activity.abort_connection(null);
        setRequiredToSave(false);
        if(targetfragment_onhold == null){
            activity.onBackPressed();
        }else{
            activity.move_to_fragment(targetfragment_onhold);
        }
    }
}
