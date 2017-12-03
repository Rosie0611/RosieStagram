package rosie.example.com.rosiestagram.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import rosie.example.com.rosiestagram.Data.MyData;
import rosie.example.com.rosiestagram.Data.MyData_Comment;
import rosie.example.com.rosiestagram.R;


public class MainActivity_photoCloseup extends AppCompatActivity {
    ImageView imageView;
    ImageButton ib_unliked, ib_comment, ib_goback;
    TextView tv_post, tv_userid_post;
    MyData_Comment commentData;
    int countClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_items);
        ib_unliked = (ImageButton) findViewById(R.id.ib_unLiked);
        ib_comment = (ImageButton) findViewById(R.id.ib_comment);
        ib_goback = (ImageButton) findViewById(R.id.ib_goGridview);
        tv_post = (TextView) findViewById(R.id.tv_writtenPost);
        tv_userid_post = (TextView) findViewById(R.id.tv_userid_post);
        imageView = (ImageView) findViewById(R.id.iv_photo);


        //intent로받아온 이미지,게시글배치
        Intent intent = getIntent();
        final int img = intent.getIntExtra("grid_image", View.NO_ID);
        String str_ed_post = intent.getStringExtra("post");

        //MainActivity에서 setOnItemClickListener 로 받아온 ID 값을 Comment에 넘겨주기 위해 getLongExtra로 수신,
        //ib_comment클릭시 intent로 Main6Activity로 넘겨준다.
        tv_post.setText(str_ed_post);
        imageView.setImageResource(img);

        ib_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(getApplication(), MainActivity.class);
                startActivity(move);
            }
        });

        ib_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(getApplication(), Main6Activity_Comment_Listview_FireBase.class);
                move.putExtra("UniqueValue", img);
                startActivity(move);
            }
        });

        ib_unliked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++countClick;
                if (countClick % 2 == 1) {
                    Log.d("TAG", "onClick:"+countClick);
                    ib_unliked.setImageResource(R.drawable.ic_favorite_black_48dp);
                } else if (countClick % 2 == 0)
                    ib_unliked.setImageResource(R.drawable.ic_favorite_border_black_48dp);
                Log.d("TAG2", "onClick:"+countClick);

            }
        });

        tv_userid_post.setText(MainActivity.USERID);

    }
}
