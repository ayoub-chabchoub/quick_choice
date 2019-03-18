package tn.example.ayoub.mygamev2app;

import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayoub.mygamev2app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.res.Resources;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by ayoub on 02/02/2018.
 */

public class ConnexionGetScores extends AsyncTask<String, Void, String> {


    String result;
    PopMenu popMenu;
    Resources resources;
    Context context;
    Boolean find= false;
    String myname;
    int level;

    public ConnexionGetScores(Context context,Resources resources,PopMenu popMenu,int level) {
        this.popMenu = popMenu;
        this.resources=resources;
        this.context=context;
        this.level = level;
    }


    @Override
    protected String  doInBackground(String... params) {


        InputStream isr = null;

        try{
            String URL=params[0];
            java.net.URL url = new URL( URL);
            URLConnection urlConnection = url.openConnection();
            isr  = new BufferedInputStream(urlConnection.getInputStream());


        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(LevelsActivity.context, "prob 1",Toast.LENGTH_LONG).show();
            Log.e("log_tag", "Error in http connection " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(LevelsActivity.context, "prob 1",Toast.LENGTH_LONG).show();
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
            //Toast.makeText(LevelsActivity.context, "doInBackground done",Toast.LENGTH_LONG).show();

        } catch (UnsupportedEncodingException e) {
            Toast.makeText(LevelsActivity.context, "prob 2",Toast.LENGTH_LONG).show();
            Log.e("log_tag", "Error  converting result " + e.toString());
            e.printStackTrace();

        } catch (IOException e) {
            Toast.makeText(LevelsActivity.context, "prob 2",Toast.LENGTH_LONG).show();
            Log.e("log_tag", "Error  converting result " + e.toString());
            e.printStackTrace();
        }

//parse json data


        return null;
    }

    protected void onPostExecute(String  result2){
        /*
        if (result.length()>0) {
            if (result.substring(0,1).equals("0")) {
                Toast.makeText(LevelsActivity.context,"c0",Toast.LENGTH_LONG).show();
                FragmentManager manager = LevelsActivity.context.getFragmentManager();
                PopMenu popMenu1 = new PopMenu();
                Bundle b = new Bundle();
                b.putInt("ressource", R.layout.old_scores_layout);
                b.putBundle("bundle",popMenu.getArguments());
                b.putString("name",popMenu.name);
                popMenu1.setArguments(b);
                popMenu1.show(manager, null);
                popMenu.dismiss();

            } else if (result.substring(0,1) == "1") {
                TextView msgText = (TextView) popMenu.form.findViewById(R.id.textView9);
                msgText.setText("this name already exists,please choose an other.");
            } else if (result.substring(0,1) == "2") {
                sendAllScores();
                LevelsActivity.initializeFile(LevelsActivity.context, "nick_name.txt", popMenu.name);
            } else {
                Toast.makeText(LevelsActivity.context,"celse",Toast.LENGTH_LONG).show();
*/
                try {

                    String s = "";

                    JSONArray jArray = new JSONArray(result);

                    final ArrayList<ListItem> items=new  ArrayList<ListItem> ();
                    for (int i = 0; i < jArray.length(); i++) {
                        //a verifier
                        JSONObject json = jArray.getJSONObject(i);
                        items.add(new ListItem((byte)(i+1),json.getString("nick_name"),0,json.getInt("score")));

                    }
                    myname = firstActivity.getDataFromFile(LevelsActivity.context,"nick_name.txt");
                    final ListScoreAdapter myadpter= new ListScoreAdapter(items);
                    ListView ls=(ListView)popMenu.form.findViewById(R.id.list_view);
                    ls.setAdapter(myadpter);
                } catch (JSONException e) {
                    // TODO: handle exception

                    Log.e("log_tag", "Error Parsing Data " + e.toString());
                }
            }

            String getScore(){
                if(level==0){
                    return String.valueOf(LevelsActivity.total_score);
                }else{
                    DBConnection dbConnection = new DBConnection(context);
                    return String.valueOf(dbConnection.exist(level+1,0));
                }

            }

    class ListScoreAdapter extends BaseAdapter {

        ArrayList<ListItem> items = new ArrayList<ListItem>();
        public ListScoreAdapter( ArrayList<ListItem> items) {
            this.items=items;

        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public String getItem(int position) {
            return items.get(position).name;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater linflater =LevelsActivity.context.getLayoutInflater();
            View view1=linflater.inflate(R.layout.rank_item, null);
            if(position == 0){
                ImageView imageView = (ImageView)view1.findViewById(R.id.imageView2);
                imageView.setBackgroundResource(R.drawable.gold);
            }else if(position == 1){
                ImageView imageView = (ImageView)view1.findViewById(R.id.imageView2);
                imageView.setBackgroundResource(R.drawable.silver);
            }else if(position == 2){
                ImageView imageView = (ImageView)view1.findViewById(R.id.imageView2);
                imageView.setBackgroundResource(R.drawable.bronze);
            }
            TextView txtname =(TextView) view1.findViewById(R.id.textView10);
            TextView txtscore =(TextView) view1.findViewById(R.id.textView12);
            if(!find && !items.get(position).name.equals(myname)){
                txtname.setTextColor(resources.getColor(R.color.red_button));
                txtname.setText((position + 1) + "." + items.get(position).name);
                txtscore.setText(String.valueOf(items.get(position).score));
                find=true;
            }else if(!find && items.get(position).name.equals("you.")){
                find=true;
                txtname.setTextColor(resources.getColor(R.color.red_button));
                txtname.setText(items.get(position).score + "." + items.get(position).name);
                if(getScore().equals("0"))
                    return null;
                else
                txtscore.setText(getScore());

            }else{
                txtname.setText((position + 1) + "." + items.get(position).name);
                txtscore.setText(String.valueOf(items.get(position).score));
            }
            return view1;

        }
    }
}
