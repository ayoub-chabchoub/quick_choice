package tn.example.ayoub.mygamev2app;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ayoub.mygamev2app.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity/*Activity*/ implements View.OnClickListener {

    boolean AUTO_ADD =false;
    ArrayList<Button> arrayUsedButton = null;
    ArrayList<Integer> tableInt;
    ArrayList<Float> tableFloat;
    ArrayList<Button> arrayButton = new ArrayList<>();
    Intent myIntent;
    TextView txtCounter;
    Bundle bundle;
    Counter ct;
    int a=100,b=0,c=100;
    boolean isInt = true;
    short counterTime;
    short timeToAdd = 10;
    long score;
    MediaPlayer mediaPlayer;
    MediaPlayer victorySound;
    boolean sound;
    float coef = 1f;
    byte size;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bundle = getIntent().getExtras();
        int x = bundle.getInt("num_level");
        myIntent = new Intent(this, ResultActivity.class);
        sound = bundle.getBoolean("sound");
        //1 setContentView
        //2 connect with the buttons of the view        -->setlevel
        //3 set a,b and c varialbles and initialize the table of numbers
        //4 initialize buttons
        //5  sort the table of numbers

      if(x<=8)
          setLevel(R.layout.levels_3_4);
      else if(x>8 && x<=24)
          setLevel(R.layout.activity_main);
      else
          setLevel(R.layout.level_5_6);

        TextView numLevel = (TextView)findViewById(R.id.textView4);
        numLevel.setText(x + "/" + bundle.getInt("number of levels"));
      chooseLevel(x);
        MobileAds.initialize(this,"ca-app-pub-3940256099942544/6300978111");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if(isInt){
            initializeTableInt();
            initializeButtons();
            Collections.sort(tableInt);
        }
        else {
            initializeTableFloat();
            initializeButtons();
            Collections.sort(tableFloat);
        }

        size = (byte)arrayButton.size();



            if(AUTO_ADD){
             AddButtons addButtons = new AddButtons(timeToAdd);
                addButtons.start();
            }
            if(sound) {
                mediaPlayer = MediaPlayer.create(this, R.raw.button_42);
                victorySound = MediaPlayer.create(this, R.raw.victory);
            }

            ct = new Counter(counterTime, 1);
            ct.start();

    }

    void chooseLevel(int x){

        switch (x){
            case 1:
                counterTime = 17;
                break;
            case 2:
                a = 1000;
                counterTime = 18;
                break;
            case 3:
                isInt = false;
                counterTime = 19;
                break;
            case 4:
                isInt = false;
                a = 1000;
                counterTime = 21;
                break;
            case 5:
                AUTO_ADD = true;
                counterTime = 17;
                break;
            case 6:
                AUTO_ADD = true;
                a = 1000;
                counterTime = 17;
                break;
            case 7:
                AUTO_ADD = true;
                isInt = false;
                counterTime = 21;
                break;
            case 8:
                AUTO_ADD = true;
                isInt = false;
                a = 1000;
                counterTime = 19;
                break;
            case 9:
                counterTime = 33;
                break;
            case 10:
                a = 201;
                b = 100;
                counterTime = 29;
                break;
            case 11:
                a =1000;
                counterTime = 40;
                break;
            case 12:
                a = 2001;
                b = 1000;
                counterTime = 35;
                break;
            case 13:
                isInt = false;
                counterTime = 48;
                break;
            case 14:
                a = 1000;
                isInt = false;
                counterTime = 40;
                break;
            case 15:
                a = 201;
                b = 100;
                isInt = false;
                counterTime = 40;
                break;
            case 16:
                a = 2001;
                b = 1000;
                isInt = false;
                counterTime = 38;
                break;
            case 17:
                AUTO_ADD = true;
                counterTime = 45;
                break;
            case 18:
                a = 201;
                b = 100;
                AUTO_ADD = true;
                counterTime = 30;
                break;
            case 19:
                a =1000;
                AUTO_ADD = true;
                counterTime = 35;
                break;
            case 20:
                a = 2001;
                b = 1000;
                AUTO_ADD = true;
                counterTime = 30;
                break;
            case 21:
                isInt = false;
                AUTO_ADD = true;
                counterTime = 50;
                break;
            case 22:
                a = 1000;
                isInt = false;
                AUTO_ADD = true;
                counterTime = 50;
                break;
            case 23:
                a = 201;
                b = 100;
                isInt = false;
                AUTO_ADD = true;
                counterTime = 40;
                break;
            case 24:
                a = 2001;
                b = 1000;
                isInt = false;
                AUTO_ADD = true;
                counterTime = 45;
                break;
            case 25:
                counterTime = 55;
                break;
            case 26:
                a = 201;
                b = 100;
                counterTime = 65;
                break;
            case 27:
                a =1000;
                counterTime = 95;
                break;
            case 28:
                a = 2001;
                b = 1000;
                counterTime = 80;
                break;
            case 29:
                isInt = false;
                counterTime = 90;
                break;
            case 30:
                a = 1000;
                isInt = false;
                counterTime = 100;
                break;
            case 31:
                a = 201;
                b = 100;
                isInt = false;
                counterTime = 90;
                break;
            case 32:
                a = 2001;
                b = 1000;
                isInt = false;
                counterTime = 80;
                break;
            case 33:
                AUTO_ADD = true;
                counterTime = 80;
                break;
            case 34:
                a = 201;
                b = 100;
                AUTO_ADD = true;
                counterTime = 80;
                break;
            case 35:
                a =1000;
                AUTO_ADD = true;
                counterTime = 80;
                break;
            case 36:
                a = 2001;
                b = 1000;
                AUTO_ADD = true;
                counterTime = 72;
                break;
            case 37:
                isInt = false;
                AUTO_ADD = true;
                counterTime = 115;
                break;
            case 38:
                a = 1000;
                isInt = false;
                AUTO_ADD = true;
                counterTime = 95;
                break;
            case 39:
                a = 201;
                b = 100;
                isInt = false;
                AUTO_ADD = true;
                counterTime = 90;
                break;
            case 40:
                a = 2001;
                b = 1000;
                isInt = false;
                AUTO_ADD = true;
                counterTime = 100;
                break;
        }

    }

    void setLevel(int layoutid){
        setContentView(layoutid);
        connect();
        if(layoutid==R.layout.activity_main)
            connect2();
        if(layoutid==R.layout.level_5_6) {
            connect2();
            connect3();
        }
    }


    void connect(){
        arrayButton.add((Button) findViewById(R.id.button1));
        arrayButton.add((Button) findViewById(R.id.button2));
        arrayButton.add((Button) findViewById(R.id.button3));
        arrayButton.add((Button) findViewById(R.id.button4));
        arrayButton.add((Button) findViewById(R.id.button5));
        arrayButton.add((Button) findViewById(R.id.button6));
        arrayButton.add((Button) findViewById(R.id.button7));
        arrayButton.add((Button) findViewById(R.id.button8));
        arrayButton.add((Button) findViewById(R.id.button9));
        arrayButton.add((Button) findViewById(R.id.button10));
        arrayButton.add((Button) findViewById(R.id.button11));
        arrayButton.add((Button) findViewById(R.id.button12));
    }

    void connect2(){
        arrayButton.add((Button) findViewById(R.id.button13));
        arrayButton.add((Button) findViewById(R.id.button14));
        arrayButton.add((Button) findViewById(R.id.button15));
        arrayButton.add((Button) findViewById(R.id.button16));
        arrayButton.add((Button) findViewById(R.id.button17));
        arrayButton.add((Button) findViewById(R.id.button18));
        arrayButton.add((Button) findViewById(R.id.button19));
        arrayButton.add((Button) findViewById(R.id.button20));
    }

    void connect3(){
        arrayButton.add((Button) findViewById(R.id.button21));
        arrayButton.add((Button) findViewById(R.id.button22));
        arrayButton.add((Button) findViewById(R.id.button23));
        arrayButton.add((Button) findViewById(R.id.button24));
        arrayButton.add((Button) findViewById(R.id.button25));
        arrayButton.add((Button) findViewById(R.id.button26));
        arrayButton.add((Button) findViewById(R.id.button27));
        arrayButton.add((Button) findViewById(R.id.button28));
        arrayButton.add((Button) findViewById(R.id.button29));
        arrayButton.add((Button) findViewById(R.id.button30));
    }

    void initializeTableInt() {
        tableInt = new ArrayList<>();
        while (tableInt.size() < arrayButton.size()) {
            int x = (int)(Math.random() * a) - b;
            Integer x1 = new Integer(x);
            if (!tableInt.contains(x1)) {
                tableInt.add(x1);
            }
        }
    }

    void initializeTableFloat() {
        tableFloat = new ArrayList<>();
        while (tableFloat.size() < arrayButton.size()) {
            int x = (int)(Math.random() * a) - b;
            Float x1 = new Float((float)x/c);
            if (!tableFloat.contains(x1)) {
                tableFloat.add(x1);
            }
        }
    }

    void initializeButtons(){
        if (isInt) {
            for (int i = 0; i < tableInt.size(); i++) {
                arrayButton.get(i).setText(String.valueOf(tableInt.get(i)));
                arrayButton.get(i).setOnClickListener(this);
            }
        }
        else{
            for (int i = 0; i < tableFloat.size(); i++) {
                arrayButton.get(i).setText(tableFloat.get(i).toString());
                arrayButton.get(i).setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String s = button.getText().toString();
        if (!s.equals("")) {
            if(
                    isInt) {
                int numPressed = Integer.parseInt(s);
                if (numPressed == tableInt.get(0)) {
                    tableInt.remove(0);
                    if (AUTO_ADD)
                        arrayUsedButton.add(button);
                    button.setVisibility(View.INVISIBLE);
                    size--;
                    if (tableInt.size() == 0) {
                        ct.cancel();
                        if(sound)
                        victorySound.start();
                        bundle.putString("result", "victory");
                        bundle.putInt("score",(int)(score*coef));
                        myIntent.putExtras(bundle);
                        AUTO_ADD = false;
                        startActivity(myIntent);

                    }
                }
                else{
                    if(sound)
                    mediaPlayer.start();
                    coef *= 1-(1.0/size);

                }
            }else{
                float numPressed = Float.parseFloat(s);
                if (numPressed == tableFloat.get(0).floatValue()) {
                    tableFloat.remove(0);
                    if (AUTO_ADD)
                        arrayUsedButton.add(button);
                    button.setVisibility(View.INVISIBLE);
                    size--;
                    if (tableFloat.size() == 0) {
                        ct.cancel();
                        if(sound)
                        victorySound.start();
                        bundle.putString("result", "victory");
                        bundle.putInt("score",(int)(score*coef));
                        myIntent.putExtras(bundle);
                        AUTO_ADD = false;
                        startActivity(myIntent);

                    }
                }
                else{
                    if(sound)
                    mediaPlayer.start();
                    coef *= 1-(1.0/size);
                }
            }
        }
    }

    public void buReset(View view) {
        ct.cancel();
       myIntent = getIntent();
        finish();
        startActivity(myIntent);
    }


    public void buHome(View view) {
        ct.cancel();
        myIntent = new Intent(this,LevelsActivity.class);
        myIntent.putExtras(bundle);
        finish();
        startActivity(myIntent);
    }

    @Override
    public void onBackPressed(){
        buHome(null);
    }

    class AddButtons extends Thread{

        int timeToAdd;
        public AddButtons(short timeToAdd) {
            this.timeToAdd = timeToAdd * 1000;
            arrayUsedButton = new ArrayList<>();
        }

        @Override
        public void run() {
            if(isInt)
                runForInteger();
            else
                runForFloat();

        }

        void runForInteger(){
            while (AUTO_ADD) {
                try {
                    Thread.sleep(timeToAdd);
                    if (arrayUsedButton.size() > 0) {
                        int x;
                        Integer x1;
                        do {
                            x = (int) (Math.random() * a) - b;
                            x1 = new Integer(x);
                        }while(tableInt.contains(x1));
                            final Button button = arrayUsedButton.get(0);
                            button.setText(String.valueOf(x));
                            arrayUsedButton.remove(0);
                            if(!AUTO_ADD)
                                break;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    button.setVisibility(View.VISIBLE);
                                }
                            });
                            size++;
                            addInplace(x);

                    }
                } catch (InterruptedException e) {
                    Toast.makeText(null, "there is a problem with thread addButtons", Toast.LENGTH_LONG).show();
                }

                if (tableInt.size() == 0)
                    break;
            }
        }

        //runForFlaot
            void runForFloat(){
                while (AUTO_ADD) {
                    try {
                        Thread.sleep(timeToAdd);
                        if (arrayUsedButton.size() > 0) {
                            int x;
                            Float x1;
                            do {
                             x = (int) (Math.random() * a) - b;
                             x1 = new Float(((float) x / c));
                            }while (tableFloat.contains(x1));
                                final Button button = arrayUsedButton.get(0);
                                button.setText(x1.toString());
                                arrayUsedButton.remove(0);
                                if(!AUTO_ADD)
                                    break;

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        button.setVisibility(View.VISIBLE);
                                    }
                                });
                                size++;
                                addInplace(x);
                        }
                    } catch (InterruptedException e) {
                        Toast.makeText(null, "there is a problem with thread addButtons", Toast.LENGTH_LONG).show();
                    }
                    //Toast.makeText(null, "marche", Toast.LENGTH_SHORT).show();
                    if (tableFloat.size() == 0)
                        break;
                }
            }


        void addInplace(int x){
            int i = 0;
            if(isInt) {
                int size = tableInt.size();
               while (i<size && tableInt.get(i).intValue() < x) {
                   i++;
               }
               if(size==i)
                   tableInt.add(new Integer(x));
               else
               tableInt.add(i, new Integer(x));
           }
           else{
                int size = tableFloat.size();
                float x1 = (float)x/c;
                while (i<size && tableFloat.get(i).floatValue() < x1) {
                    i++;
                }
                if(size==i)
                    tableFloat.add(new Float(x1));
                else
                    tableFloat.add(i, new Float(x1));

            }
           }
        }


    class Counter extends CountDownTimer {

        public Counter(long millisInFuture, long countDownInterval){
            super(millisInFuture * 1000,countDownInterval);
            txtCounter = (TextView) findViewById(R.id.textView5);
        }

        @Override
        public void onTick(long l) {

            txtCounter.setText(String.valueOf((l/1000)+1));
            score = l;

        }

        @Override
        public void onFinish() {
            bundle.putString("result","lose");
            bundle.putString("score","0");
            myIntent.putExtras(bundle);
            startActivity(myIntent);
        }
    }
}