package tn.example.ayoub.mygamev2app;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ayoub.mygamev2app.R;

/**
 * Created by AYOUB on 06/11/2017.
 */

    public class RankWindow  extends DialogFragment {

    boolean internet = getArguments().getBoolean("internet");


    View form;

        @Nullable
        @Override
        public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){

            if(internet){

            }else {
                form = inflater.inflate(R.layout.rank_layout1, container, false);
            }
            return form;
        }



}

