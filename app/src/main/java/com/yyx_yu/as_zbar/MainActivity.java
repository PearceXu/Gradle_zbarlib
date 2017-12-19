package com.yyx_yu.as_zbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yyx_yu.as_zbar.permission.PermissionsActivity;
import com.yyx_yu.as_zbar.permission.PermissionsChecker;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     *  TAG
     */
    private static String TAG = MainActivity.class.getSimpleName();

    /**
     * 权限返回参数
     */
    private static int REQUEST_CODE= 1;

    private static int REQUEST_ALBUM_OK= 2344;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        Button openCamera = (Button) findViewById(R.id.open_camera);

        openCamera.setOnClickListener(this);

        Button openPhoto = (Button)findViewById(R.id.open_photo);

        openPhoto.setOnClickListener(this);
    }






    @Override
    protected void onResume() {
        super.onResume();

          // 缺少权限时, 进入权限配置页面
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && PermissionsChecker.lacksPermissions(this)) {

            PermissionsActivity.startActivityForResult(this, REQUEST_CODE);
        }
    }

    /**
     * 接收返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }else if (requestCode == REQUEST_ALBUM_OK) {

            Log.d(TAG, "onActivityResult:相册 " + data.getData().toString());
            ContentResolver resolver = getContentResolver();

            try {
                InputStream inputStream = resolver.openInputStream(data.getData());

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                //  mIvDispaly.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {


                e.printStackTrace();
            }
        }
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.open_camera:

                Intent intent = new Intent(MainActivity.this,CameraTestActivity.class);

                MainActivity.this.startActivity(intent);

                break;
            case R.id.open_photo:

                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, REQUEST_ALBUM_OK);

                break;
            default:
                Log.d(TAG,"View控件不可能选择为空的!");
                break;
        }

    }
}
