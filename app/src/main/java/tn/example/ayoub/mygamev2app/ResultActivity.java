package tn.example.ayoub.mygamev2app;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayoub.mygamev2app.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.FileOutputStream;
import java.io.IOException;


public class ResultActivity extends AppCompatActivity/*Activity*/ {

    TextView txtv;
    TextView txtv1;
    Intent myIntent;
    Bundle b;
    Animation animation;
    ImageButton imageButton;
    private AdView mAdView;
    int numLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        txtv = (TextView)findViewById(R.id.textView3);
        imageButton = (ImageButton)findViewById(R.id.imageButton2);
        b = getIntent().getExtras();
        String result = b.getString("result");
        if(result.equals("victory")) {
            int score = b.getInt("score");
            score = score/10;
            setContentView(R.layout.activity_result_win);
            imageButton = (ImageButton)findViewById(R.id.imageButton2);
            txtv1 = (TextView)findViewById(R.id.textView6);
            txtv1.setText(String.valueOf(score));
            long totalScore = b.getLong("total_score") + score;
            b.putLong("total_score",totalScore);
            writeInFile(this,"score.txt",String.valueOf(totalScore));
            numLevel = b.getInt("num_level");

            if(numLevel==b.getInt("number of levels"))
                imageButton.setVisibility(View.INVISIBLE);
            else {
                b.putInt("num_level", ++numLevel);
                if((b.getInt("current_level")+1)==numLevel) {
                    b.putInt("current_level", numLevel);
                    writeInFile(this,"file.txt",String.valueOf(numLevel));
                }
            }
            DBConnection dbConnection = new DBConnection(this);
            int x = dbConnection.update(b.getInt("num_level"),score);
            if(x==0||x==-1){
                TextView txtv2 = (TextView)findViewById(R.id.textView3);
                txtv2.setText(getString(R.string.best_score));
            }else if(x>0){
                TextView txtv2 = (TextView)findViewById(R.id.textView2);
                txtv2.setText(getString(R.string.best_score) + ": " + x);
            }

        }
        if(result.equals("lose")) {
            setContentView(R.layout.activity_result);
            imageButton = (ImageButton)findViewById(R.id.imageButton2);
            imageButton.setBackgroundResource(R.drawable.reset1);
            animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
            AnimThread animThread = new AnimThread();
            animThread.start();

        }
        MobileAds.initialize(this,"ca-app-pub-3940256099942544/6300978111");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }

    @Override
    public void onBackPressed(){
        buHome(null);
    }

    public void buHome(View view) {
        myIntent = new Intent(this,LevelsActivity.class);
        myIntent.putExtras(b);
        startActivity(myIntent);

    }

    public void buNext(View view) {
        myIntent = new Intent(this,MainActivity.class);
        myIntent.putExtras(b);
        startActivity(myIntent);

    }

    public static void writeInFile(Context context,String path, String text){

        try {
            FileOutputStream fos = context.openFileOutput(path, context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"file crate problem",Toast.LENGTH_LONG).show();
        }
    }

    class AnimThread extends Thread{


        public AnimThread() {

        }

        @Override
        public void run() {
            while(this.isAlive()) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageButton.startAnimation(animation);
                        }
                    });

                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
