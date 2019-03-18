package tn.example.ayoub.mygamev2app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by AYOUB on 09/09/2017.
 */

    public class DBConnection extends SQLiteOpenHelper {

    public static final String DbName ="bestscoces.db";
    public static final int VERSION = 1;
    Context context;
    public  DBConnection(Context context){
        super(context,DbName,null,VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `scores` (`num_level` INTEGER,`best_score` INTEGER,PRIMARY KEY(`num_level`));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table IF EXISTS scores");
        onCreate(sqLiteDatabase);

    }

    public int update(int numLevel,int score){
        SQLiteDatabase db  = this.getWritableDatabase();
        int x = exist(numLevel,score);
        if(x==0){
            db.execSQL("update scores set best_score=" + score +" where num_level=" + numLevel);
            addToFile(numLevel,score);
        }else if(x==-1){
            db.execSQL("insert into scores(num_level,best_score) values (" + numLevel + "," + score + ")");
            addToFile(numLevel,score);
        }

        return x;
    }

    public void addToFile(int numLevel,int score){
        FileInputStream fis = null;
        String s = "";
        try {
            fis = context.openFileInput("update_server.txt");
            int i = 0;

            while ((i = fis.read()) != -1) {
                s += String.valueOf((char) i);
            }
            if(s.length()==0){
                s = "0=" + LevelsActivity.total_score;
            }
            s += "&" + (numLevel-1) + "=" + score;
            LevelsActivity.initializeFile(context,"update_server.txt",s);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String getAllRecord4Url(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from scores",null);
        String s = "";
        if(res.getCount()>0) {
            res.moveToFirst();
            int numLevel = res.getColumnIndex("num_level");
            int best_score = res.getColumnIndex("best_score");
            while (!res.isAfterLast()){
                s = s + "&" + (res.getInt(numLevel)-1) + "=" + res.getInt(best_score);
                res.moveToNext();
            }
        }
        return s;
    }

    public int exist(int numLevel,int score){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from scores where num_level=" + numLevel,null);
        res.moveToFirst();
        if(res.getCount()==1) {
            int bestScore = res.getInt(res.getColumnIndex("best_score"));
            if (bestScore < score)
                return 0;
            else
                return bestScore;
        }
        else if(res.getCount()==0)
            return -1;
        else
            Toast.makeText(context,"problem",Toast.LENGTH_LONG).show();
        return -2;

    }
}
