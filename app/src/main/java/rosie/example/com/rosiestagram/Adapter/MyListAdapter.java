package rosie.example.com.rosiestagram.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import rosie.example.com.rosiestagram.Activity.Main6Activity_Comment_Listview_FireBase;
import rosie.example.com.rosiestagram.Activity.MainActivity;
import rosie.example.com.rosiestagram.Data.MyListData;
import rosie.example.com.rosiestagram.R;

/**
 * Created by skyki on 2017-07-12.
 */

public class MyListAdapter extends BaseAdapter {
    MainActivity context;
    int iResID;
    ArrayList<MyListData> arrBindData;

    public MyListAdapter(MainActivity context, int iResID, ArrayList<MyListData> arrBindData) {
        this.context = context;
        this.iResID = iResID;
        this.arrBindData = arrBindData;
    }

    @Override
    public int getCount() {
        return arrBindData.size();
    }

    @Override
    public Object getItem(int position) {
        return arrBindData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        ImageView iv_profilePhoto;
        ImageView iv_photo;
        TextView tv_userId;
        TextView tv_LikedCount;
        TextView tv_PostDate;
        TextView tv_writtenPost;
        TextView tv_userId_post;
        ImageButton ib_setting;
        ImageButton ib_unLiked;
        ImageButton ib_share;
        ImageButton ib_comment;
        ImageButton ib_goGridview;

        public ViewHolder(ImageView iv_profilePhoto, ImageView iv_photo, TextView tv_userId, TextView tv_LikedCount,
                          TextView tv_PostDate, TextView tv_writtenPost, TextView tv_userId_post, ImageButton ib_setting,
                          ImageButton ib_unLiked, ImageButton ib_share, ImageButton ib_comment, ImageButton ib_goGridview) {
            this.iv_profilePhoto = iv_profilePhoto;
            this.iv_photo = iv_photo;
            this.tv_userId = tv_userId;
            this.tv_LikedCount = tv_LikedCount;
            this.tv_PostDate = tv_PostDate;
            this.tv_writtenPost = tv_writtenPost;
            this.tv_userId_post = tv_userId_post;
            this.ib_setting = ib_setting;
            this.ib_unLiked = ib_unLiked;
            this.ib_share = ib_share;
            this.ib_comment = ib_comment;
            this.ib_goGridview = ib_goGridview;
        }
    }

    ImageButton ib_comment;
    ImageButton ib_unLiked;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View inflated = convertView;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            inflated = inflater.inflate(iResID, null);

            ImageView iv_profilePhoto = (ImageView) inflated.findViewById(R.id.iv_profilePhoto);
            ImageView iv_photo = (ImageView) inflated.findViewById(R.id.iv_photo);
            TextView tv_userId = (TextView) inflated.findViewById(R.id.tv_userId);
            TextView tv_LikedCount = (TextView) inflated.findViewById(R.id.tv_LikedCount);
            TextView tv_PostDate = (TextView) inflated.findViewById(R.id.tv_PostDate);
            TextView tv_userid_post = (TextView) inflated.findViewById(R.id.tv_userid_post);
            TextView tv_writtenPost = (TextView) inflated.findViewById(R.id.tv_writtenPost);
            ImageButton ib_setting = (ImageButton) inflated.findViewById(R.id.ib_setting);
            ib_unLiked = (ImageButton) inflated.findViewById(R.id.ib_unLiked);
            ImageButton ib_share = (ImageButton) inflated.findViewById(R.id.ib_share);
            ImageButton ib_goback = (ImageButton) inflated.findViewById(R.id.ib_goGridview);

            ib_comment = (ImageButton) inflated.findViewById(R.id.ib_comment);
            ib_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent move = new Intent(context.getApplication(), Main6Activity_Comment_Listview_FireBase.class);
                    context.startActivity(move);
                }
            });

            ib_unLiked.setOnClickListener(new View.OnClickListener() {
                int countClick = 0;

                @Override
                public void onClick(View v) {
                    ++countClick;
                    if (countClick % 2 == 1) {
                        ib_unLiked.setImageResource(R.drawable.ic_favorite_black_48dp);
                    } else if (countClick % 2 == 0)
                        ib_unLiked.setImageResource(R.drawable.ic_favorite_border_black_48dp);
                }
            });
            holder = new ViewHolder(iv_profilePhoto, iv_photo, tv_userId, tv_LikedCount, tv_PostDate,tv_userid_post,
                    tv_writtenPost, ib_setting, ib_unLiked, ib_share, ib_comment, ib_goback);
            inflated.setTag(holder);
        } else {
            holder = (ViewHolder) inflated.getTag();
        }

        MyListData temp = arrBindData.get(position);
        if (temp.str_PostDate == null) {
            long date = System.currentTimeMillis();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM d yyyy", Locale.ENGLISH);
            String format = simpleDateFormat.format(new Date(date));
            temp.str_PostDate = format;
        }
        holder.tv_PostDate.setText(temp.str_PostDate);
        holder.tv_LikedCount.setText(temp.tv_LikedCount);
        holder.tv_userId.setText(temp.tv_userId);
        holder.ib_unLiked.setImageResource(temp.ib_unLiked);
        holder.iv_photo.setImageResource(temp.iv_photo);
        holder.ib_goGridview.setVisibility(View.GONE);
        Log.d("TAG", temp.str_PostDate + "");
        return inflated;
    }
}
