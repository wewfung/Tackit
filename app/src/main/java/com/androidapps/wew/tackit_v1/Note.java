package com.androidapps.wew.tackit_v1;


/**
 * Created by William on 6/14/2015.
 */
public class Note {
    private String title = "Title";
    private String details = "(some text)";
    private String time;

    public Note(String title, String details, String time){
        super();
        if(title.equals("")) this.title = "(No Title)";
        else this.title = title;
        if(details.equals("")) this.details = "(no details)";
        else this.details = details;
        this.time = time;
    }

    public String getTitle(){
        return title;
    }

    public String getDetails(){
        return details;
    }

    public String getTime(){
        return time;
    }
}
