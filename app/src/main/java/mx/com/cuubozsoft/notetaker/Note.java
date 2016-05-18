package mx.com.cuubozsoft.notetaker;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by carlos on 17/05/16.
 */
public class Note implements Serializable
{
    private String title;
    private String content;
    private Date date;

    public Note(String title, String content, Date date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Note() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
