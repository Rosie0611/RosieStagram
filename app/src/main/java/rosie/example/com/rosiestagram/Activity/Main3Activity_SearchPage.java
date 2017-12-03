package rosie.example.com.rosiestagram.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;

import rosie.example.com.rosiestagram.R;

public class Main3Activity_SearchPage extends NMapActivity {
    private NMapView mMapView;// 지도 화면 View
    private final String CLIENT_ID = "HkZkrd9TnoIcQNNMSlvZ";// 애플리케이션 클라이언트 아이디 값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_search);
        mMapView = new NMapView(this);
//        setContentView(mMapView);
        mMapView.setClientId(CLIENT_ID); // 클라이언트 아이디 값 설정
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();

        ImageView iv_home = (ImageView)findViewById(R.id.iv_home);
        ImageView iv_addPhoto=(ImageView)findViewById(R.id.iv_addPhoto);
        ImageView iv_IveLiked =(ImageView)findViewById(R.id.iv_IveLiked);

        EditText editText = (EditText)findViewById(R.id.edit_search);
        Button bt_search = (Button)findViewById(R.id.bt_search) ;

        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(getApplication(),MainActivity.class);
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

        iv_IveLiked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(getApplication(),Main2Activity_Notifi_Listview.class);
                startActivity(move);
                finish();
            }
        });

    }
}
