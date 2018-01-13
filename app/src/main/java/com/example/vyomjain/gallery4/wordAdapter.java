package com.example.vyomjain.gallery4;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import static com.bumptech.glide.Glide.with;

/**
 * Created by Vyom Jain on 27-03-2017.
 */

public class wordAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private String[] imageUrls;

    public wordAdapter(Context context, String[] imageUrls) {
        super(context, R.layout.grid_item, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
        }

        ImageView gridImage = (ImageView)convertView.findViewById(R.id.grid_item_image);
        ImageView movie = (ImageView)convertView.findViewById(R.id.movie_bg);
        Glide
                .with(context)
                .load(imageUrls[position])
                .into(gridImage);

        File myFile = new File(imageUrls[position]);
        String extension = myFile.getAbsolutePath().substring(myFile.getAbsolutePath().lastIndexOf("."));
        if(extension.equals(".mp4")){
            movie.setVisibility(View.VISIBLE);
        }else
            movie.setVisibility(View.INVISIBLE);

        return convertView;
    }
}
