package rosie.example.com.rosiestagram.Data;

//import io.realm.RealmObject;

import java.text.SimpleDateFormat;

/**
 * Created by skyki on 2017-07-28.
 */

public class MyData_Comment {
    public String id, comment, time;


    public MyData_Comment() {
    }

    public MyData_Comment(String id, String comment) {
        this.id = id;
        this.comment = comment;
        SimpleDateFormat sd = new SimpleDateFormat("a h:mm");
        this.time = sd.format(System.currentTimeMillis());
    }

    public String getTime() { return time; }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
