package com.example.vyomjain.gallery4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static com.bumptech.glide.Glide.with;

/**
 * Created by Vyom Jain on 24-04-2017.
 */

public class FolderAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private String[] folderNames;
    private String[] firstImages;

    public FolderAdapter(Context context, String[] folderNames, String[] firstImages) {
        super(context, R.layout.folder_item, folderNames);

        this.context = context;
        this.folderNames = folderNames;
        this.firstImages = firstImages;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.folder_item, parent, false);
        }

        TextView folderName = (TextView)convertView.findViewById(R.id.folderName_item);
        folderName.setText(folderNames[position]);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.first_image);
        Glide
                .with(context)
                .load(firstImages[position])
                .into(imageView);

        return convertView;
    }
}
