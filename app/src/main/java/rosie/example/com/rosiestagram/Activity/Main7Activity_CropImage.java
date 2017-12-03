package rosie.example.com.rosiestagram.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import rosie.example.com.rosiestagram.R;

public class Main7Activity_CropImage extends AppCompatActivity {

    Button bt_postPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_crop);
        ImageView iv_cropImage = (ImageView) findViewById(R.id.iv_cropImage);
        bt_postPage = (Button) findViewById(R.id.bt_MovePostPage);


        bt_postPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLastPage = new Intent(Main7Activity_CropImage.this, Main4Activity_NewPostPage.class);
                startActivity(intentToLastPage);
            }
        });
    }

}