package rosie.example.com.rosiestagram.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import rosie.example.com.rosiestagram.Activity.Main2Activity_Notifi_Listview;
import rosie.example.com.rosiestagram.Data.LikedListData;
import rosie.example.com.rosiestagram.R;

/**
 * Created by skyki on 2017-07-14.
 */

public class LikedListAdapter extends BaseAdapter {

    Main2Activity_Notifi_Listview context;
    int iResId;
    ArrayList<LikedListData> arrbindedData = new ArrayList<>();

    public LikedListAdapter(Main2Activity_Notifi_Listview context, int iResId, ArrayList<LikedListData> arrbindedData) {
        this.context = context;
        this.iResId = iResId;
        this.arrbindedData = arrbindedData;
    }

    @Override
    public int getCount() {
        return arrbindedData.size();
    }

    @Override
    public Object getItem(int position) {
        return arrbindedData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class TempHolder {
        ImageView iv_heart;
        TextView tv_OthersLikedMyPost;
        ImageButton ib_IveLikedPhoto;

        public TempHolder(ImageView iv_heart, TextView tv_OthersLikedMyPost, ImageButton ib_IveLikedPhoto) {
            this.iv_heart = iv_heart;
            this.tv_OthersLikedMyPost = tv_OthersLikedMyPost;
            this.ib_IveLikedPhoto = ib_IveLikedPhoto;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View inflated = convertView;
        TempHolder holder = null;

        if (inflated == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflated = inflater.inflate(R.layout.listview_items_liked, null);

            ImageView iv_heart = (ImageView) inflated.findViewById(R.id.iv_heart);
            TextView tv_OthersLikedMyPost = (TextView) inflated.findViewById(R.id.tv_OthersLikedMyPost);
            ImageButton ib_IveLikedPhoto = (ImageButton) inflated.findViewById(R.id.ib_beenLikedPhoto);
            // Log.d("TAG",i1Case+" 이 출력되었습니다.");
            holder = new TempHolder(iv_heart, tv_OthersLikedMyPost, ib_IveLikedPhoto);
            inflated.setTag(holder);

        } else {
            holder = (TempHolder) inflated.getTag();
        }

        LikedListData temp = arrbindedData.get(position);
        Random rd = new Random();
        int iRd = rd.nextInt(10000);
        switch (temp.iExplain) {
            case "1" :
                String Like = iRd+" others Liked your Post";
                temp.iExplain=Like;
                break;
            case "2" :
                String comment =iRd+" others Commented on your Post";
                temp.iExplain=comment;
                break;
        }
        holder.ib_IveLikedPhoto.setImageResource(temp.iPhotoId);
        holder.tv_OthersLikedMyPost.setText(temp.iExplain);
//        holder.iv_heart.setImageResource(R.drawable.ic_create_black_48dp);

        return inflated;
    }
}
