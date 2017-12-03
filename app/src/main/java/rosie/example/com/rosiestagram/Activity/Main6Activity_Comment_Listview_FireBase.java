package rosie.example.com.rosiestagram.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import rosie.example.com.rosiestagram.Adapter.MyBaseAdapter_Comment_FireBase;
import rosie.example.com.rosiestagram.Data.MyData;
import rosie.example.com.rosiestagram.Data.MyData_Comment;
import rosie.example.com.rosiestagram.R;

public class Main6Activity_Comment_Listview_FireBase extends AppCompatActivity {

    ListView listView;
    Button bt_deleteComment;
    ImageButton ib_goback;
    EditText ed_userid, ed_comment;

    MyBaseAdapter_Comment_FireBase adapter;
    ArrayList<MyData_Comment> arrData = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference MyRef;

    ProgressDialog progressDialog;

    int countClick = 0;
    int igetImgID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutinflate_comment_listview);
        listView = (ListView) findViewById(R.id.listview);
        ed_userid = (EditText) findViewById(R.id.ed_userid);
        ed_comment = (EditText) findViewById(R.id.ed_comment);
        bt_deleteComment = (Button) findViewById(R.id.bt_deleteComment);
        ib_goback = (ImageButton) findViewById(R.id.ib_goGridview);


        Intent intent = getIntent();
        //intent로 받아온 선택한 이미지 고유 id값
        igetImgID = intent.getIntExtra("UniqueValue", View.NO_ID);

        database = FirebaseDatabase.getInstance();
        MyRef = database.getReference().child("/Comment/PhotoID/");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");

        MyRef.child(igetImgID + "").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MyData_Comment myData_comment = snapshot.getValue(MyData_Comment.class);
                        arrData.add(myData_comment);
                        adapter = new MyBaseAdapter_Comment_FireBase(Main6Activity_Comment_Listview_FireBase.this, R.layout.layoutinflate_items_comment, arrData);
                        listView.setAdapter(adapter);
                    }
                }else{
                    adapter = new MyBaseAdapter_Comment_FireBase(Main6Activity_Comment_Listview_FireBase.this, R.layout.layoutinflate_items_comment, arrData);
                    listView.setAdapter(adapter);
                    }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ib_goback.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main6Activity_Comment_Listview_FireBase.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onBtnCommentLike(View v) {
        // DB에 저장시키는 기능 추가
        ++countClick;
        ImageButton ib_likeComment = (ImageButton) v.findViewById(R.id.ib_likeComment);
        if (countClick % 2 == 1) {
            ib_likeComment.setImageResource(R.drawable.ic_favorite_black_48dp);
        } else if (countClick % 2 == 0)
            ib_likeComment.setImageResource(R.drawable.ic_favorite_border_black_48dp);
    }

    public void onBtnPost(View view) {
        if( ed_userid != null && ed_comment!= null){

            SimpleDateFormat sdf = new SimpleDateFormat("a MM월 dd일 hh:mm:ss");
            long time = System.currentTimeMillis();
            String curTime = sdf.format(new Date(time));
            String strUserId = ed_userid.getText().toString().trim();
            String strComment = ed_comment.getText().toString().trim();
            ed_userid.setText("");
            ed_comment.setText("");
            MyData_Comment comment = new MyData_Comment(strUserId, strComment);
            adapter.notifyDataSetChanged();
            MyRef.child(igetImgID + "").child(strUserId).setValue(comment);
        }else if(ed_comment == null) {
            Toast.makeText(Main6Activity_Comment_Listview_FireBase.this, "Please Write a comment about this photo :) ", Toast.LENGTH_SHORT).show();

        } else if(ed_userid == null) {
            Toast.makeText(Main6Activity_Comment_Listview_FireBase.this, "Please Put your UserID ", Toast.LENGTH_SHORT).show();

        }
        else{
            return;

        }
    }

}