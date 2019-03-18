package tn.example.ayoub.mygamev2app;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayoub.mygamev2app.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LevelsActivity extends AppCompatActivity/*Activity*/ {
    final static int NBRE_OF_lEVELS = 40;
    ArrayList<Integer> levels;
    Intent myIntent;
    Bundle bundle;
    static int currentLevel;
    Switch mySwitch;
    private AdView mAdView;
    PopMenu popMenu,popMenu1;
    long id;
    static LevelsActivity context;
    static long total_score;
    TextView txtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        levels = new ArrayList<>();
        for (int i = 1; i <= NBRE_OF_lEVELS; i++) {
            levels.add(new Integer(i));
        }
        context=this;
        id = getMacAddress(this);
        bundle = getIntent().getExtras();
        mySwitch = (Switch) findViewById(R.id.switch1);
        myIntent = new Intent(this, MainActivity.class);
        currentLevel = bundle.getInt("current_level");
        bundle.putInt("number of levels", NBRE_OF_lEVELS);
        initGridView();
        txtv = (TextView) findViewById(R.id.textView5);
        total_score = bundle.getLong("total_score");
        txtv.setText(String.valueOf(total_score));
        FileInputStream fis = null;
        try {
            try {
                fis = this.openFileInput("sound.txt");
            } catch (FileNotFoundException ex) {
                initializeFile(this,"sound.txt", "1");
                fis = this.openFileInput("sound.txt");
            }
            int i = 0;
            String s = "";
            while ((i = fis.read()) != -1) {
                s += String.valueOf((char) i);
            }

            if (s.equals("1")) {
                bundle.putBoolean("sound", true);
            } else if (s.equals("0")) {
                mySwitch.setChecked(false);
                bundle.putBoolean("sound", false);
            } else {
                Toast.makeText(this, "problem with sound", Toast.LENGTH_LONG).show();
            }
            fis.close();


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "IOException", Toast.LENGTH_LONG).show();
        }

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    bundle.putBoolean("sound", true);
                    initializeFile(context,"sound.txt", "1");
                } else {
                    bundle.putBoolean("sound", false);
                    initializeFile(context,"sound.txt", "0");
                }
            }
        });

        if (isOnline() && bundle.getBoolean("test")) {

            if ( new File(getApplicationContext().getFilesDir(),"nick_name.txt").exists()){
                Toast.makeText(this,"true", Toast.LENGTH_LONG).show();
                updateServer(this);
            } else {
                Toast.makeText(this,"false", Toast.LENGTH_LONG).show();
                FragmentManager manager = getFragmentManager();
                popMenu = new PopMenu();
                Bundle b = new Bundle();
                b.putInt("ressource", R.layout.inset_nick_name);
                b.putLong("id",id);
                b.putLong("score",bundle.getLong("total_score"));
                b.putLong("total_score",bundle.getLong("total_score"));
                popMenu.setArguments(b);
                popMenu.show(manager, null);

            }
            bundle.putBoolean("test",false);

        }
    }

    public void initGridView(){
        BaseAdapter adapter = new MyCustomAdapter(levels);
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
    }




    public static void initializeFile(Context context,String path,String text) {
        try {
            FileOutputStream fos = context.openFileOutput(path, context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(context,path + " created successfully",Toast.LENGTH_LONG).show();
        }catch(FileNotFoundException ex) {
            Toast.makeText(context,"FileNotFoundException",Toast.LENGTH_LONG).show();
        }catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"IOException111",Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed(){
        startActivity(new Intent(this,firstActivity.class));
    }

    public void buRank(View view) {
        FragmentManager manager = getFragmentManager();
        popMenu1 = new PopMenu();
        Bundle b1 = new Bundle();
        if(isOnline())
        b1.putInt("ressource",R.layout.rank_layout);
        else
            b1.putInt("ressource",R.layout.rank_layout1);
        popMenu1.setArguments(b1);
        popMenu1.show(manager,null);

    }
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void buClose(View view) {
        popMenu.dismiss();
    }



    public static long getMacAddress(Context context)

    {
        String s = Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;

    }

    public static void updateServer(Context context) {
        FileInputStream fis = null;
        String s = "";
        try {
            try {
                fis = context.openFileInput("update_server.txt");
            }catch(FileNotFoundException ex) {
                initializeFile(context,"update_server.txt","1");
                fis = context.openFileInput("update_server.txt");
            }
            int i=0;

            while((i = fis.read())!=-1){
                s += String.valueOf((char)i);
            }
            if(s.length()>2) {
                new Connexion2Server(null).execute("https://ayoubchabchoub.000webhostapp.com/save_levels.php?id=" +
                        getMacAddress(context) + "&" + s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class MyCustomAdapter extends BaseAdapter {

        ArrayList<Integer> items= new ArrayList<>();
        MyCustomAdapter(ArrayList<Integer> items){

            this.items=items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            LayoutInflater linflater= getLayoutInflater();
            View view1 = linflater.inflate(R.layout.cellule_button,null);
            Button button = (Button)view1.findViewById(R.id.button);
            button.setText(items.get(i).toString());
            if(Integer.parseInt(items.get(i).toString())>currentLevel){
                Context context = null;
                Drawable d;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    d = ResourcesCompat.getDrawable(getResources(),R.drawable.button_level1,null);
                } else {
                    d = getDrawable(R.drawable.button_level1);
                }

                button.setBackground(d);
            }else {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bundle.putInt("num_level", items.get(i).intValue());
                        myIntent.putExtras(bundle);
                        startActivity(myIntent);
                    }
                });
            }


            return view1;
        }
    }
}
