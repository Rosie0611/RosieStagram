package rosie.example.com.rosiestagram.Data;

/**
 * Created by skyki on 2017-07-12.
 */

public class MyData {
    public int photo_id;
    public boolean bChecked = false;
    public String post;

    public MyData(String s) {
    }

    public MyData(int photo_id, String post) {
        this.photo_id = photo_id;
        this.post = post;
    }

    public MyData() {

    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
