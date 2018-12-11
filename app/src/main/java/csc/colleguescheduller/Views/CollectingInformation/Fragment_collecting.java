package csc.colleguescheduller.Views.CollectingInformation;

/*
Mohamed Mostafa Mohamed
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import csc.colleguescheduller.R;
import csc.colleguescheduller.Views.ActivitytoFragment_interface;
import csc.colleguescheduller.Views.MyFragment;


public class Fragment_collecting extends MyFragment {
    public Fragment_collecting()
    {}

    public Fragment_collecting(ActivitytoFragment_interface activity) {
        super(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
      ViewGroup v = (ViewGroup) inflater.inflate(R.layout.collectinginformation_fragment_collecting, container,false);
      activity.set_animation(v);
      activity.set_font(v);
      return v;
    }
}
