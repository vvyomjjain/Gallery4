package com.example.vyomjain.gallery4;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Date;

public class Properties extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);

        final String path = getIntent().getStringExtra("path");
        File file = new File(path);

        final TextView pathview= (TextView)findViewById(R.id.path);
        pathview.setText(path);

        final TextView sizeview = (TextView)findViewById(R.id.size);
        float size = file.length()/1024;
        if(size > 1100){
            size = file.length()/(1024*1024);
            String file_size = String.valueOf(size);
            sizeview.setText(file_size + " MB");
        }else{
            String file_size = String.valueOf(size);
            sizeview.setText(file_size + " KB");
        }


        final TextView nameView = (TextView)findViewById(R.id.name);
        nameView.setText(file.getName());

        final ImageView imageView = (ImageView)findViewById(R.id.image_view);
        Glide
                .with(this)
                .load(path)
                .into(imageView);

        final TextView dateView = (TextView)findViewById(R.id.date);
        Date lastModDate = new Date(file.lastModified());
        dateView.setText(lastModDate.toString());
    }
}