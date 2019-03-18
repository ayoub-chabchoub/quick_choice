package tn.example.ayoub.mygamev2app;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ayoub.mygamev2app.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ayoub on 19/01/2018.
 */

public class Connexion2Server extends AsyncTask<String, Void, String> {

    String result;

    PopMenu popMenu;
    public Connexion2Server(PopMenu context) {
     popMenu = context;

    }

    @Override
    protected String  doInBackground(String... params) {


        InputStream isr = null;

        try{
            String URL=params[0];
            java.net.URL url = new URL( URL);
            URLConnection urlConnection = url.openConnection();
            isr  = new BufferedInputStream(urlConnection.getInputStream());
            //Toast.makeText(LevelsActivity.context, "doInBackground begin",Toast.LENGTH_LONG).show();

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
        //Toast.makeText(LevelsActivity.context,result.substring(0,1)/* + " " +result.length()*/,Toast.LENGTH_LONG).show();
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

                try {

                    String s = "";

                    JSONArray jArray = new JSONArray(result);
                    int max_level = -1;
                    DBConnection db = new DBConnection(LevelsActivity.context);
                    for (int i = 0; i < jArray.length(); i++) {

                        JSONObject json = jArray.getJSONObject(i);
                        int num_level = json.getInt("num_level");
                        num_level++;
                        int score = json.getInt("score");
                        if (num_level == 0) {
                            if (score > LevelsActivity.total_score) {
                                LevelsActivity.total_score = score;
                                LevelsActivity.context.bundle.putLong("total_score", score);
                                ResultActivity.writeInFile(LevelsActivity.context, "score.txt", String.valueOf(score));
                                LevelsActivity.context.txtv.setText(String.valueOf(score));
                            }

                        } else {
                            if (num_level > max_level)
                                max_level = num_level;
                        }

                    }
                    if (max_level+1 > LevelsActivity.currentLevel) {
                        ResultActivity.writeInFile(LevelsActivity.context, "file.txt", String.valueOf(max_level+1));
                        LevelsActivity.currentLevel = max_level+1;
                    }

                    sendAllScores();
                    popMenu.dismiss();
                    if(popMenu.name != null)
                    LevelsActivity.initializeFile(LevelsActivity.context, "nick_name.txt", popMenu.name);
                    LevelsActivity.context.initGridView();


                /*if (s.length() > 0) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(), "user name or password isnot correct", Toast.LENGTH_LONG).show();
                  */
                } catch (JSONException e) {
                    // TODO: handle exception

                    Log.e("log_tag", "Error Parsing Data " + e.toString());
                }
            }
        }
    }


    public void sendAllScores(){
        DBConnection db = new DBConnection(LevelsActivity.context);
        String s = db.getAllRecord4Url().substring(1);
        LevelsActivity.initializeFile(LevelsActivity.context,"update_server.txt",s);
        LevelsActivity.updateServer(LevelsActivity.context);
    }
}