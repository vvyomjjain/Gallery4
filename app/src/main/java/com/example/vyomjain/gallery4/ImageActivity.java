package com.example.vyomjain.gallery4;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

import static android.R.attr.name;
import static android.R.attr.path;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        final String path = getIntent().getStringExtra("imagePath");
        String name = null;
        name = (new File(path)).getName();

        final ImageView imageView = (ImageView)findViewById(R.id.image);
        Glide
                .with(ImageActivity.this)
                .load(path)
                .crossFade()
                .into(imageView);

        final Button properties = (Button)findViewById(R.id.properties);
        properties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent proIntent = new Intent(ImageActivity.this, Properties.class);
                proIntent.putExtra("path", path);
                startActivity(proIntent);
            }
        });

        final Button share = (Button)findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
            }
        });

        final Button wallpaper = (Button)findViewById(R.id.wallpaper);
        wallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setDataAndType(Uri.fromFile(new File(path)), "image/jpeg");
                intent.putExtra("mimeType", "image/jpeg");
                startActivity(Intent.createChooser(intent, "Set as:"));
            }
        });

        final RelativeLayout buttons = (RelativeLayout)findViewById(R.id.buttons);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttons.getVisibility() == View.VISIBLE){
                    buttons.setVisibility(View.INVISIBLE);
                }else
                    buttons.setVisibility(View.VISIBLE);
            }
        });
    }
}
