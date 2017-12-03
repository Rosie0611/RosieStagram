package rosie.example.com.rosiestagram.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import rosie.example.com.rosiestagram.Activity.Main2Activity_Notifi_Listview;
import rosie.example.com.rosiestagram.Data.followingListData;
import rosie.example.com.rosiestagram.R;

/**
 * Created by skyki on 2017-07-14.
 */

public class followingListAdapter extends BaseAdapter {

    Main2Activity_Notifi_Listview context;
    int iResID;
    ArrayList<followingListData> arrBindData = new ArrayList<>();

    public followingListAdapter(Main2Activity_Notifi_Listview context, int iResID, ArrayList<followingListData> arrBindData) {
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

    public class TempHolder {
        ImageView iv_follower;
        TextView tv_Message;
        Button bt_Ignore;
        Button bt_Accept;
        Button bt_follow;
        Button bt_Following;

        public TempHolder(ImageView iv_follower, TextView tv_Message, Button bt_Accept, Button bt_Ignore, Button bt_follow, Button bt_Following) {
            this.iv_follower = iv_follower;
            this.tv_Message = tv_Message;
            this.bt_Accept = bt_Accept;
            this.bt_Ignore = bt_Ignore;
            this.bt_follow = bt_follow;
            this.bt_Following = bt_Following;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View inflated = convertView;
        final TempHolder holder;
        final String messageToString;
        String setMessage;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflated = inflater.inflate(R.layout.listview_items_request_following, null);

            ImageView iv_follower = (ImageView) inflated.findViewById(R.id.iv_profilePhoto);
            TextView tv_Message = (TextView) inflated.findViewById(R.id.tv_requestFollowingYou);
            Button bt_Ignore = (Button) inflated.findViewById(R.id.bt_Ignore);
            Button bt_Accept = (Button) inflated.findViewById(R.id.bt_Accept);
            Button bt_Follow = (Button) inflated.findViewById(R.id.bt_follow);
            Button bt_Following = (Button) inflated.findViewById(R.id.bt_following);

            holder = new TempHolder(iv_follower, tv_Message, bt_Accept, bt_Ignore, bt_Follow, bt_Following);
            inflated.setTag(holder);
        } else {
            holder = (TempHolder) inflated.getTag();
        }
        followingListData temp = arrBindData.get(position);
        messageToString = temp.strMessage.toString();
        setMessage = messageToString + " request following you";
        holder.iv_follower.setImageResource(temp.iFollowerPhoto);
        holder.tv_Message.setText(setMessage);

        holder.bt_Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.bt_Accept.setVisibility(View.GONE);
                holder.bt_Ignore.setVisibility(View.GONE);
                holder.bt_follow.setVisibility(View.VISIBLE);
                holder.tv_Message.setText(messageToString + " started following you");
            }
        });

        holder.bt_Ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.bt_Accept.setVisibility(View.GONE);
                holder.bt_Ignore.setVisibility(View.GONE);
                holder.bt_follow.setVisibility(View.VISIBLE);
                holder.tv_Message.setText("ignored following request from /n" + messageToString);
            }
        });

        holder.bt_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.bt_Accept.setVisibility(View.GONE);
                holder.bt_Ignore.setVisibility(View.GONE);
                holder.bt_follow.setVisibility(View.GONE);
                holder.bt_Following.setVisibility(View.VISIBLE);
                holder.tv_Message.setText("You started follow " + messageToString);
            }
        });

        return inflated;

    }
}
