package rosie.example.com.rosiestagram.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rosie.example.com.rosiestagram.Data.MyData;
import rosie.example.com.rosiestagram.R;

import static rosie.example.com.rosiestagram.Activity.Main7Activity_Album_SelectPhoto.PLEASE_TAKE_PHOTO;

public class Main4Activity_NewPostPage extends Activity {
    TextView tv_share;
    ImageView iv_newPhoto;
    EditText ed_post;

    private StorageReference mStorageRef;
    private DatabaseReference databaseReference;

    Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_post_format);

        ImageView iv_backPage = (ImageView) findViewById(R.id.iv_backPage);
        tv_share = (TextView) findViewById(R.id.tv_share);
        iv_newPhoto = (ImageView) findViewById(R.id.iv_newPhoto);
        ed_post = (EditText) findViewById(R.id.ed_post);



        Intent intent = getIntent();
        final Uri photoID = intent.getData();
        iv_newPhoto.setImageURI(photoID);

//        mDataRef = FirebaseDatabase.getInstance().getReference("/PhotoAlbum/");
//        mDataRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                MyData myData = dataSnapshot.getValue(MyData.class);
//                int setPHOTO =  myData.getPhoto_id();
//                iv_newPhoto.setImageResource(setPHOTO);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        //intent로받아온 사진 parsing & 이미지뷰에적용
//        Intent intent= getIntent();
//        byte[] image = intent.getByteArrayExtra("image");
////        filePath =  intent.getParcelableExtra("pics");
//        Log.d("TAG","사진넘어왔다");
//        final Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
//        iv_newPhoto.setImageBitmap(bitmap);


        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iv_newPhoto != null) {

                    // 선택한 사진  next버튼 누르는 순간 firebase에 업로드됨
                    databaseReference = FirebaseDatabase.getInstance().getReference("/Uploaded Image/").child("Image Info");
                    mStorageRef = FirebaseStorage.getInstance().getReference();
                    final String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
                    int count = 0;
                    StorageReference riversRef = mStorageRef.child(timeStamp + "_" + (count++));
                    riversRef.putFile(photoID).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @SuppressWarnings("VisibleForTests")
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //파일 업로드 성공시 토스트메시지
                            String post = ed_post.getText().toString();
                            MyData myData = new MyData(Integer.parseInt(taskSnapshot.getDownloadUrl().toString()), post);
                            String photoID = databaseReference.push().getKey();
                            databaseReference.child(photoID).setValue(myData);
                            Toast.makeText(Main4Activity_NewPostPage.this, "File Uploaded", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Main4Activity_NewPostPage.this, MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Main4Activity_NewPostPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                        double progress = ((100 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount());
//                                mProgressDialog.setMessage("Uploading Now");
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please Add your Photo", Toast.LENGTH_SHORT).show();
                }
//                mProgressDialog.setTitle("Uploading...");
//                String strPost = ed_post.getText().toString();
//
//                //파이어베이스 db에 저장
//                mDataRef = FirebaseDatabase.getInstance().getReference("/ImageComment/");
//                int photoID = iv_newPhoto.getId();
//                mDataRef.child("photoID/" + photoID).setValue(strPost);
//                Intent intent = new Intent(Main4Activity_NewPostPage.this,MainActivity.class);
//                startActivity(intent);
            }
        });
        iv_backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), Main7Activity_Album_SelectPhoto.class));
            }
        });
    }
}