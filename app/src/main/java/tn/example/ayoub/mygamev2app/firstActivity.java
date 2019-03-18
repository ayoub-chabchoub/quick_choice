package tn.example.ayoub.mygamev2app;


import android.content.Context;
import android.content.Intent;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.example.ayoub.mygamev2app.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class firstActivity extends AppCompatActivity/*Activity*/ {

    private AdView mAdView;
    Bundle bundle;
    Intent intent;
    PopMenu popMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        MobileAds.initialize(this,"ca-app-pub-3940256099942544/6300978111");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        bundle = new Bundle();

        FileInputStream fis = null;
        try {
            try {
                fis = this.openFileInput("file.txt");
            }catch(FileNotFoundException ex) {
                initializeFile("file.txt","1");
                fis = this.openFileInput("file.txt");
            }
            int i=0;
            String s = "";
            while((i = fis.read())!=-1){
                s += String.valueOf((char)i);
            }
            bundle.putInt("current_level",Integer.parseInt(s));

            try {
                fis = this.openFileInput("score.txt");
            }catch(FileNotFoundException ex) {
                initializeFile("score.txt","0");
                fis = this.openFileInput("score.txt");
            }
            i=0;
            s = "";
            while((i = fis.read())!=-1){
                s += String.valueOf((char)i);
            }
            bundle.putLong("total_score",Long.parseLong(s));
            fis.close();



        }catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"IOException",Toast.LENGTH_LONG).show();
        }

        bundle.putBoolean("test",true);
    }

    private void initializeFile(String path,String text) {
     try {
         FileOutputStream fos = this.openFileOutput(path, this.MODE_PRIVATE);
         fos.write(text.getBytes());
         fos.close();
     }catch(FileNotFoundException ex) {
         Toast.makeText(this,"FileNotFoundException",Toast.LENGTH_LONG).show();
    }catch (IOException e) {
        e.printStackTrace();
         Toast.makeText(this,"IOException111",Toast.LENGTH_LONG).show();
    }
    }

    static String getDataFromFile(Context context,String fname){
        FileInputStream fis = null;
        String s = "";
        try {
            fis = context.openFileInput(fname);

            int i=0;
            while((i = fis.read())!=-1){
                s += String.valueOf((char)i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;

    }

    public void buPlay(View view) {
        intent = new Intent(this,LevelsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void buHowToPlay(View view) {
        FragmentManager manager = getFragmentManager();
        popMenu = new PopMenu();
        Bundle b = new Bundle();
        b.putInt("ressource",R.layout.how_to_play_layout);
        popMenu.setArguments(b);
        popMenu.show(manager,null);

    }

    public void buClose(View view) {
        popMenu.dismiss();
    }
}
