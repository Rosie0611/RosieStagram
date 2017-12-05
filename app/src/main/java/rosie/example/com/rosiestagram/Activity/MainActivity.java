package rosie.example.com.rosiestagram.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Random;

import rosie.example.com.rosiestagram.Adapter.MyBaseAdapter;
import rosie.example.com.rosiestagram.Adapter.MyListAdapter;
import rosie.example.com.rosiestagram.Data.MyData;
import rosie.example.com.rosiestagram.Data.MyListData;
import rosie.example.com.rosiestagram.R;

public class MainActivity extends AppCompatActivity {

    public final static String USERID = "_2.60";
    GridView gridView;
    ListView listView;
    ArrayList<MyData> data = new ArrayList<>();
    ArrayList<MyListData> listData;
    MyBaseAdapter myBaseAdapter;
    MyListAdapter myListAdapter;
    private DatabaseReference dbRef;
    private StorageReference mStorageRef;

//    Fragment_gridview01 fragment_gridview01;
//    Fragment_listview01 fragment_listview01;

//    FragmentManager fragmentManager;
//    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_instagram);

        ImageButton ib_profilePhoto = (ImageButton) findViewById(R.id.ib_profilePhoto);
        ImageView iv_gridAgain = (ImageView) findViewById(R.id.iv_gridAgain);
        ImageView iv_list = (ImageView) findViewById(R.id.ib_listView);
        ImageView iv_IveLiked = (ImageView) findViewById(R.id.iv_IveLiked);
        ImageView iv_search = (ImageView) findViewById(R.id.iv_search);
        ImageView iv_addPhoto = (ImageView) findViewById(R.id.iv_addPhoto);
        ImageView iv_takePic = (ImageView) findViewById(R.id.iv_takePic);

//        fragmentManager = getSupportFragmentManager();
//        fragment_gridview01 = new Fragment_gridview01();
//        fragment_listview01 = new Fragment_listview01();

        //첫화면에 fragment_gridview01 출력하겠다.
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, fragment_gridview01);
//        fragmentTransaction.commit();

        gridView = (GridView) findViewById(R.id.gridview);
        listView = (ListView) findViewById(R.id.listview_secondpage);
        data = new ArrayList<>();

//        Intent intent = getIntent();
//        Uri uri = Uri.parse(intent.getStringExtra("newImage"));
//        String str_post = intent.getStringExtra("post");
//        data.add(new MyData(uri,str_post));
        //작성당일날짜받아놓기 ->DB에 사진,URI,USERID, 게시글,날짜추가

        data.add(new MyData(R.drawable.flower2,"앙뇽"));
        data.add(new MyData(R.drawable.flower3,"꽃2"));
        data.add(new MyData(R.drawable.flower4,"꽃3"));
        data.add(new MyData(R.drawable.flower5,"꽃4"));
        data.add(new MyData(R.drawable.flower6,"꽃5"));
        data.add(new MyData(R.drawable.flower7,"꽃6"));
        data.add(new MyData(R.drawable.flower8,"꽃7"));
        data.add(new MyData(R.drawable.flower9,""));
        data.add(new MyData(R.drawable.flower10,""));


        myBaseAdapter = new MyBaseAdapter(this, R.layout.gridview_instagram, data);
        gridView.setAdapter(myBaseAdapter);

                dbRef = FirebaseDatabase.getInstance().getReference("/Uploaded Image/");
        dbRef.child("Image Info").addValueEventListener(new ValueEventListener() {
                                                              @Override
                                                              public void onDataChange(DataSnapshot dataSnapshot) {
                                                                  if (dataSnapshot != null) {
                                                                      for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                          MyData myData = dataSnapshot1.getValue(MyData.class);
                                                                          data.add(myData);
                                                                      }
                                                                      myBaseAdapter = new MyBaseAdapter(MainActivity.this, R.layout.gridview_instagram, data);
                                                                      gridView.setAdapter(myBaseAdapter);
                                                                  } else{
                                                                      myBaseAdapter = new MyBaseAdapter(MainActivity.this, R.layout.gridview_instagram, data);
                                                                      gridView.setAdapter(myBaseAdapter);
                                                                  }
                                                              }

                                                              @Override
                                                              public void onCancelled(DatabaseError databaseError) {

                                                              }
                                                          });

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(MainActivity.this, position + 1 + "번째 사진이 선택되었습니다.", Toast.LENGTH_LONG).show();
                        data.get(position).bChecked = !data.get(position).bChecked;
                        myBaseAdapter.notifyDataSetChanged();
                    }
                });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MainActivity_photoCloseup.class);
                MyData myData = data.get(position);
                intent.putExtra("grid_image", myData.photo_id);
                //사진마다의 게시글
                intent.putExtra("post", myData.post);
                startActivity(intent);
                return true;
            }
        });

        // Count Method
        PostCount();
        Follower();
        Following();

        ib_profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Follower Access Only", Toast.LENGTH_LONG).show();
            }
        });

        iv_IveLiked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(getApplication(), Main2Activity_Notifi_Listview.class);
                startActivity(move);
            }
        });

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(getApplication(), Main3Activity_SearchPage.class);
                startActivity(move);

            }
        });

        iv_gridAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fragment사용
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment_gridview01);
//                fragmentTransaction.commit();

//화면전환방식 -처음 적용했던 방식
                listView.setVisibility(View.GONE);
                listView.removeAllViewsInLayout();
                gridView.setVisibility(View.VISIBLE);
                gridView.setAdapter(myBaseAdapter);
            }
        });

        iv_addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), Main7Activity_Album_SelectPhoto.class);
                startActivity(intent);
            }
        });


        //ListView 호출
        iv_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment_listview01);
//                fragmentTransaction.commit();
//화면전환방식 - 처음 적용했던 방식
                listData = new ArrayList<>();

                gridView.setVisibility(View.GONE);
                gridView.removeAllViewsInLayout();
                listView = (ListView) findViewById(R.id.listview_secondpage);
                listView.setVisibility(View.VISIBLE);

//                dbRef = FirebaseDatabase.getInstance().getReference("images/");
//                dbRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            MyListData myListData = snapshot.getValue(MyListData.class);
//                            listData.add(myListData);
//                        }
//                        myListAdapter = new MyListAdapter(MainActivity.this, R.layout.listview_items, listData);
//
//                        myBaseAdapter.notifyDataSetChanged();
//                        listView.setAdapter(myListAdapter);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });

                listData.add(new MyListData(R.drawable.photo01, R.drawable.ic_favorite_border_black_48dp, USERID, "Liked by 34523 others", null,USERID,""));
                listData.add(new MyListData(R.drawable.photo02, R.drawable.ic_favorite_border_black_48dp, USERID, "Liked by 66234 others", null,USERID,""));
                listData.add(new MyListData(R.drawable.photo03, R.drawable.ic_favorite_black_48dp,USERID, "Liked by 16453 others", null,USERID,""));
                myListAdapter = new MyListAdapter(MainActivity.this, R.layout.listview_items, listData);

                myBaseAdapter.notifyDataSetChanged();
                listView.setAdapter(myListAdapter);


            }
        });

        iv_takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void PostCount() {
        int count = myBaseAdapter.getCount();
        TextView tv_postCount = (TextView) findViewById(R.id.tv_postCount);
        tv_postCount.setText(count + "");
    }

    public void Follower() {
        Random random = new Random();
        int iFollower = random.nextInt(10000);
        TextView tv_follower = (TextView) findViewById(R.id.tv_follower);
        tv_follower.setText("" + iFollower);
    }

    public void Following() {
        Random random = new Random();
        int iFollower = random.nextInt(10000);
        TextView tv_follower = (TextView) findViewById(R.id.tv_following);
        tv_follower.setText("" + iFollower);
    }
}
