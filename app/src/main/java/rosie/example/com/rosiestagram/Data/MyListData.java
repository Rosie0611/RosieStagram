package rosie.example.com.rosiestagram.Data;

import rosie.example.com.rosiestagram.R;

/**
 * Created by skyki on 2017-07-12.
 */

public class MyListData {

    public int iv_photo;
    public int ib_unLiked;
    public String tv_userId;
    public String tv_LikedCount;
    public String str_PostDate;
    String str_userId_post;
    String str_writtenPost;


    public MyListData(int iv_photo, int ib_unLiked, String tv_userId, String tv_LikedCount,String str_PostDate,String str_writtenPost,String str_userId_post) {

        this.iv_photo = iv_photo;
        this.ib_unLiked = ib_unLiked;
        this.tv_userId = tv_userId;
        this.tv_LikedCount = tv_LikedCount;
        this.str_PostDate = str_PostDate;
        this.str_userId_post =str_userId_post;
        this.str_writtenPost = str_writtenPost;

        if (this.iv_photo == R.drawable.photo01) {
            iv_photo = R.drawable.photo01;
        }else if (this.iv_photo == R.drawable.photo02) {
            iv_photo = R.drawable.photo02;
        }else if (this.iv_photo == R.drawable.photo03) {
            iv_photo = R.drawable.photo03;
        }

    }


}
