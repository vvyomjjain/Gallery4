package com.example.vyomjain.gallery4;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.mimeType;
import static android.R.attr.path;
import static android.R.attr.type;
import static android.R.id.list;
import static android.content.ContentValues.TAG;
import static android.os.Build.VERSION_CODES.M;
import static com.bumptech.glide.Glide.with;

public class MainActivity extends AppCompatActivity {

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
            List<String> files = getFilePaths(this);
            final String[] paths = files.toArray(new String[files.size()]);

            GridView gridView = (GridView)findViewById(R.id.activity_main);
            gridView.setAdapter(new wordAdapter(MainActivity.this, paths));
        }
    }

    public List<String> getFilePaths(Context context){
        String[] columns = {MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.DATE_ADDED, MediaStore.Files.FileColumns.MEDIA_TYPE};
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        Uri queryUri = MediaStore.Files.getContentUri("external");

        CursorLoader cursorLoader = new CursorLoader(
                this,
                queryUri,
                columns,
                selection,
                null, // Selection args (none).
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC" // Sort order.
        );

        Cursor cursor = cursorLoader.loadInBackground();

        List<String> result = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            final int image_path_col = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            do {
                result.add(cursor.getString(image_path_col));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridView gridView = (GridView)findViewById(R.id.activity_main);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Lobster-Regular.ttf");
        getActionBar();

        if(isStoragePermissionGranted()){
            List<String> files = getFilePaths(this);
            final String[] paths = files.toArray(new String[files.size()]);

            gridView.setAdapter(new wordAdapter(MainActivity.this, paths));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Uri myUri = Uri.parse(paths[position]);
                    File myFile = new File(paths[position]);
                    String extension = myFile.getAbsolutePath().substring(myFile.getAbsolutePath().lastIndexOf("."));

                    if(extension.equals(".mp4")){
                        Intent intent = new Intent(Intent.ACTION_VIEW, myUri);
                        intent.setDataAndType(myUri, "video/mp4");
                        startActivity(intent);

                    }else {

                        Intent imageIntent = new Intent(MainActivity.this, ImageActivity.class);
                        imageIntent.putExtra("imagePath", paths[position]);
                        startActivity(imageIntent);
                    }
                }
            });

            FloatingActionButton folders = (FloatingActionButton)findViewById(R.id.folders);
            folders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent folderIntent = new Intent(MainActivity.this, FolderActivity.class);
                    folderIntent.putExtra("paths", paths);
                    startActivity(folderIntent);
                }
            });
        }

    }
}
