package tn.example.ayoub.mygamev2app;

/**
 * Created by ayoub on 02/02/2018.
 */

public class ListItem {
    byte rank;
    String name;
    int img;
    int score;

    public ListItem(byte rank,String name, int img,int score) {
        this.rank = rank;
        this.name = name;
        if(img != 0)
        this.img = img;
        this.score=score;


    }


}
