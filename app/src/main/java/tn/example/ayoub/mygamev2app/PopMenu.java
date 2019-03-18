package tn.example.ayoub.mygamev2app;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.ayoub.mygamev2app.R;

import java.util.ArrayList;


/**
 * Created by AYOUB on 08/09/2017.
 */

public class PopMenu extends DialogFragment implements View.OnClickListener {

    int ressource;

   // public PopMenu() {

     //   this.ressource = getArguments().getInt("ressource");
    //}

    View form;
    //RetrieveFeedTask ret;
    String name = null;
    Toolbar toolbar;
    TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ressource = getArguments().getInt("ressource");
        form = inflater.inflate(/*R.layout.how_to_play_layout*/ressource,container,false);
        if(ressource == R.layout.inset_nick_name) {
                Button button = (Button) form.findViewById(R.id.button35);
                button.setOnClickListener(this);

        }else if(ressource == R.layout.old_scores_layout){
                Button button = (Button) form.findViewById(R.id.button40);
                button.setOnClickListener(this);
                Button button1 = (Button) form.findViewById(R.id.button41);
                button.setOnClickListener(this);
                name = getArguments().getString("name");

        }else if(ressource == R.layout.rank_layout) {
            HorizontalScrollView horScrollView = form.findViewById(R.id.scroll_view);
            LinearLayout linearLayout = form.findViewById(R.id.linearlayout);
            Drawable d;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                d = ResourcesCompat.getDrawable(getResources(),R.drawable.button_level,null);
            } else {
                d = getResources().getDrawable(R.drawable.button_level);
            }
            linearLayout.addView(createButton("total_score",0,d));
            for(int i=1;i<=40;i++){
                linearLayout.addView(createButton("" + i,i,d));
            }
            Toast.makeText(LevelsActivity.context,((Button)linearLayout.findViewById(0)).getText(),Toast.LENGTH_LONG).show();
        }

        return form;
    }

    private Button createButton(String text,int id,Drawable d){
        Button button = new Button(LevelsActivity.context);
        button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        button.setBackground(d);
        button.setId(id);
        new ConnexionGetScores(LevelsActivity.context,getResources(),PopMenu.this,0).execute("https://ayoubchabchoub.000webhostapp.com//getRank.php?num_level=0");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LevelsActivity.context,""+ v.getId() , Toast.LENGTH_LONG).show();
                new ConnexionGetScores(LevelsActivity.context,getResources(),PopMenu.this,v.getId()).execute("https://ayoubchabchoub.000webhostapp.com//getRank.php?num_level=" + v.getId() );
            }
        });
        button.setText(text);
        return button;

    }

    @Override
    public void onClick(View view) {
        Button button = (Button)view;
        if(button.getId() == R.id.button35){
            //Toast.makeText(LevelsActivity.context,"onclick",Toast.LENGTH_LONG).show();

            EditText nameText = (EditText)form.findViewById(R.id.editText);
        name = nameText.getText().toString();
        if(name.matches("[a-zA-Z0-9,_,-]+")) {
            new Connexion2Server(this).execute("https://ayoubchabchoub.000webhostapp.com/registration_php.php?id=" +
                    getArguments().getLong("id") + "&name=" + name + "&score=" + getArguments().getLong("score"));
        }else{
            TextView textView = (TextView)form.findViewById(R.id.textView9);
            textView.setText(getString(R.string.name_verification) );
            nameText.setText("");
        }
        }else if (button.getId() == R.id.button40){
            new Connexion2Server(this).execute("https://ayoubchabchoub.000webhostapp.com/getOldScores.php?id="
                    + getArguments().getBundle("bundle").getLong("id") + "&name=" + name);
            String s = "0=" + LevelsActivity.total_score;
            DBConnection db = new DBConnection(LevelsActivity.context);
            s += db.getAllRecord4Url();
            LevelsActivity.initializeFile(LevelsActivity.context,"update_server.txt",s);
            LevelsActivity.updateServer(LevelsActivity.context);
            dismiss();


        }else if (button.getId() == R.id.button41){
            FragmentManager manager = getFragmentManager();
            PopMenu popMenu = new PopMenu();
            Bundle b = getArguments();
            popMenu.setArguments(b.getBundle("bundle"));
            popMenu.show(manager, null);
            dismiss();
            new Connexion2Server(null).execute("https://ayoubchabchoub.000webhostapp.com/delete_old_scores.php?id="
                    + getArguments().getBundle("bundle").getLong("id"));
        }
    }



    /*class MyCustomAdapter extends BaseAdapter {

        ArrayList<Integer> items= new ArrayList<>();
        MyCustomAdapter(ArrayList<Integer> items,Context context){

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
    }*/

   /* class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        String result;
        @Override
        protected String  doInBackground(String... params) {

            InputStream isr = null;

            try{
                String URL=params[0];
                java.net.URL url = new URL( URL);
                URLConnection urlConnection = url.openConnection();
                isr  = new BufferedInputStream(urlConnection.getInputStream());


            }

            catch(Exception e){

                Log.e("log_tag", "Error in http connection " + e.toString());

            }

//convert response to string

            try{

                BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);

                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

                isr.close();

                result=sb.toString();

            }

            catch(Exception e){

                Log.e("log_tag", "Error  converting result " + e.toString());

            }

//parse json data


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(result == "1"){

            }else if(result == "2"){

            }else if (result == "0"){
                dismiss();
                LevelsActivity.initializeFile(LevelsActivity.context,"nickName",name);
                new
                Toast.makeText(LevelsActivity.context,getString(R.string.add2serverSucess),Toast.LENGTH_LONG).show();
            }

            TextView msgText = (TextView)form.findViewById(R.id.textView9);
            msgText.setText(result);


        }
    }*/

}
