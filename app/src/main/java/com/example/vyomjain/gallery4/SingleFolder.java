package com.example.vyomjain.gallery4;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.vyomjain.gallery4.R.id.folders;

public class SingleFolder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_folder);

        final GridView gridView = (GridView)findViewById(R.id.single_folder_grid);
        final String[] paths = getIntent().getStringArrayExtra("paths");
        final String folderName = getIntent().getStringExtra("folderName");
        List<String> folderImages = new ArrayList<String>();
        getSupportActionBar().setTitle(folderName);

        for(int i=0; i<paths.length; i++){
            File file = new File(paths[i]);
            String directoryPath = file.getParent();
            File directory = new File(directoryPath);

            if((directory.getName()).equals(folderName)){
                folderImages.add(paths[i]);
            }
        }
        final String[] imageArray = folderImages.toArray(new String[folderImages.size()]);

        gridView.setAdapter(new wordAdapter(this, imageArray));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri myUri = Uri.parse(imageArray[position]);
                File myFile = new File(imageArray[position]);
                String extension = myFile.getAbsolutePath().substring(myFile.getAbsolutePath().lastIndexOf("."));

                if(extension.equals(".mp4")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, myUri);
                    intent.setDataAndType(myUri, "video/mp4");
                    startActivity(intent);

                }else {

                    Intent imageIntent = new Intent(SingleFolder.this, ImageActivity.class);
                    imageIntent.putExtra("imagePath", imageArray[position]);
                    startActivity(imageIntent);
                }
            }
        });
    }
}
