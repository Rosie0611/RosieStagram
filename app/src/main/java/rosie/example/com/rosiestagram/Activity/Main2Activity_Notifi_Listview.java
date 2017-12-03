package rosie.example.com.rosiestagram.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import rosie.example.com.rosiestagram.Adapter.LikedListAdapter;
import rosie.example.com.rosiestagram.Adapter.followingListAdapter;
import rosie.example.com.rosiestagram.Data.LikedListData;
import rosie.example.com.rosiestagram.Data.followingListData;
import rosie.example.com.rosiestagram.R;

public class Main2Activity_Notifi_Listview extends AppCompatActivity {

    ListView listView = null;
    LikedListAdapter listAdapter;
    followingListAdapter followingAdapter;
    ArrayList<LikedListData> data = new ArrayList<>();
    ArrayList<followingListData> followingData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_notification);

        //1 = like 2 = comment
        data.add(new LikedListData("1", R.drawable.flower5));
        data.add(new LikedListData("2", R.drawable.flower5));
        data.add(new LikedListData("1", R.drawable.flower8));
        data.add(new LikedListData("2", R.drawable.flower8));
        data.add(new LikedListData("1", R.drawable.photo03));
        data.add(new LikedListData("1", R.drawable.photo04));


        listView = (ListView) findViewById(R.id.listview_Notification);
        final Button bt_youNotification = (Button) findViewById(R.id.bt_youNotification);
        final Button bt_following = (Button) findViewById(R.id.bt_following);
        ImageView iv_home = (ImageView) findViewById(R.id.iv_home);
        ImageView iv_search = (ImageView) findViewById(R.id.iv_search);
        ImageView iv_addPhoto=(ImageView)findViewById(R.id.iv_addPhoto);

        listAdapter = new LikedListAdapter(this, R.id.listview_Notification, data);
        listView.setAdapter(listAdapter);

        bt_youNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bt_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.removeAllViewsInLayout();
                bt_youNotification.setBackgroundColor(getResources().getColor(R.color.lightpink, null));
                bt_following.setBackgroundColor(View.GONE);

                followingData.add(new followingListData(R.drawable.flower8, "rosie_01"));
                followingData.add(new followingListData(R.drawable.flower7, "rosie_02"));
                followingData.add(new followingListData(R.drawable.flower6, "rosie_03"));
                followingData.add(new followingListData(R.drawable.flower5, "rosie_04"));
                followingData.add(new followingListData(R.drawable.flower4, "rosie_05"));
                followingData.add(new followingListData(R.drawable.flower3, "rosie_06"));


                followingAdapter = new followingListAdapter(Main2Activity_Notifi_Listview.this, R.id.listview_Notification, followingData);
                listView.setAdapter(followingAdapter);

            }
        });
        bt_youNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bt_following.setBackgroundColor(getResources().getColor(R.color.lightpink, null));
                bt_youNotification.setBackgroundColor(View.GONE);
                listView.removeAllViewsInLayout();
                listView.setAdapter(listAdapter);
            }
        });

        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(getApplication(), MainActivity.class);
                startActivity(move);
                finish();
            }
        });

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(getApplication(), MainActivity.class);
                startActivity(move);
                finish();
            }
        });

        iv_addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(getApplication(),Main4Activity_NewPostPage.class);
                startActivity(move);
                finish();
            }
        });

    }
}
