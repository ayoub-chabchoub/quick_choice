package tn.example.ayoub.mygamev2app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ayoub.mygamev2app.R;

import java.util.zip.Inflater;

/**
 * Created by ayoub on 31/01/2018.
 */

public class FragmentDemo extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.toolbar_content,container,false);
    }
}
