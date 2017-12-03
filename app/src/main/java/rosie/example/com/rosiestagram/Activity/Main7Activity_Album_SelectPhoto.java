package rosie.example.com.rosiestagram.Activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rosie.example.com.rosiestagram.Data.MyData;
import rosie.example.com.rosiestagram.R;

public class Main7Activity_Album_SelectPhoto extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 1; //카메라 촬영으로 사진 가져오기
    private static final int PICK_FROM_ALBUM = 2; //앨범에서 사진 가져오기
    private static final int CROP_FROM_CAMERA = 3; //가져온 사진을 자르기 위한 변수
    private static final int PICK_IMAGE_REQUEST = 1001;
    public static final int PLEASE_TAKE_PHOTO = 1002;
    private static final int PLEASE_BRING_PHOTO = 1003;

    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}; //권한 설정 변수
    private static final int MULTIPLE_PERMISSIONS = 101; //권한 동의 여부 문의 후 CallBack 함수에 쓰일 변수

    Button bt_goHome, bt_cropImage, bt_takePhoto, bt_Album;
    ImageView iv_showPics;
    private String mCurrentPhotoPath;
    Uri PhotoUri;
    Uri filePath;

   private StorageReference mStorageRef;
   private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);

        bt_goHome = (Button) findViewById(R.id.bt_goHome);
        bt_cropImage = (Button) findViewById(R.id.bt_cropImage);
        bt_takePhoto = (Button) findViewById(R.id.bt_takepic);
        bt_Album = (Button) findViewById(R.id.bt_Album);
        iv_showPics = (ImageView) findViewById(R.id.iv_showPics);
        checkPermissions(); //권한 묻기
        mStorageRef = FirebaseStorage.getInstance().getReference("/PhotoAlbum/");
        //MainActivity로 돌아가기
        bt_goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main7Activity_Album_SelectPhoto.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //선택한사진가지고 다음Activity
        bt_cropImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(getApplicationContext(), Main4Activity_NewPostPage.class);
                    File file = createImageFile();
                    intent.putExtra("FileName",file.toURI());
                    startActivity(intent);
                } catch (IOException e) {
                    Toast.makeText(Main7Activity_Album_SelectPhoto.this, "fail to move img", Toast.LENGTH_SHORT).show();
                }

            }

//            Intent intent = new Intent(getApplicationContext(), Main4Activity_NewPostPage.class);
//                Bitmap sendBitmap = BitmapFactory.decodeResource(getResources(), R.id.iv_showPics);
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    sendBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                    byte[] byteArray = stream.toByteArray();
//                    intent.putExtra("image",byteArray);
//                startActivity(intent);
        });
        //사진찍기
        bt_takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PERMISSION받은 후 카메라실행
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile(); // 사진찍고 저장할 임시 파일
                    } catch (IOException ex) {
                        //파일저장실패시 ToastMessage
                        Toast.makeText(getApplicationContext(), "createImageFile Failed", Toast.LENGTH_LONG).show();
                    }

                    if (photoFile != null) {
                        PhotoUri = FileProvider.getUriForFile(Main7Activity_Album_SelectPhoto.this, "rosie.example.com.rosiestagram.provider", photoFile); //임시 파일의 위치, 경로 가져옴
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, PhotoUri);
                        startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
                    }
                }
            }
        });

        //앨범불러오기
        bt_Album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select An Image"), PICK_IMAGE_REQUEST);
//                doTakeAlbumAction();
            }
        });

        //android.os.FileUriExposedException: file:///storage/emulated/0/tmp_170806_103953.jpg exposed beyond app through ClipData.Item.getUri() 에러 후 추가
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    } //onCreate close


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            Toast.makeText(Main7Activity_Album_SelectPhoto.this, "Image Processing error, Please Try Again ", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {
            case CROP_FROM_CAMERA:   //요청한코드가 REQUEST_TAKE_PHOTO인경우//    private void galleryRefresh(){
//                Bitmap bitmap = null;

//                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), PhotoUri);
//                    Bitmap thumbImage = ThumbnailUtils.extractThumbnail(bitmap, 128, 128);
//                    ByteArrayOutputStream BArrayOutStr = new ByteArrayOutputStream();
//                    thumbImage.compress(Bitmap.CompressFormat.JPEG, 100, BArrayOutStr);
                galleryAddPic();
                iv_showPics.setImageURI(null);
                iv_showPics.setImageURI(PhotoUri);
                break;

            case PICK_IMAGE_REQUEST:
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                    filePath = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        iv_showPics.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case PICK_FROM_CAMERA:
                cropImage();
                //갤러리에 나타나도록
                MediaScannerConnection.scanFile(Main7Activity_Album_SelectPhoto.this, new String[]{PhotoUri.getPath()}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                            }
                        });
                break;

            case PLEASE_BRING_PHOTO:        // Main4Activity로 넘어갈때 imageView의 이미지 넘기기
                filePath = data.getData();
                try {
                    Intent intent = new Intent(Main7Activity_Album_SelectPhoto.this, Main4Activity_NewPostPage.class);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    intent.putExtra("pics", bitmap);
                    startActivityForResult(intent, PLEASE_TAKE_PHOTO);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }// onActivityResult Override Close

    //동기화메서드
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE); //동기화
//파일경로 파일타입으로 담기
        File f = new File(mCurrentPhotoPath);
// f 변수에저장된 파일경로를 Uri타입 변수명 contentUri 로 저장
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    //앨범호출, 앨범에서 사진 가져오기
    private void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    //Camera에서 찍은후 사진 가져오기(사진선택)
    private File createImageFile() throws IOException {
        //특정 경로와 폴더를 지정하지 않고, 메모리 최상 위치에 저장방법 : tmp_ + 현재시간.jpg
        String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        String imageFileName = "tmp_" + timeStamp + "rosie_";
        //(저장경로 , 저장할파일이름)
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/RosieAlbum/");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(imageFileName, ".jpg", storageDir); // 파일이름, 형식, 저장폴더
        mCurrentPhotoPath = "file : " + image.getAbsolutePath();   //external 절대경로로 사진 저장경로지정
        return image;
    }

    private void cropImage() {

        this.grantUriPermission("com.android.camera", PhotoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION & Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(PhotoUri, "image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        grantUriPermission(list.get(0).activityInfo.packageName, PhotoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                & Intent.FLAG_GRANT_READ_URI_PERMISSION);

        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "Working has been Canceled", Toast.LENGTH_SHORT).show();
            return;

        } else {
            Toast.makeText(this, "It is able to take long time In case PhotoVolume has huge", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            File croppedFileName = null;
            try {
                croppedFileName = createImageFile();
                Log.d("TAG", "LOG1");
            } catch (IOException e) {
                e.printStackTrace();
            }
            File folder = new File(Environment.getExternalStorageDirectory() + "/RosieAlbum/");
            Log.d("TAG", "LOG2");
            File tempFile = new File(folder.toString(), croppedFileName.getName());
            Log.d("TAG", "LOG3");

            PhotoUri = FileProvider.getUriForFile(Main7Activity_Album_SelectPhoto.this, "rosie.example.com.rosiestagram.provider", tempFile);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            intent.putExtra("return-data", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, PhotoUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            Log.d("TAG", "LOG4");
            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                getApplication().grantUriPermission(res.activityInfo.packageName, PhotoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            Log.d("TAG", "LOG5");
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            checkPermissions();
            Log.d("TAG", "LOG6");
            startActivityForResult(i, CROP_FROM_CAMERA);
        }
    }


    //Permission 시작
    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if (result != PackageManager.PERMISSION_GRANTED) { //사용자가 해당 권한을 가지고 있지 않을 경우 리스트에 해당 권한명 추가
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) { //권한이 추가되었으면 해당 리스트가 empty가 아니므로 request 즉 권한을 요청합니다.
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(this.permissions[0])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        } else if (permissions[i].equals(this.permissions[1])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        } else if (permissions[i].equals(this.permissions[2])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        }
                    }
                } else {
                    showNoPermissionToastAndFinish();
                }
                return;
            }
        }
    }

    private void showNoPermissionToastAndFinish() {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
}