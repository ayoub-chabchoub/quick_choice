package tn.example.ayoub.mygamev2app;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ayoub.mygamev2app.R;

/**
 * Created by ayoub on 30/01/2018.
 */

public class SimpleFragmentPageAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] tabtitle = new String[]{"total_score"};
    final private int numberOfPages = 41;
    public SimpleFragmentPageAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
        for(int i = 1;i<=40;i++){
            tabtitle[i]="" + i;
        }

    }

    @Override
    public Fragment getItem(int position) {
        FragmentDemo fragmentDemo = new FragmentDemo();
        return fragmentDemo;
    }

    @Override
    public int getCount() {
        return numberOfPages;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return tabtitle[position];

    }
}
